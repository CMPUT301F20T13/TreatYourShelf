package com.cmput301f20t13.treatyourshelf.data;

import androidx.fragment.app.Fragment;

public class SettingsCategory {

    private String icon;
    private String title;
    private int page;

    public SettingsCategory(String icon, String title, int page) {
        this.icon = icon;
        this.title = title;
        this.page = page;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public int getPage() {
        return page;
    }

}
