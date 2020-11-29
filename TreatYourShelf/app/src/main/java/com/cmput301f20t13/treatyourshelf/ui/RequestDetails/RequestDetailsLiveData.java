package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Request;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class RequestDetailsLiveData extends
        LiveData<Request> implements
        EventListener<QuerySnapshot> {
    private final Query query;
    public MutableLiveData<Request> request = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};

    public RequestDetailsLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Request requestTemp = new Request();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Map<String, Object> requestDetails = document.getData();
                    requestTemp.setRequester((String) requestDetails.getOrDefault("requester", "default requester"));
                    requestTemp.setBookId((String) requestDetails.getOrDefault("bookId", "12345"));
                    requestTemp.setStatus((String) requestDetails.getOrDefault("status", "Requested"));
                    requestTemp.setOwner((String) requestDetails.getOrDefault("owner", "default owner"));
                    requestTemp.setIsbn((String) requestDetails.getOrDefault("isbn", "12345678910"));
                    requestTemp.setAuthor((String) requestDetails.getOrDefault("author", "default author"));
                    requestTemp.setTitle((String) requestDetails.getOrDefault("title", "default title"));
                    requestTemp.setImageUrls((List<String>) requestDetails.getOrDefault("imageUrls", null));
                }
                request.setValue(requestTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
