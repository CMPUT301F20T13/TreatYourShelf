package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

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

    public static ProfileLiveData isUsernameAvailableLiveData(String username) {
        Query query = firebaseFirestore.collection("users").whereEqualTo("username", username);
        return new ProfileLiveData(query);
    }

    public static void updateInformationByEmail(String email, Profile profile) {
        // firebaseFirestore.collection("users").
    }

}
