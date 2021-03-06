package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The Fragment for the Profile display screen.
 */
public class ProfileFragment extends Fragment {

    /**
     * Called when the fragment is navigated to.
     * @param inflater              Inflates the screen
     * @param container             The viewgroup container.
     * @param savedInstanceState    The instance state.
     * @return                      The inflated view, this is fragment_profile.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment_profile screen
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Get the necessary Views in the Fragment
        ImageView image = view.findViewById(R.id.profile_image);
        TextView username = view.findViewById(R.id.profile_username);

        TableLayout profileInfo = view.findViewById(R.id.profile_info_table);
        TextView email = view.findViewById(R.id.profile_email);
        TextView phone = view.findViewById(R.id.profile_phone);
        Button editButton = view.findViewById(R.id.profile_edit_button);

        // Get the current FirebaseAuth's email if not passed another email
        String profileEmail = ProfileFragmentArgs.fromBundle(getArguments()).getEmail();
        if (profileEmail.isEmpty()) {
            profileEmail = "user1@gmail.com";
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                profileEmail = user.getEmail();
            }
        } else {
            editButton.setVisibility(View.INVISIBLE);
        }

        // Get the details from the Firestore to set in the viewmodel
        ProfileViewModel profileViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel.class);
        profileViewModel.getProfileByEmailLiveData(profileEmail).observe(getViewLifecycleOwner(), Observable -> {});

        // Set the View's values from the ViewModel's data
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
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

        // Add a listener for the edit button

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.profileEditFragment);
            }
        });
        return view;
    }
}
