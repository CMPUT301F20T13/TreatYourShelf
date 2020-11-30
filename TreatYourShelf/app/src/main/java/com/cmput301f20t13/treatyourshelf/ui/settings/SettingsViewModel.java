package com.cmput301f20t13.treatyourshelf.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cmput301f20t13.treatyourshelf.data.SettingsCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * the viewmodel that is used by the BottomSheetSettingsMenu
 */
public class SettingsViewModel extends AndroidViewModel {

    public List<SettingsCategory> categories;

    /**
     * initializes the viewmodel
     * @param application the current application
     */
    public SettingsViewModel(@NonNull Application application) {
        super(application);
        categories = new ArrayList<>();
    }

}
