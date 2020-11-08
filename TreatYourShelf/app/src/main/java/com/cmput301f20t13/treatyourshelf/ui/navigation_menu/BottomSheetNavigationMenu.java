package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
        return navigationItems;

    }

}
