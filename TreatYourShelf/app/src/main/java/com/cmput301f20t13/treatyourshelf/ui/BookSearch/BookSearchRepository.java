package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookSearchRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public BookSearchLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("isbn", isbn).whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    public BookSearchLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("owner", owner).whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    public BookSearchLiveData getBookByTitleLiveData(String title) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("title", title).whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    public BookSearchLiveData getAllBooksLiveData() {
        Query query = firebaseFirestore.collection("books").whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }
}
