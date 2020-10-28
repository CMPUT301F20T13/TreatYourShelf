package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BottomSheetNavigationMenu extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet_navigation, container, false);
        RecyclerView navigationRv = view.findViewById(R.id.navigation_rv);
        navigationRv.setLayoutManager(new LinearLayoutManager(requireContext()));

        navigationRv.setAdapter(new NavigationItemAdapter(setUpNavigationList(), requireContext(), navigationItem -> {

            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(navigationItem.getNavigationDestination());
            dismiss();
        }));
        return view;
    }

    private List<NavigationItem> setUpNavigationList() {
        List<NavigationItem> navigationItems = new ArrayList<NavigationItem>();
        NavigationItem navigationItem = new NavigationItem(R.id.borrReqBooksFragment, R.drawable.ic_book_clock, "My Requests");
        navigationItems.add(navigationItem);
        NavigationItem navigationItem2 = new NavigationItem(R.id.cameraXFragment, R.drawable.ic_qrcode, "Barcode Scanner");
        navigationItems.add(navigationItem2);
        return navigationItems;

    }

}