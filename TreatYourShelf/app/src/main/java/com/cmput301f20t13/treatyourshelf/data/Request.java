package com.cmput301f20t13.treatyourshelf.data;

public class Request {

    private String requester;
    private String owner;
    private String bookId;
    private String status;
    private String isbn;
    private String author;
    private String title;

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) { this.requester = requester; }

    public String getOwner() { return owner; }

    public void setOwner(String owner) { this.owner = owner; }

    public String getBookId() { return bookId; }

    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
