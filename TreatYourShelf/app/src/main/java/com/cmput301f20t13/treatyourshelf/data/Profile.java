package com.cmput301f20t13.treatyourshelf.data;

import android.widget.ImageView;

/**
 * The Profile class. Stores all the necessary data for Profiles.
 */
public class Profile {
    // The fields
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;

    /**
     * Construct a profile given explicit parameters.
     * @param username      The unique username.
     * @param password      The password associated with the login.
     * @param email         The unique email.
     * @param phoneNumber   The unique phone number.
     */
    public Profile(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a default profile. Not recommended.
     */
    public Profile() {
        this.username = "default user";
        this.password = "password123";
        this.email = "default@gmail.com";
        this.phoneNumber = "555-555-5555";
    }

    /**
     * Gets the profile's username.
     * @return  The username String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the profile's username.
     * @param username  The username String.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the profile's password.
     * @return  The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the profile's password.
     * @param password  The password String.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the profile's email.
     * @return  The email String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the profile's email.
     * @param email The email String.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the profile's phone number.
     * @return  The phone number String.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the profile's phone number.
     * @param phoneNumber   The phone number String.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the profile's image URL.
     * @return  The image URL String.
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * Set the profile's image URL.
     * @param profileImageUrl   The image URL String.
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
