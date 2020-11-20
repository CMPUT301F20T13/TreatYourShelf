package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProfileRepository {

    private static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private ProfileRepository() { }

    public static ProfileLiveData getProfileByUsernameLiveData(String username) {
        Query query = firebaseFirestore.collection("users").whereEqualTo("username", username);
        return new ProfileLiveData(query);
    }

    public static ProfileLiveData getProfileByEmailLiveData(String email) {
        Query query = firebaseFirestore.collection("users").whereEqualTo("email", email);
        return new ProfileLiveData(query);
    }

    public static boolean isUsernameAvailable(String username) {
        // TODO : Add functionality to search for a username's availability
        return false;
    }

    public static void updateInformationByEmail(String email, Profile profile) {
        // TODO : Update the user's information given their email
    }

}
