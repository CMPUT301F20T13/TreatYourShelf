package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Profile;

public class ProfileViewModel extends ViewModel {

    ProfileLiveData liveData = null;

    public LiveData<Profile> getProfileByUsernameLiveData(String username) {
        liveData = ProfileRepository.getProfileByUsernameLiveData(username);
        return liveData;
    }

    public LiveData<Profile> getProfileByEmailLiveData(String email) {
        liveData = ProfileRepository.getProfileByEmailLiveData(email);
        return liveData;
    }

    public LiveData<Profile> getProfile() {
        return liveData.profile;
    }
}
