package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileLiveData;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * BottomSheet which composes the navigation menu
 */
public class BottomSheetNavigationMenu extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_navigation, container, false);
        RecyclerView navigationRv = view.findViewById(R.id.navigation_rv);
        navigationRv.setLayoutManager(new LinearLayoutManager(requireContext()));

        navigationRv.setAdapter(new NavigationItemAdapter(setUpNavigationList(), requireContext(), navigationItem -> {

            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(navigationItem.getNavigationDestination());
            dismiss();
        }));
        ConstraintLayout constraintLayout = view.findViewById(R.id.profile_container);

        setUpProfilePreview(view);

        constraintLayout.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.profileFragment);
            dismiss();
        });
        return view;
    }

    /**
     * Sets up the navigation elements
     * @return the list of navigation items to pass to adapter
     */
    private List<NavigationItem> setUpNavigationList() {
        List<NavigationItem> navigationItems = new ArrayList<>();

        NavigationItem navigationItem = new NavigationItem(R.id.bookListFragment, R.drawable.ic_book_clock, "All Books");
        navigationItems.add(navigationItem);

        NavigationItem navigationItem2 = new NavigationItem(R.id.cameraXFragment, R.drawable.ic_qrcode, "Barcode Scanner");
        navigationItems.add(navigationItem2);

        NavigationItem navigationItem3 = new NavigationItem(R.id.ownedBooksFragment, R.drawable.ic_baseline_book_24, "My Owned Books");
        navigationItems.add(navigationItem3);

        NavigationItem navigationItem4 = new NavigationItem(R.id.borrRequestedListFragment, R.drawable.ic_round_book_online, "Requested Books");
        navigationItems.add(navigationItem4);

        return navigationItems;
    }

    private void setUpProfilePreview(View view) {
        // Get the textviews
        TextView username = (TextView) view.findViewById(R.id.usernameTV);
        TextView email = (TextView) view.findViewById(R.id.emailTV);
        ImageView image = (ImageView) view.findViewById(R.id.profile_image_bsnv);

        // Get currently logged in user email
        String profileEmail = "default email";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            profileEmail = user.getEmail();
        }

        // Query for the username and email
        ProfileLiveData pld = ProfileRepository.getProfileByEmailLiveData(profileEmail);
        pld.observe(getViewLifecycleOwner(), Observable -> {});

        // Retrieve the data and set the text
        pld.profile.observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                username.setText(profile.getUsername());
                email.setText(profile.getEmail());
                // Set the profile picture
                Glide.with(view).load(profile.getProfileImageUrl()).into(image);
            } else {
                Log.d("TAG", "waiting for info");
            }
        });

        // TODO : Finish adding the profile pictures to properly link to an actual png because so far it can't find the image
    }

}
