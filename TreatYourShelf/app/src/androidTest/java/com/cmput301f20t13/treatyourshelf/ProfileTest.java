package com.cmput301f20t13.treatyourshelf;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301f20t13.treatyourshelf.data.Profile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Profile().
 * Unit test to cover each function of Profile().
 */
@RunWith(AndroidJUnit4.class)
public class ProfileTest {
    Profile testProfile;

    /**
     * Runs before all tests and clears reference of testProfile.
     */
    @Before
    public void clearProfile(){
        testProfile = null;
    }

    /**
     * Creates a profile with arguments and tests getting all the members back
     */
    @Test
    public void testConstructorWithArguments() {
        String username = "username";
        String password = "password";
        String email = "email";
        String phoneNumber = "123-456-7891";
        testProfile = new Profile(username, password, email, phoneNumber);
        assertEquals(testProfile.getUsername(), username);
        assertEquals(testProfile.getPassword(), password);
        assertEquals(testProfile.getEmail(), email);
        assertEquals(testProfile.getPhoneNumber(), phoneNumber);
    }

    /**
     * Creates a profile with no arguments and tests getting all the members back
     */
    @Test
    public void testConstructorWithNoArguments() {
        String username = "default user";
        String password = "password123";
        String email = "default@gmail.com";
        String phoneNumber = "555-555-5555";
        testProfile = new Profile();
        assertEquals(testProfile.getUsername(), username);
        assertEquals(testProfile.getPassword(), password);
        assertEquals(testProfile.getEmail(), email);
        assertEquals(testProfile.getPhoneNumber(), phoneNumber);
    }

    /**
     * Creates a profile with no arguments and tests setting then getting all the members back
     */
    @Test
    public void testConstructorWithNoArgumentsSetGet() {
        String username = "username";
        String password = "password";
        String email = "email";
        String phoneNumber = "123-456-7891";
        testProfile = new Profile();
        testProfile.setUsername(username);
        testProfile.setPassword(password);
        testProfile.setEmail(email);
        testProfile.setPhoneNumber(phoneNumber);
        assertEquals(testProfile.getUsername(), username);
        assertEquals(testProfile.getPassword(), password);
        assertEquals(testProfile.getEmail(), email);
        assertEquals(testProfile.getPhoneNumber(), phoneNumber);
    }
}