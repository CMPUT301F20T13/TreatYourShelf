package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import androidx.lifecycle.LiveData;
import com.cmput301f20t13.treatyourshelf.data.Profile;

public class ProfileRepository {
    private LiveData<Profile> profile;


    public ProfileRepository(LiveData<Profile> profile) {
        this.profile = profile;
    }

    public LiveData<Profile> getProfile(){return profile; }

    public void editProfile() {

    }
}


