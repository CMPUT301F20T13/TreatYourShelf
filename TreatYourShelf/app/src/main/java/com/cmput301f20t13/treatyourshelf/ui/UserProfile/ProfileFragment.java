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

import com.cmput301f20t13.treatyourshelf.R;


public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView image = view.findViewById(R.id.profile_image);
        TextView username = view.findViewById(R.id.profile_username);

        TableLayout profileInfo = view.findViewById(R.id.profile_info_table);
        TextView email = view.findViewById(R.id.profile_email);
        TextView phone = view.findViewById(R.id.profile_phone);

        String profileUsername = "user1"; // TODO: profileUsername should be set with intent of fragment

        ProfileViewModel profileViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel.class);

        profileViewModel.getProfileByUsernameLiveData(profileUsername).observe(getViewLifecycleOwner(), Observable -> {});

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null ) {
                username.setText(profile.getUsername());
                email.setText(profile.getEmail());
                phone.setText(profile.getPhoneNumber());
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        Button editButton = view.findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Not implemented yet
            }
        });
        return view;
    }
}
