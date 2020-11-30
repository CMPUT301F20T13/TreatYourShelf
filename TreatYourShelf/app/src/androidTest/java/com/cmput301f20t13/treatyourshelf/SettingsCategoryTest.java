package com.cmput301f20t13.treatyourshelf;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301f20t13.treatyourshelf.data.SettingsCategory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Test class for SettingsCategory().
 * Unit test to cover each function of SettingsCategory().
 */
@RunWith(AndroidJUnit4.class)
public class SettingsCategoryTest {
    SettingsCategory testSettingsCategory;

    /**
     * Runs before all tests and clears reference of testSettingsCategory.
     */
    @Before
    public void clearSettingsCategory(){
        testSettingsCategory = null;
    }

    /**
     * Creates a profile with arguments and tests getting all the members back
     */
    @Test
    public void testConstructorWithArguments() {
        String icon = "icon";
        String title = "title";
        int page = 0;
        testSettingsCategory = new SettingsCategory(icon, title, page);
        assertEquals(testSettingsCategory.getIcon(), icon);
        assertEquals(testSettingsCategory.getTitle(), title);
        assertEquals(testSettingsCategory.getPage(), page);
    }

    /**
     * Creates a SettingsCategory with no arguments and tests getting all the members back
     */
    @Test
    public void testConstructorWithNoArguments() {
        String icon = "icon";
        String title = "title";
        int page = 0;
        testSettingsCategory = new SettingsCategory();
        assertEquals(testSettingsCategory.getIcon(), icon);
        assertEquals(testSettingsCategory.getTitle(), title);
        assertEquals(testSettingsCategory.getPage(), page);
    }

    /**
     * Creates a SettingsCategory with no arguments and tests setting then getting all the members back
     */
    @Test
    public void testConstructorWithNoArgumentsSetGet() {
        String icon = "icon";
        String title = "title";
        int page = 0;
        testSettingsCategory = new SettingsCategory();
        testSettingsCategory.setIcon(icon);
        testSettingsCategory.setTitle(title);
        testSettingsCategory.setPage(page);
        assertEquals(testSettingsCategory.getIcon(), icon);
        assertEquals(testSettingsCategory.getTitle(), title);
        assertEquals(testSettingsCategory.getPage(), page);
    }
}