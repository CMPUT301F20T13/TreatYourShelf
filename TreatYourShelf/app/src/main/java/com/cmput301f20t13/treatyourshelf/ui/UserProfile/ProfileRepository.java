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

/**
 * Class used for parsing the "users" collection in Firestore and all operations associated.
 */
public class ProfileRepository {

    // The firebase instance
    private static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    /**
     * Private constructor. We don't want any other class instantiating and instance. This is
     * a static class, treated as a singleton.
     */
    private ProfileRepository() { }

    /**
     * Returns LiveData based on a query given a username. Usernames are unique and thus
     * only one ProfileLiveData is returned.
     * @param username  The unique username.
     * @return  The LiveData given the result of the query for the Firestore.
     */
    public static ProfileLiveData getProfileByUsernameLiveData(String username) {
        Query query = firebaseFirestore.collection("users").whereEqualTo("username", username);
        return new ProfileLiveData(query);
    }

    /**
     * Returns LiveData based on a query given an email. Emails are unique and thus
     * only one ProfileLiveData is returned.
     * @param email The unique email.
     * @return      The LiveData given the result of the query for the Firestore.
     */
    public static ProfileLiveData getProfileByEmailLiveData(String email) {
        Query query = firebaseFirestore.collection("users").whereEqualTo("email", email);
        return new ProfileLiveData(query);
    }

    /**
     * Updates the information in the repository given the email and the profile to set the new
     * data to.
     * @param email     The email for which to update the information for. Unique.
     * @param profile   The new profile data for which to update the repository.
     */
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

    /**
     * Adds a new profile to the "users" collection in the repository.
     * @param profile   The profile object for which to add to the Firestore repository.
     */
    public static void addProfile(Profile profile) {
        // Create a map for which the new profile is stores
        Map<String, Object> insertProfile = new HashMap<>();
        insertProfile.put("email", profile.getEmail());
        insertProfile.put("password", profile.getPassword());
        insertProfile.put("phoneNumber", profile.getPhoneNumber());
        // TODO : Profile images
        // insertProfile.put("profileImage", profile.)
        insertProfile.put("username", profile.getUsername());

        // Add it to the Firestore Firebase
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
