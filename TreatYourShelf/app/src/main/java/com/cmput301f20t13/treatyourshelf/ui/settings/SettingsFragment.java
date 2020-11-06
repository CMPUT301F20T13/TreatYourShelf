package com.cmput301f20t13.treatyourshelf.ui.settings;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.SettingsCategory;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private SettingsAdapter settingsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.settings_menu, container, false);

        RecyclerView settings_list = view.findViewById(R.id.settings_list);

        settings_list.setLayoutManager(new LinearLayoutManager(getContext()));
        settingsAdapter = new SettingsAdapter(new ArrayList<>());

        settings_list.setAdapter(settingsAdapter);
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        List<SettingsCategory> data = new ArrayList<>();
        settingsAdapter.setCategoryList(data);

        return view;
    }

}
