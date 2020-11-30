package com.cmput301f20t13.treatyourshelf.ui.RequestList;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * the livedata object used in the RequestListRepository, contains a firebase query and a
 * MutableliveData that holds the results of the query
 */
public class RequestListLiveData extends
        LiveData<List<Request>> implements
        EventListener<QuerySnapshot> {
    private final List<Request> requestListTemp = new ArrayList<>();
    private final Query query;
    public MutableLiveData<List<Request>> requestList = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};

    /**
     * sets the livedata's query property
     * @param query the query object
     */
    public RequestListLiveData(Query query) {
        this.query = query;
    }

    /**
     * sets the listenerRegistration
     */
    @Override
    protected void onActive() {
        listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }

    /**
     * removes the listenerRegistration
     */
    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }

    /**
     * retrieves data from a query and adds the data to a list.
     * the value of MutableLiveData is set to the list
     * @param value
     * @param error
     */
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                requestListTemp.clear();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Request itemToAdd = new Request();
                    Map<String, Object> requestDetails = document.getData();
                    itemToAdd.setRequester((String) requestDetails.getOrDefault("requester", "default requester"));
                    itemToAdd.setBookId((String) requestDetails.getOrDefault("bookId", "12345"));
                    itemToAdd.setStatus((String) requestDetails.getOrDefault("status", "Requested"));
                    itemToAdd.setOwner((String) requestDetails.getOrDefault("owner", "default owner"));
                    itemToAdd.setIsbn((String) requestDetails.getOrDefault("isbn", "12345678910"));
                    itemToAdd.setAuthor((String) requestDetails.getOrDefault("author", "default author"));
                    itemToAdd.setTitle((String) requestDetails.getOrDefault("title", "default title"));
                    requestListTemp.add(itemToAdd);
                }
                requestList.setValue(requestListTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
