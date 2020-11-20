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

public class ProfileLiveData extends LiveData<Profile> implements EventListener<QuerySnapshot> {

    private final Query query;
    public MutableLiveData<Profile> profile = new MutableLiveData<>();

    private ListenerRegistration listenerRegistration = () -> {};

    public ProfileLiveData(Query query) {
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
                Profile profileTemp = new Profile();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Map<String, Object> profileDetails = document.getData();
                        profileTemp.setUsername((String) profileDetails.getOrDefault("username", "default username"));
                        profileTemp.setPassword((String) profileDetails.getOrDefault("password", "default password"));
                        profileTemp.setEmail((String) profileDetails.getOrDefault("email", "default email"));
                        profileTemp.setPhoneNumber((String) profileDetails.getOrDefault("phoneNumber", "default number"));
                        // Get the profile URL
                        DocumentReference profileRef = (DocumentReference) profileDetails.getOrDefault("profileImage", "");
                        profileTemp.setProfileImageUrl(profileRef.toString());
                }
                profile.setValue(profileTemp);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
