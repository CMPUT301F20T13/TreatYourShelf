package com.cmput301f20t13.treatyourshelf.ui.UserProfile.Editing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileLiveData;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileRepository;

public class ProfileEditViewModel extends ViewModel {

    ProfileLiveData liveData = null;

    public LiveData<Profile> getProfileByEmailLiveData(String email) {
        liveData = ProfileRepository.getProfileByEmailLiveData(email);
        return liveData;
    }

    public LiveData<Profile> getProfile() {
        return liveData.profile;
    }

    public void setProfileByEmail(String email, Profile profile) {
        ProfileRepository.updateInformationByEmail(email, profile);
    }

    public boolean isUsernameAvailable(String username) {
        return ProfileRepository.isUsernameAvailable(username);
    }

}
