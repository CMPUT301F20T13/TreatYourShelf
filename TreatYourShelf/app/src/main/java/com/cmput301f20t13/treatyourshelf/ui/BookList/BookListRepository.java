package com.cmput301f20t13.treatyourshelf.ui.BookList;

import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.AddBookLiveData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookListRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public AddBookLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("isbn", isbn);
        return new BookListLiveData(query);
    }

    public BookListLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("owner", owner);
        return new BookListLiveData(query);
    }

    public BookListLiveData getAllBooksLiveData() {
        Query query = firebaseFirestore.collection("books");
        return new BookListLiveData(query);
    }
}