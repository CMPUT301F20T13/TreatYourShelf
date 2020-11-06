package com.cmput301f20t13.treatyourshelf.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cmput301f20t13.treatyourshelf.data.SettingsCategory;

import java.util.ArrayList;
import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    public List<SettingsCategory> categories;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        categories = new ArrayList<>();
    }

}
