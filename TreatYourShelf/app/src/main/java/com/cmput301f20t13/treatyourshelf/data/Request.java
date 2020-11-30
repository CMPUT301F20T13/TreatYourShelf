package com.cmput301f20t13.treatyourshelf.data;


/**
 * the request object holds the information about a request for a book
 */

import java.util.List;

public class Request {

    private String requester;
    private String owner;
    private String bookId;
    private String status;
    private String isbn;
    private String author;
    private String title;
    private String location;
    private List<String> imageUrls;

    /**
     * returns the requester for the request
     * @return the requester for the request
     */
    public String getRequester() {
        return requester;
    }

    /**
     * sets the requester for the request
     * @param requester the provided requester
     */
    public void setRequester(String requester) { this.requester = requester; }

    /**
     * returns the owner of the book for the request
     * @return the owner of the book
     */
    public String getOwner() { return owner; }

    /**
     * sets the owner of the book for the request
     * @param owner the provided owner
     */
    public void setOwner(String owner) { this.owner = owner; }

    /**
     * returns the BookId of the book for the request
     * @return the bookid of the request
     */
    public String getBookId() { return bookId; }

    /**
     * sets the BookId of the book for the request
     * @param bookId the provided bookid
     */
    public void setBookId(String bookId) { this.bookId = bookId; }


    /**
     * returns the status of the request
     * @return the status of the book
     */
    public String getStatus() {
        return status;
    }


    /**
     * sets the status of the request
     * @param status the provided status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * returns the isbn of the book for the request
     * @return the isbn of the book
     */
    public String getIsbn() { return isbn; }

    /**
     * sets the isbn of the book for the request
     * @param isbn the provided isbn
     */
    public void setIsbn(String isbn) { this.isbn = isbn; }

    /**
     * returns the author of the book for the request
     * @return the author of the book
     */
    public String getAuthor() { return author; }

    /**
     * sets the author of the book for the request
     * @param author the provided author
     */
    public void setAuthor(String author) { this.author = author; }

    /**
     * returns the title of the book for the request
     * @return the title of the book
     */
    public String getTitle() { return title; }

    /**
     * sets the title of the book for the request
     * @param title the provided title
     */
    public void setTitle(String title) { this.title = title; }

    public List<String> getImageUrls() { return imageUrls; }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }
}
