package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookDetailsRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collectionRequests = firebaseFirestore.collection("requests");
    private static final String TAG = "BookDetailsFragment";


    public void addRequest(Book book, String requester) {
        Map<String, Object> newRequest = new HashMap<>();
        newRequest.put("requester", requester);
        newRequest.put("title", book.getTitle());
        newRequest.put("author", book.getAuthor());
        newRequest.put("isbn", book.getIsbn());
        newRequest.put("owner", book.getOwner());
        newRequest.put("status", "Requested");
        String requestId = book.getIsbn() + requester;

        collectionRequests.document(requestId)
                .set(newRequest)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error writing document", e));

        
    }
}
