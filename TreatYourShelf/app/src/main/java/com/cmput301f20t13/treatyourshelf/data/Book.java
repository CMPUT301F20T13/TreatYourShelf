package com.cmput301f20t13.treatyourshelf.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;
import java.util.List;

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

    @Ignore
    public Book() {
        this.title = "default title";
        this.author = "default author";
        this.isbn = "default isbn";
    }

    public Book(String title, String author, String isbn, String description, String owner, List<String> imageUrls, String status) {

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.owner = owner;
        this.imageUrls = imageUrls;
        this.status = status;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrl) {
        this.imageUrls = imageUrl;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
