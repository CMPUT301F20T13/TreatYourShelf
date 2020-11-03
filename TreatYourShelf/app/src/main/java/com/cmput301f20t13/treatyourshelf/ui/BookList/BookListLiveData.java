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

public class BookListLiveData extends
        LiveData<List<Book>> implements
        EventListener<QuerySnapshot> {
    private final List<Book> bookListTemp = new ArrayList<>();
    private final Query isbnQuery;
    public MutableLiveData<List<Book>> bookList = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};

    public BookListLiveData(Query isbnQuery) {
        this.isbnQuery = isbnQuery;
    }

    @Override
    protected void onActive() {
        listenerRegistration = isbnQuery.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        isbnQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                bookListTemp.clear();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Book itemToAdd = new Book();
                        Map<String, Object> bookDetails = document.getData();
                        itemToAdd.setTitle((String) bookDetails.getOrDefault("title", "default title"));
                        itemToAdd.setAuthor((String) bookDetails.getOrDefault("author", "default author"));
                        itemToAdd.setIsbn((String) bookDetails.getOrDefault("isbn", "default isbn"));
                        bookListTemp.add(itemToAdd);
                }
                bookList.setValue(bookListTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
