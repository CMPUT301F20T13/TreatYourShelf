package com.cmput301f20t13.treatyourshelf.ui.BookList;

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
 * livedata object used for the BookListRepository
 */
public class BookListLiveData extends
        LiveData<List<Book>> implements
        EventListener<QuerySnapshot> {
    private final List<Book> bookListTemp = new ArrayList<>();
    private final Query query;
    public MutableLiveData<List<Book>> bookList = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {
    };

    public BookListLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }

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
                    itemToAdd.setDescription((String) bookDetails.getOrDefault("description", "default description"));
                    itemToAdd.setIsbn((String) bookDetails.getOrDefault("isbn", "default isbn"));
                    itemToAdd.setOwner((String) bookDetails.getOrDefault("owner", "default owner"));
                    itemToAdd.setImageUrls((List<String>) bookDetails.getOrDefault("imageUrls", null));
                    itemToAdd.setBorrower((String) bookDetails.getOrDefault("borrower", "default borrower"));
                    itemToAdd.setStatus((String) bookDetails.getOrDefault("status", "Available"));
                    bookListTemp.add(itemToAdd);
                }
                bookList.setValue(bookListTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
