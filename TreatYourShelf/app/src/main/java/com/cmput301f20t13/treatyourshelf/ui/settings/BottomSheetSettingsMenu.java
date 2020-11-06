package com.cmput301f20t13.treatyourshelf.ui.settings;

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
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItem;
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItemAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetSettingsMenu extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_settings_menu, container, false);
        RecyclerView settingsRv = view.findViewById(R.id.settings_rv);
        settingsRv.setLayoutManager(new LinearLayoutManager(requireContext()));

        settingsRv.setAdapter(new SettingsAdapter(setUpSettingsMenu(), requireContext(), settingsItem -> {

            // closes bottom sheet
            dismiss();
        }));

        return view;
    }

    private List<SettingsItem> setUpSettingsMenu() {
        List<SettingsItem> settingsItems = new ArrayList<>();

        SettingsItem settingsItem = new SettingsItem(R.drawable.ic_round_exit_to_app_24, "Log Out", "Log out of your account.");

        settingsItems.add(settingsItem);

        return settingsItems;

    }
}
