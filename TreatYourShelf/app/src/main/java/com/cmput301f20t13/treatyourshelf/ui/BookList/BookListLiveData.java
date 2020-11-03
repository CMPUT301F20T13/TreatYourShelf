package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookListLiveData extends
        LiveData<List<Book>> implements
        EventListener<DocumentSnapshot> {
    private List<Book> bookListTemp = new ArrayList<>();
    private DocumentReference documentReference;
    public MutableLiveData<List<Book>> bookList = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};

    public BookListLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    protected void onActive() {
        listenerRegistration = documentReference.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        Log.d("TAG", "live data event");
        if(documentSnapshot != null && documentSnapshot.exists()) {
            Map<String, Object> bookListItems = documentSnapshot.getData();
            bookListTemp.clear();
            for (Map.Entry<String, Object> entry : bookListItems.entrySet()) {
                Book itemToAdd = new Book();
                Map<String, Object> bookDetails = (Map<String, Object>) entry.getValue();
                itemToAdd.setTitle((String) bookDetails.getOrDefault("title", "default title"));
                itemToAdd.setAuthor((String) bookDetails.getOrDefault("author", "default author"));
                Log.d("TAG", "got a new book "+ itemToAdd.getTitle()+ " "+ itemToAdd.getAuthor());
                bookListTemp.add(itemToAdd);
            }
            bookList.setValue(bookListTemp);
        } else {
            Log.d("TAG", "error");
        }
    }
}
