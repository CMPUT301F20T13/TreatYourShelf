package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;

import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class RequestDetailsRepository{
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collectionRequests = firebaseFirestore.collection("requests");
    private final CollectionReference collectionBooks = firebaseFirestore.collection("books");

    public RequestDetailsLiveData getRequestLiveData(String isbn, String requester, String owner) {
        Query query = collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("requester", requester)
                .whereEqualTo("owner", owner);
        return new RequestDetailsLiveData(query);
    }

    public void updateBookStatusByIsbn(String isbn, String status){
        CollectionReference collectionBooks = firebaseFirestore.collection("books");
        collectionBooks
                .whereEqualTo("isbn", isbn)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            DocumentReference docRef = collectionBooks.document(document.getId());
                            docRef.update("status", status);
                        }
                    }
                });
    }


    public void updateBookBorrowerByIsbn(String isbn, String requester) {
        collectionBooks
                .whereEqualTo("isbn", isbn)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            DocumentReference docRef = collectionBooks.document(document.getId());
                            docRef.update("borrower", requester);
                        }
                    }
                });

    }

    public void updateRequestStatusByIsbn(String isbn, String requester, String status) {
        collectionRequests
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("requester", requester)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            DocumentReference docRef = collectionRequests.document(document.getId());
                            docRef.update("status", status);
                        }
                    }
                });


    }
}
