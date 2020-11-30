package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import android.util.Log;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestListRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collectionRequests = firebaseFirestore.collection("requests");
    private static final String TAG = "RequestListRepo";

    public RequestListLiveData getRequestByIsbnLiveData(String isbn) {
        Query query = collectionRequests.whereEqualTo("isbn", isbn);
        return new RequestListLiveData(query);
    }

    public RequestListLiveData getRequestByOwnerLiveData(String owner) {
        Query query = collectionRequests.whereEqualTo("owner", owner);
        return new RequestListLiveData(query);
    }

    public RequestListLiveData getRequestByIsbnOwnerLiveData(String isbn, String owner) {
        Query query = collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("owner", owner);
        return new RequestListLiveData(query);
    }

    public RequestListLiveData getRequestByIsbnRequesterLiveData(String isbn, String requester) {
        Query query = collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("requester", requester);
        return new RequestListLiveData(query);
    }

    public RequestListLiveData getRequestByRequesterLiveData(String requester) {
        Query query = collectionRequests
                .whereEqualTo("requester", requester);
        return new RequestListLiveData(query);
    }

    public void updateStatusByIsbn(String requester, String isbn, String status){
        collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("requester", requester)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            DocumentReference docRef = collectionRequests.document(document.getId());
                            docRef.update("status", status);
                        }
                    }
                });
    }


    public void removeRequest(String isbn, String owner, String requester) {
        collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("owner", owner)
                .whereEqualTo("requester", requester)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            DocumentReference docRef = collectionRequests.document(document.getId());
                            docRef.delete();
                        }
                    }
                });
    }


    public void addRequest(Book book, String requester) {
        Map<String, Object> newRequest = new HashMap<>();
        newRequest.put("requester", requester);
        newRequest.put("title", book.getTitle());
        newRequest.put("author", book.getAuthor());
        newRequest.put("isbn", book.getIsbn());
        newRequest.put("owner", book.getOwner());
        newRequest.put("status", "requested");
        newRequest.put("imageUrls", book.getImageUrls());
        String requestId = book.getIsbn() + requester;

        collectionRequests.document(requestId)
                .set(newRequest)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error writing document", e));


    }
}
