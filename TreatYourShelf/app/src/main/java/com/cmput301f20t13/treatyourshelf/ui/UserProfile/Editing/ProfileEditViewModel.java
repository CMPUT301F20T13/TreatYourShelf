package com.cmput301f20t13.treatyourshelf.ui.UserProfile.Editing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileLiveData;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileRepository;

/**
 * The ViewModel for the ProfileEditFragment.
 */
public class ProfileEditViewModel extends ViewModel {

    // The liveData for returning from all queries
    private ProfileLiveData liveData = null;

    /**
     * Returns LiveData for a Profile given a unique email.
     * @param email The unique email String for which to query the repository.
     * @return      The LiveData after querying.
     */
    public LiveData<Profile> getProfileByEmailLiveData(String email) {
        liveData = ProfileRepository.getProfileByEmailLiveData(email);
        return liveData;
    }

    /**
     * Returns the profile after querying if the profile needs to be accessed again.
     * @return  The LiveData after a query to be returned.
     */
    public LiveData<Profile> getProfile() {
        return liveData.profile;
    }

    /**
     * Updates the repository given an email and the new Profile data.
     * @param email     The unique email for which to update.
     * @param profile   The new profile data.
     */
    public void setProfileByEmail(String email, Profile profile) {
        ProfileRepository.updateInformationByEmail(email, profile);
    }

    /**
     * Gets the profile given a unique username from the repository.
     * @param username  The unique username String.
     * @return          The LiveData for the profile found in the repository.
     */
    public LiveData<Profile> getProfileByUsernameLiveData(String username) {
        liveData = ProfileRepository.getProfileByUsernameLiveData(username);
        return liveData;
    }

}
