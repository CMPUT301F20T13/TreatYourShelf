package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class BookDetailsLiveData extends
        LiveData<Book> implements
        EventListener<QuerySnapshot> {
    private final Query query;
    public MutableLiveData<Book> book = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {
    };

    public BookDetailsLiveData(Query query) {
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
                Book bookTemp = new Book();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Map<String, Object> bookDetails = document.getData();
                    bookTemp.setTitle((String) bookDetails.getOrDefault("title", "default title"));
                    bookTemp.setAuthor((String) bookDetails.getOrDefault("author", "default author"));
                    bookTemp.setDescription((String) bookDetails.getOrDefault("description", "default description"));
                    bookTemp.setIsbn((String) bookDetails.getOrDefault("isbn", "default isbn"));
                    bookTemp.setOwner((String) bookDetails.getOrDefault("owner", "default owner"));
                    bookTemp.setImageUrls((List<String>) bookDetails.getOrDefault("imageUrls", null));
                    bookTemp.setBorrower((String) bookDetails.getOrDefault("borrower", "default borrower"));
                    bookTemp.setStatus((String) bookDetails.getOrDefault("status", "Available"));
                }
                book.setValue(bookTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
