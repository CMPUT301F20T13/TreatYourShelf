package com.cmput301f20t13.treatyourshelf;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301f20t13.treatyourshelf.data.Request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Request().
 * Unit test to cover each function of Request().
 */
@RunWith(AndroidJUnit4.class)
public class RequestTest {
    Request testRequest;

    /**
     * Runs before all tests and clears reference of testRequest.
     */
    @Before
    public void clearRequest(){
        testRequest = null;
    }

    /**
     * Creates a request with no arguments and tests setting then getting all the members back
     */
    @Test
    public void testConstructorWithNoArgumentsSetGet() {
        String requester = "requester";
        String owner = "owner";
        String bookId = "bookId";
        String status = "status";
        String isbn = "isbn";
        String author = "author";
        String title = "title";
        String location = "location";
        testRequest = new Request();
        testRequest.setRequester(requester);
        testRequest.setOwner(owner);
        testRequest.setBookId(bookId);
        testRequest.setStatus(status);
        testRequest.setIsbn(isbn);
        testRequest.setAuthor(author);
        testRequest.setTitle(title);
        testRequest.setLocation(location);
        assertEquals(testRequest.getIsbn(), isbn);
        assertEquals(testRequest.getAuthor(), author);
        assertEquals(testRequest.getTitle(), title);
        assertEquals(testRequest.getLocation(), location);
    }
}