package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * the livedata object used in the BookSearchRepository
 */
public class BookSearchLiveData extends LiveData<List<Book>>
        implements EventListener<QuerySnapshot> {
    private final List<Book> bookListTemp = new ArrayList<>();
    private final Query query;
    public MutableLiveData<List<Book>> bookList = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};
    /**
     * sets the livedata's query property
     * @param query the query object
     */
    public BookSearchLiveData(Query query) {
        this.query = query;
    }
    /**
     * sets the listenerRegistration
     */
    @Override
    protected void onActive() {
        listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }
    /**
     * removes the listenerRegistration
     */
    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }
    /**
     * retrieves data from a query and adds the data to a list.
     * the value of MutableLiveData is set to the list
     * @param value
     * @param error
     */
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                bookListTemp.clear();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Book itemToAdd = new Book();
                    Map<String, Object> bookDetails = document.getData();
                    itemToAdd.setTitle((String) bookDetails.getOrDefault("title", "default title"));
                    itemToAdd.setAuthor((String) bookDetails.getOrDefault("author", "default author"));
                    itemToAdd.setIsbn((String) bookDetails.getOrDefault("isbn", "default isbn"));
                    itemToAdd.setBorrower((String) bookDetails.getOrDefault("borrower", ""));
                    itemToAdd.setOwner((String) bookDetails.getOrDefault("owner", "default owner"));
                    itemToAdd.setStatus((String) bookDetails.getOrDefault("status", "default status"));
                    bookListTemp.add(itemToAdd);
                }
                bookList.setValue(bookListTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}