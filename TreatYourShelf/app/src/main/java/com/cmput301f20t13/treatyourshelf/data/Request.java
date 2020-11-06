package com.cmput301f20t13.treatyourshelf.data;

public class Request {

    private String username;
    private String bookId;
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getBookId() { return bookId; }

    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
