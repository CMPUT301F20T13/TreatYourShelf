package com.cmput301f20t13.treatyourshelf.ui.UserProfile.Editing;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The Fragment for the ProfileEdit screen.
 */
public class ProfileEditFragment extends Fragment {

    /**
     * Called when the Fragment is navigated to.
     * @param inflater              Layout inflater used to display fragment_profile_edit.
     * @param container             The ViewGroup container.
     * @param savedInstanceState    The instance state.
     * @return                      Returns the view, this is fragment_profile_edit.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // Get the views needed
        ImageView image = (ImageView) view.findViewById(R.id.profile_image_edit);
        TextInputEditText username = (TextInputEditText) view.findViewById(R.id.profile_username_edit);
        TextView email = (TextView) view.findViewById(R.id.profile_email_edit);
        EditText phone = (EditText) view.findViewById(R.id.profile_phone_edit);

        // Get the current user's email, this is a constant
        String profileEmail = "user1@gmail.com";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            profileEmail = user.getEmail();
        }

        // Get the viewmodel and do an initial empty observe
        ProfileEditViewModel vm = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileEditViewModel.class);
        vm.getProfileByEmailLiveData(profileEmail).observe(getViewLifecycleOwner(), Observable -> {});

        // Set the View's values from the ViewModel's data
        vm.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null ) {
                username.setText(profile.getUsername());
                email.setText(profile.getEmail());
                phone.setText(profile.getPhoneNumber());
                // Get the profile picture
                Glide.with(view).load(profile.getProfileImageUrl()).into(image);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        // Get the old username before its been edited
        final String oldUsername = username.getText().toString();

        // Add functionality for the buttons, which is the most important part here
        Button changepfp = (Button) view.findViewById(R.id.profile_image_edit_button);
        changepfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Change the user's profile picture
            }
        });

        // Add functionality for the cancel button
        Button cancel = (Button) view.findViewById(R.id.profile_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the profile screen
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
            }
        });


        // Add functionality for the Done button
        Button done = (Button) view.findViewById(R.id.profile_done_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the field's typed in value
                String newUsername = username.getText().toString();

                // Empty observe
                vm.getProfileByUsernameLiveData(newUsername).observe(getViewLifecycleOwner(), Observable -> {});

                // Observe the profile and retrieve the data
                vm.getProfile().observe(getViewLifecycleOwner(), profile -> {
                    if (profile != null ) {
                        // Check if the username is available or not
                        if (!profile.getUsername().equals("default user") && !profile.getUsername().equals("default username")) {
                            if (!profile.getUsername().equals(oldUsername)) {
                                // The username is invalid, it's taken already
                                username.setError("That username is taken!");
                                return;
                            }
                        }
                        // The username is valid, it's ours or not taken
                        // Write the new profile data to Firebase
                        String newPhoneNumber = phone.getText().toString();
                        Profile newProfileData = new Profile();
                        newProfileData.setUsername(newUsername);
                        newProfileData.setEmail(email.getText().toString());
                        newProfileData.setPhoneNumber(phone.getText().toString());
                        // newProfileData.setProfileImageUrl();
                        vm.setProfileByEmail(email.getText().toString(), newProfileData);
                        // TODO : Update the profile picture too
                        // Navigate back to the profile screen with the committed changes
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                    }
                    else {
                        Log.d("TAG", "waiting for info");
                    }
                });
            }
        });

        return view;
    }

}
