package com.cmput301f20t13.treatyourshelf.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DBConn {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DBConn() {

    }

    // User functions
    public Profile getUserProfile(String profileUsername){
        Profile foundProfile = new Profile();
        db.collection("users")
                .whereEqualTo("username", profileUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            foundProfile.setUsername((String) document.getData().getOrDefault("username", "default"));
                            foundProfile.setPhoneNumber((String) document.getData().getOrDefault("phoneNumber", "No Phone number"));
                            foundProfile.setPassword((String) document.getData().getOrDefault("email", "No Email Address"));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
        return foundProfile;
    }

    // Book functions

    public Book getBookFromISBN(){
        Book foundBook = new Book();

        return foundBook;
    }

    // BookList functions

    public List<Book> getAllBooks(){
        List<Book> foundBooks = new ArrayList<Book>();

        db.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String title;
                        String author;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            title = (String) document.getData().getOrDefault("title", "default title");
                            author = (String) document.getData().getOrDefault("author", "default author");
                            foundBooks.add(new Book(title, author));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
        return foundBooks;
    }

    public List<Book> getBookListOfUser(String username){
        List<Book> foundBooks = new ArrayList<Book>();

        db.collection("books")
                .whereEqualTo("ownerUsername", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String title;
                            String author;
                            List<Book> books = new ArrayList<Book>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                title = (String) document.getData().getOrDefault("title", "default title");
                                author = (String) document.getData().getOrDefault("author", "default author");
                                books.add(new Book(title, author));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return foundBooks;
    }

}
