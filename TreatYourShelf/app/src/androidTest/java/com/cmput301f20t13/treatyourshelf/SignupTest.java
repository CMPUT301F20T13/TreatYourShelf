package com.cmput301f20t13.treatyourshelf;

import android.app.Activity;

import androidx.navigation.Navigation;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for LoginFragment. All the UI tests are written here. Robotium test framework.
 * Referenced Lab 7
 *
 *  IMPORTANT: Assumes that the navigation controller is set to Signup Fragement and there is no user signed in currently
 *  IF there is, clear app data first.
 */
@RunWith(AndroidJUnit4.class)
public class SignupTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the Activity
     *
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Attempts to signup with invalid email and password
     */
    @Test
    public void blankEmail() {

        solo.enterText((EditText) solo.getView(R.id.edit_et), "");
        solo.enterText((EditText) solo.getView(R.id.password_et), "INVALID_PASSWORD");
        solo.clickOnButton("SIGN UP"); // Attempt to login

        assertTrue(solo.waitForText("SIGN UP", 1, 2000));
    }

    /**
     * Attempts to login with invalid  password
     */
    @Test
    public void blankPassword() {

        solo.enterText((EditText) solo.getView(R.id.edit_et), "");
        solo.enterText((EditText) solo.getView(R.id.password_et), "INVALID_PASSWORD");
        solo.clickOnButton("SIGN UP"); // Attempt to login

        assertTrue(solo.waitForText("SIGN UP", 1, 2000));
    }

    /**
     * Attempts to login with valid email and password
     */
    @Test
    public void preexistingEmail() {

        solo.enterText((EditText) solo.getView(R.id.edit_et), "antonsucks@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_et), "testing123");
        solo.clickOnButton("SIGN UP"); // Attempt to login

        assertTrue(solo.waitForText("SIGN UP", 1, 2000));
    }


}