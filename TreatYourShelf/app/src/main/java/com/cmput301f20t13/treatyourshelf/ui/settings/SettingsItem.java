package com.cmput301f20t13.treatyourshelf.ui.settings;

public class SettingsItem {

    private int icon;
    private String settingsOption;
    private String settingDescription;

    public SettingsItem(int icon, String settingsOption, String settingDescription) {

        this.icon = icon;
        this.settingDescription = settingDescription;
        this.settingsOption = settingsOption;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSettingsOption() {
        return settingsOption;
    }

    public void setSettingsOption(String settingsOption) {
        this.settingsOption = settingsOption;
    }

    public String getSettingDescription() {
        return settingDescription;
    }

    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }
}
