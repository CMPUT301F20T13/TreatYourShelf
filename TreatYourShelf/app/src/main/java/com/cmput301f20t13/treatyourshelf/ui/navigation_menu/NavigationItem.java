package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

/**
 * Data class for Navigation Item
 */
public class NavigationItem {

    private int navigationDestination;
    private int icon;
    private String destinationName;

    /**
     * constructor for the navigation item
     * @param navigationDestionation
     * @param icon
     * @param destinationName
     */
    public NavigationItem(int navigationDestionation, int icon, String destinationName) {
        this.navigationDestination = navigationDestionation;
        this.icon = icon;
        this.destinationName = destinationName;
    }

    /**
     * returns the navigation destination
     * @return the navigation destination
     */
    public int getNavigationDestination() {
        return navigationDestination;
    }

    /**
     * sets the navigation destination
     * @param navigationDestination the int that represents the navigation destination
     */
    public void setNavigationDestination(int navigationDestination) {
        this.navigationDestination = navigationDestination;
    }

    /**
     * returns the icon
     * @return the icon
     */
    public int getIcon() {
        return icon;
    }

    /**
     * sets the icon
     * @param icon the int that represents the icon
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * returns the destination name
     * @return the destination name
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * sets the destination name
     * @param destinationName the provided destination name
     */
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
}
