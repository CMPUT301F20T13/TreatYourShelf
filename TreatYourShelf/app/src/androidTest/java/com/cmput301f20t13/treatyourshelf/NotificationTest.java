package com.cmput301f20t13.treatyourshelf;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301f20t13.treatyourshelf.data.Notification;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Notification.
 * Unit test to cover each function of Notification.
 */
@RunWith(AndroidJUnit4.class)
public class NotificationTest {
    Notification testNotification;

    /**
     * Runs before all tests and clears reference of testNotification.
     */
    @Before
    public void clearNotification(){
        testNotification = null;
    }

    /**
     * Creates a notification with arguments and test getting all the members back
     */
    @Test
    public void testConstructorWithArguments() throws JSONException {
        String title = "title";
        String message = "message";
        String user = "user";
        String topic = "/topics/" + user;
        testNotification = new Notification(title, message, user);
        assertEquals(testNotification.getNotification().getJSONObject("data").getString(title), title);
        assertEquals(testNotification.getNotification().getJSONObject("data").getString(message), message);
        assertEquals(testNotification.getNotification().getString("to"), topic);
    }

}