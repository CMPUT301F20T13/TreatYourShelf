package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import androidx.annotation.NonNull;

import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestListRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collectionRequests = firebaseFirestore.collection("Requests");

    public RequestListLiveData getRequestByIdLiveData(String bookId) {
        Query query = firebaseFirestore.collection("requests").whereEqualTo("id", bookId);
        return new RequestListLiveData(query);
    }

    public RequestListLiveData getRequestByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("requests").whereEqualTo("isbn", isbn);
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

}
