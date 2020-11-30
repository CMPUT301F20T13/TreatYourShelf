package com.cmput301f20t13.treatyourshelf.ui.BookList;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * repository object that hold the queries that retrieve data from the database
 */
public class BookListRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    /**
     * returns books from the database based on the provided isbn
     * @param isbn the provided isbn
     * @return BookListLiveData that holds the result of the query
     */
    public  BookListLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("isbn", isbn);
        return new BookListLiveData(query);
    }

    /**
     * returns books from the database based on the provided owner
     * @param owner the provided owner
     * @return BookListLiveData that holds the result of the query
     */
    public BookListLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("owner", owner);
        return new BookListLiveData(query);
    }

    /**
     * returns all books from the database
     * @return BookListLiveData that holds the result of the query
     */
    public BookListLiveData getAllBooksLiveData() {
        Query query = firebaseFirestore.collection("books");
        return new BookListLiveData(query);
    }
}
