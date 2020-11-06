package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Profile;

public class ProfileViewModel extends ViewModel {

    private final ProfileRepository repository = new ProfileRepository();
    ProfileLiveData liveData = null;

    public LiveData<Profile> getProfileByUsernameLiveData(String username) {
        liveData = repository.getProfileByUsernameLiveData(username);
        return liveData;
    }


    public LiveData<Profile> getProfile() {
        return liveData.profile;
    }
}
