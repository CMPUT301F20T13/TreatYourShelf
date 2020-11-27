package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Profile;

/**
 * The view model for the profile fragment.
 */
public class ProfileViewModel extends ViewModel {

    // The LiveData for the profile from queries
    private ProfileLiveData liveData = null;

    /**
     * Return a LiveDate profile given a username. Searches the database and returns the result.
     * @param username  The username for which to query by.
     * @return          The LiveData Profile from the repository.
     */
    public LiveData<Profile> getProfileByUsernameLiveData(String username) {
        liveData = ProfileRepository.getProfileByUsernameLiveData(username);
        return liveData;
    }

    /**
     * Return a LiveDate profile given an email. Searches the database and returns the result.
     * @param email The unique email for which to query by.
     * @return      The LiveData Profile from the repository.
     */
    public LiveData<Profile> getProfileByEmailLiveData(String email) {
        liveData = ProfileRepository.getProfileByEmailLiveData(email);
        return liveData;
    }

    /**
     * Get the result of previous queries.
     * @return  The LiveData Profile.
     */
    public LiveData<Profile> getProfile() {
        return liveData.profile;
    }
}
