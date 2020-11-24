package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

/**
 * Data class for Navigation Item
 */
public class NavigationItem {

    private int navigationDestination;
    private int icon;
    private String destinationName;

    public NavigationItem(int navigationDestionation, int icon, String destinationName) {
        this.navigationDestination = navigationDestionation;
        this.icon = icon;
        this.destinationName = destinationName;
    }

    public int getNavigationDestination() {
        return navigationDestination;
    }

    public void setNavigationDestination(int navigationDestination) {
        this.navigationDestination = navigationDestination;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
}
