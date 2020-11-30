package com.cmput301f20t13.treatyourshelf.data;

import android.widget.ImageView;

/**
 * The Profile object that holds the information about a user's profile
 */
public class Profile {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private ImageView profileImage;

    /**
     * the profile of the book
     * @param username the username of the profile
     * @param password the password of the profile
     * @param email the email of the profile
     * @param phoneNumber the phone number of the profile
     */
    public Profile(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * the default constructor for the Profile
     */
    public Profile() {
        this.username = "default user";
        this.password = "password123";
        this.email = "default@gmail.com";
        this.phoneNumber = "555-555-5555";
    }

    /**
     * returns the username of the profile
     * @return the username of the profile
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username of the profile
     * @param username the provided username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns the password of the profile
     * @return the password of the profile
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password of the profile
     * @param password the provided password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * returns the email of the profile
     * @return the email of the profile
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email of the profile
     * @param email the provided email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns the phone number of the profile
     * @return the phone number of the profile
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets the phone number of the profile
     * @param phoneNumber the provided phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * returns the profile image of the profile
     * @return the profile image of the profile
     */
    public ImageView getProfileImage() {
        return profileImage;
    }

    /**
     * sets the profile image of the profile
     * @param profileImage the provided profile image
     */
    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
