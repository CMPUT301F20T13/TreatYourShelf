package com.cmput301f20t13.treatyourshelf;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301f20t13.treatyourshelf.data.Book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Book.
 * Unit test to cover each function of Book.
 */
@RunWith(AndroidJUnit4.class)
public class BookTest {
    Book testBook;

    /**
     * Runs before all tests and clears reference of testBook.
     */
    @Before
    public void clearBook(){
        testBook = null;
    }

    /**
     * Creates a book with arguments and tests getting all the members back
     */
    @Test
    public void testConstructorWithArguments() {
        String title = "testTitle";
        String author = "testAuthor";
        String isbn = "1234567891011";
        String description = "testDescription";
        String owner = "testOwner";
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://testURL.com");
        String status = "testStatus";
        testBook = new Book(title, author, isbn, description, owner, imageUrls, status);
        assertEquals(testBook.getTitle(), title);
        assertEquals(testBook.getAuthor(), author);
        assertEquals(testBook.getIsbn(), isbn);
        assertEquals(testBook.getDescription(), description);
        assertEquals(testBook.getOwner(), owner);
        assertEquals(testBook.getImageUrls(), imageUrls);
        assertEquals(testBook.getStatus(), status);
    }

    /**
     * Creates a book with no arguments and tests getting all the members back
     */
    @Test
    public void testConstructorWithNoArguments() {
        String title = "default title";
        String author = "default author";
        String isbn = "default isbn";
        testBook = new Book();
        assertEquals(testBook.getTitle(), title);
        assertEquals(testBook.getAuthor(), author);
        assertEquals(testBook.getIsbn(), isbn);
    }

    /**
     * Creates a book with no arguments and tests setting then getting all the members back
     */
    @Test
    public void testConstructorWithNoArgumentsSetGet() {
        String title = "testTitle";
        String author = "testAuthor";
        String isbn = "1234567891011";
        String description = "testDescription";
        String owner = "testOwner";
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://testURL.com");
        String status = "testStatus";
        testBook = new Book();
        testBook.setTitle(title);
        testBook.setAuthor(author);
        testBook.setIsbn(isbn);
        testBook.setDescription(description);
        testBook.setOwner(owner);
        testBook.setImageUrls(imageUrls);
        testBook.setStatus(status);
        assertEquals(testBook.getTitle(), title);
        assertEquals(testBook.getAuthor(), author);
        assertEquals(testBook.getIsbn(), isbn);
        assertEquals(testBook.getDescription(), description);
        assertEquals(testBook.getOwner(), owner);
        assertEquals(testBook.getImageUrls(), imageUrls);
        assertEquals(testBook.getStatus(), status);
    }
}