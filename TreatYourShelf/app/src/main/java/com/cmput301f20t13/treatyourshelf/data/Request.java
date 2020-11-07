package com.cmput301f20t13.treatyourshelf.data;

public class Request {

    enum Status{Accepted, Undetermined, Declined}

    private String username;
    private String bookId;
    private Status status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getBookId() { return bookId; }

    public void setBookId(String bookId) { this.bookId = bookId; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
