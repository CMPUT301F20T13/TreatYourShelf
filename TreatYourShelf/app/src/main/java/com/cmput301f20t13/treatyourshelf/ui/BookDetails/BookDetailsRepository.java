package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListLiveData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class BookDetailsRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collectionRequests = firebaseFirestore.collection("requests");
    private static final String TAG = "BookDetailsFragment";

    public BookDetailsLiveData getBookByIsbnOwner(String isbn, String owner) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("owner", owner);
        return new BookDetailsLiveData(query);
    }

}
