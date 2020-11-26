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

public class ProfileEditFragment extends Fragment {

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

        final String oldUsername = username.getText().toString();

        // Add functionality for the buttons, which is the most important part here
        Button changepfp = (Button) view.findViewById(R.id.profile_image_edit_button);
        changepfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Change the user's profile picture
            }
        });

        Button cancel = (Button) view.findViewById(R.id.profile_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                // dismiss();
            }
        });

        Button done = (Button) view.findViewById(R.id.profile_done_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Navigate back to the profile screen with the changes committed
                String newUsername = username.getText().toString();

                // Check if the newUsername is valid or not
                vm.isUsernameAvailableLiveData(newUsername).observe(getViewLifecycleOwner(), Observable -> {});

                // Set the View's values from the ViewModel's data
                vm.getProfile().observe(getViewLifecycleOwner(), profile -> {
                    if (profile != null ) {
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
