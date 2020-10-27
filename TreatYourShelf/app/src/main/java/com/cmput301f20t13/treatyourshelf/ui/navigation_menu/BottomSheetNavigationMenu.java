package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetNavigationMenu extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet_navigation, container, false);
        RecyclerView navigationRv = view.findViewById(R.id.navigation_rv);
        navigationRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        navigationRv.setAdapter(new NavigationItemAdapter(setUpNavigationList(),requireContext()));
        return view;
    }

    private List<NavigationItem> setUpNavigationList() {
        List<NavigationItem> navigationItems = new ArrayList<NavigationItem>();
        NavigationItem navigationItem = new NavigationItem(R.id.cameraXFragment, R.drawable.ic_qrcode, "Barcode Scanner");
        navigationItems.add(navigationItem);
        NavigationItem navigationItem2 = new NavigationItem(R.id.cameraXFragment, R.drawable.ic_qrcode, "Barcode Scanner");
        navigationItems.add(navigationItem2);
        return navigationItems;

    }

}
