package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * the repository object holds the methods called by the AddBookViewModel
 */
public class AddBookRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    /**
     * returns books from the database with the specified isbn as an AddBookLiveData object
     * @param isbn the provided isbn
     * @return AddBookLiveData object that contains the result of the query
     */
    public AddBookLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("isbn", isbn);
        return new AddBookLiveData(query);
    }

    /**
     * returns books from the database with the specified owner as an AddBookLiveData object
     * @param owner the provided owner
     * @return AddBookLiveData object that contains the result of the query
     */
    public AddBookLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("owner", owner);
        return new AddBookLiveData(query);
    }

    /**
     * this method adds a book to the database
     * @param book the Book object to be added
     */
    public void addBook(Book book) {
        Map<String, Object> insertBook = new HashMap<>();
        insertBook.put("author", book.getAuthor());
        insertBook.put("isbn", book.getIsbn());
        insertBook.put("description", book.getDescription());
        insertBook.put("title", book.getTitle());
        insertBook.put("imageUrls", book.getImageUrls());
        insertBook.put("owner", book.getOwner());
        insertBook.put("status", book.getStatus());
        firebaseFirestore.collection("books").document()
                .set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    /**
     * deletes a book from the database based on the given isbn
     * @param isbn the provided isbn
     */
    public void deleteBook(String isbn) {
        firebaseFirestore.collection("books").whereEqualTo("isbn", isbn)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference dRef = firebaseFirestore.collection("books").document(document.getId());
                                dRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        } else {

                        }
                    }
                });
    }

    /**
     * this method edits a book in the database based on the isbn, also allows a user to change the isbn
     * @param book the book object containing the updated information
     * @param oldIsbn the isbn of the book, will be different if the isbn is being modified
     */
    public void editBook(Book book, String oldIsbn) {
        firebaseFirestore.collection("books").whereEqualTo("isbn", oldIsbn).whereEqualTo("owner",book.getOwner())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference uRef = firebaseFirestore.collection("books").document(document.getId());
                                uRef.update("author", book.getAuthor());
                                uRef.update("description", book.getDescription());
                                uRef.update("title", book.getTitle());
                                uRef.update("imageUrls", book.getImageUrls());
                                uRef.update("isbn", book.getIsbn()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        } else {

                        }
                    }
                });

    }


}
