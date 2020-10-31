package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cmput301f20t13.treatyourshelf.data.Profile;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profileRepository;
    private LiveData<Profile> profile;

    public ProfileViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<Profile> getProfile(){
        return profileRepository.getProfile();
    }


    public void editProfile() {
        profileRepository.editProfile();
    }
}
