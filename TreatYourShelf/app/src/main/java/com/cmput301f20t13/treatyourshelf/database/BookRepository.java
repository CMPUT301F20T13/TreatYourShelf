package com.cmput301f20t13.treatyourshelf.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;

public class BookRepository {


    private BookDao bookDao;
    private LiveData<List<Book>> bookList;

    public BookRepository(Application application) {
        BookDatabase bookDatabase = BookDatabase.getDatabase(application);
        bookDao = bookDatabase.bookDao();
        bookList = bookDao.getAllBooks();
    }

    public LiveData<List<Book>> getBookList() {
        return bookList;
    }

    public void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.insert(book);
        });
    }
}
