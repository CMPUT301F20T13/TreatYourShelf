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

/**
 * the repository object that contains the methods  used by the RequestListViewModel
 */
public class RequestListRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collectionRequests = firebaseFirestore.collection("requests");
    private static final String TAG = "RequestListRepo";

    /**
     * returns requests based on the isbn of the requested book
     * @param isbn the provided isbn
     * @return RequestListLiveData that holds the result of the query
     */
    public RequestListLiveData getRequestByIsbnLiveData(String isbn) {
        Query query = collectionRequests.whereEqualTo("isbn", isbn);
        return new RequestListLiveData(query);
    }

    /**
     * returns requests based on the owner of the requested books
     * @param owner the provided owner
     * @return RequestListLiveData that holds the result of the query
     */
    public RequestListLiveData getRequestByOwnerLiveData(String owner) {
        Query query = collectionRequests.whereEqualTo("owner", owner);
        return new RequestListLiveData(query);
    }

    /**
     * returns requests based on the owner and the isbn of the requested books
     * @param isbn the isbn of the requested books
     * @param owner the owner of the requested books
     * @return RequestListLiveData that holds the result of the query
     */
    public RequestListLiveData getRequestByIsbnOwnerLiveData(String isbn, String owner) {
        Query query = collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("owner", owner);
        return new RequestListLiveData(query);
    }

    /**
     * returns requests based on the requester and isbn of the requested book
     * @param isbn the provided isbn
     * @param requester the provided requester
     * @return RequestListLiveData that holds the result of the query
     */
    public RequestListLiveData getRequestByIsbnRequesterLiveData(String isbn, String requester) {
        Query query = collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("requester", requester);
        return new RequestListLiveData(query);
    }

    /**
     * returns requests for a specific requester
     * @param requester the given requester
     * @return RequestListLiveData that holds the result of the query
     */
    public RequestListLiveData getRequestByRequesterLiveData(String requester) {
        Query query = collectionRequests
                .whereEqualTo("requester", requester);
        return new RequestListLiveData(query);
    }

    /**
     * updates the status of a request with an isbn and requester that match the inputs
     * @param requester the provided requester
     * @param isbn the provided isbn
     * @param status the status to update the request to
     */
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

    /**
     *  removes requests from the database with and isbn, owner and requester that match the inputs
     * @param isbn the provided isbn
     * @param owner the provided owner
     * @param requester the provided requester
     */
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

    /**
     * adds a request to the database
     * @param book the book that is associated with the requested
     * @param requester the provided requester
     */
    public void addRequest(Book book, String requester) {
        Map<String, Object> newRequest = new HashMap<>();
        newRequest.put("requester", requester);
        newRequest.put("title", book.getTitle());
        newRequest.put("author", book.getAuthor());
        newRequest.put("isbn", book.getIsbn());
        newRequest.put("owner", book.getOwner());
        newRequest.put("status", "requested");
        newRequest.put("imageUrls", book.getImageUrls());
        newRequest.put("location", "");
        String requestId = book.getIsbn() + requester;

        collectionRequests.document(requestId)
                .set(newRequest)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error writing document", e));


    }
}
