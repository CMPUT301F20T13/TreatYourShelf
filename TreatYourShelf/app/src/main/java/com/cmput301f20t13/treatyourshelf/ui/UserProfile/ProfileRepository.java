package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
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
        firebaseFirestore.collection("users").whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot docSnap : task.getResult()) {
                            DocumentReference doc = firebaseFirestore.collection("users").document(docSnap.getId());
                            doc.update("username", profile.getUsername());
                            doc.update("phoneNumber", profile.getPhoneNumber());
                            // TODO : Update the profile picture too
                        }
                    } else {
                        // bad
                    }
                });
    }

    public static void addProfile(Profile profile) {
        Map<String, Object> insertProfile = new HashMap<>();
        insertProfile.put("email", profile.getEmail());
        insertProfile.put("password", profile.getPassword());
        insertProfile.put("phoneNumber", profile.getPhoneNumber());
        // TODO : Profile images
        // insertProfile.put("profileImage", profile.)
        insertProfile.put("username", profile.getUsername());
        firebaseFirestore.collection("users").document()
                .set(insertProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Nothing
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Nothing
                    }
                });
    }

}
