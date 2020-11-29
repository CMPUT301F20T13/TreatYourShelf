package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Class used to store the LiveData for the Profile. Stores the information for the profiles after
 * a firebase query.
 */
public class ProfileLiveData extends LiveData<Profile> implements EventListener<QuerySnapshot> {

    // Stores the query for which the data is set in Profile
    private final Query query;
    // The actual mutable live data for the profile
    public MutableLiveData<Profile> profile = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};

    /**
     * The constructor which takes in a Query and parses the data.
     * @param query The asynchronous query given for which the data is parsed.
     */
    public ProfileLiveData(Query query) {
        this.query = query;
    }

    /**
     * Called when the LiveData is active. Calls the super onActive.
     */
    @Override
    protected void onActive() {
        listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }

    /**
     * Called when the LiveData is inactive.
     */
    @Override
    protected void onInactive() {
        listenerRegistration.remove();
        super.onInactive();
    }

    /**
     * Called when a Query event occurs. Parses the Query result and sets the MutableLiveData.
     * @param value The QuerySnapshot. Not used.
     * @param error The error from the Firebase Firestore.
     */
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Profile profileTemp = new Profile();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Map<String, Object> profileDetails = document.getData();
                        profileTemp.setUsername((String) profileDetails.getOrDefault("username", "default username"));
                        profileTemp.setPassword((String) profileDetails.getOrDefault("password", "default password"));
                        profileTemp.setEmail((String) profileDetails.getOrDefault("email", "default email"));
                        profileTemp.setPhoneNumber((String) profileDetails.getOrDefault("phoneNumber", "default number"));
                        // Get the profile URL
/*                        DocumentReference profileRef = (DocumentReference) profileDetails.getOrDefault("profileImage", "");
                        profileTemp.setProfileImageUrl(profileRef.toString());*/

                        profileTemp.setProfileImageUrl((String) profileDetails.getOrDefault("profileImage", ""));
                }
                profile.setValue(profileTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
