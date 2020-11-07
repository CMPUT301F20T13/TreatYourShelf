package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProfileRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public ProfileLiveData getProfileByUsernameLiveData(String username) {
        Query query = firebaseFirestore.collection("users").whereEqualTo("username", username);
        return new ProfileLiveData(query);
    }
}
