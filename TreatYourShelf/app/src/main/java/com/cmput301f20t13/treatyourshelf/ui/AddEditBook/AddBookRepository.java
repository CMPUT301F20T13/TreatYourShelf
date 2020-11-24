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

public class AddBookRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public AddBookLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("isbn", isbn);
        return new AddBookLiveData(query);
    }

    public AddBookLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books").whereEqualTo("owner", owner);
        return new AddBookLiveData(query);
    }

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

    public void editBook(Book book) {
        firebaseFirestore.collection("books").whereEqualTo("isbn", book.getIsbn())
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
