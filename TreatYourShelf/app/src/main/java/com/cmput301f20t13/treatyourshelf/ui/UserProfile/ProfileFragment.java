package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;

import java.util.List;


public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView image = view.findViewById(R.id.profile_image);
        TextView username = view.findViewById(R.id.profile_username);

        TableLayout profileInfo = view.findViewById(R.id.profile_info_table);
        TextView email = view.findViewById(R.id.profile_email);
        TextView phone = view.findViewById(R.id.profile_phone);


        Button editButton = view.findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.editProfile();
            }
        });

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);


        return view;
    }


}
