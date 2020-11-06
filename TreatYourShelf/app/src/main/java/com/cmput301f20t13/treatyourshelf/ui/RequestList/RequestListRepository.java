package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListLiveData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RequestListRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public RequestListLiveData getRequestByIdLiveData(String bookId) {
        Query query = firebaseFirestore.collection("requests").whereEqualTo("bookId", bookId);
        return new RequestListLiveData(query);
    }
}
