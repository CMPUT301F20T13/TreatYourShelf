package com.cmput301f20t13.treatyourshelf.ui.settings;

/**
 * the SettingsItem object used in the settings package
 */
public class SettingsItem {

    private int icon;
    private String settingsOption;
    private String settingDescription;

    /**
     * the constructor of the object
     * @param icon the int that represents the icon of the setting
     * @param settingsOption the string that represents name of the setting
     * @param settingDescription the string that describes the function of the setting
     */
    public SettingsItem(int icon, String settingsOption, String settingDescription) {

        this.icon = icon;
        this.settingDescription = settingDescription;
        this.settingsOption = settingsOption;
    }

    /**
     * returns the icon of the object
     * @return the int that represents the object
     */
    public int getIcon() {
        return icon;
    }

    /**
     * sets the icon of the object
     * @param icon the provided int that represents the object
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * returns the string that represents name of the setting
     * @return the string that represents name of the setting
     */
    public String getSettingsOption() {
        return settingsOption;
    }

    /**
     * sets the string that represents name of the setting
     * @param settingsOption the provided the string that represents name of the setting
     */
    public void setSettingsOption(String settingsOption) {
        this.settingsOption = settingsOption;
    }

    /**
     * returns the string that describes the function of the setting
     * @return the string that describes the function of the setting
     */
    public String getSettingDescription() {
        return settingDescription;
    }

    /**
     * sets the string that describes the function of the setting
     * @param settingDescription the provided the string that describes the function of the setting
     */
    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }
}
