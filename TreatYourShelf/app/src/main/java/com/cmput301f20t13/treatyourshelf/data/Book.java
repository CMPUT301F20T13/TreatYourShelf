package com.cmput301f20t13.treatyourshelf.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;
import java.util.List;

/**
 * The book object that holds the information about a book
 */
@Entity
public class Book implements Serializable {


    private String title;
    private String author;
    private String isbn;
    private String description;
    private List<String> imageUrls;
    private String owner;
    private String borrower;
    private String status;

    /**
     * creates the book with default values, no longer used
     */
    @Ignore
    public Book() {
        this.title = "default title";
        this.author = "default author";
        this.isbn = "default isbn";
    }

    /**
     * the constructor of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the isbn of the book
     * @param description the description of the book
     * @param owner the owner of the book
     * @param imageUrls the imageurl of the book
     * @param status the status of the book, available, required, accepted or borrowed
     */
    public Book(String title, String author, String isbn, String description, String owner, List<String> imageUrls, String status) {

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.owner = owner;
        this.imageUrls = imageUrls;
        this.status = status;
    }

    /**
     * returns the title of the book
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the book
     * @param title the provided title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns the author of the book
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    
    /**
     * sets the author of the book
     * @param author the provided author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * returns the description of the book
     * @return the description of the book
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the book
     * @param description the provided description of the book
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the imageurl of the book
     * @return the imageurl of the book
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     * sets the imageurl of the book
     * @param imageUrl the provided imageurl of the book
     */
    public void setImageUrls(List<String> imageUrl) {
        this.imageUrls = imageUrl;
    }

    /**
     * returns the isbn of the book
     * @return the isbn of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * sets the isbn of the book
     * @param isbn the provided isbn of the book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * sets the owner of the book
     * @param owner the provided owner of the book
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * returns the owner of the book
     * @return the owner of the book
     */
    public String getOwner() {
        return owner;
    }

    /**
     * sets the borrower of the book
     * @param borrower the provided borrower of the book
     */
    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    /**
     * returns the borrower of the book
     * @return the borrower of the book
     */
    public String getBorrower() {
        return borrower;
    }

    /**
     * sets the status of the book
     * @param status the provided status of the book
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * returns the status of the book
     * @return the status of the book
     */
    public String getStatus() {
        return status;
    }

}
