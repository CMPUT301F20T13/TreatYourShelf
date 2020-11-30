package com.cmput301f20t13.treatyourshelf.data;

public class SettingsCategory {

    private String icon;
    private String title;
    private int page;

    public SettingsCategory(String icon, String title, int page) {
        this.icon = icon;
        this.title = title;
        this.page = page;
    }

    public SettingsCategory() {
        icon = "icon";
        title = "title";
        page = 0;
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

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
