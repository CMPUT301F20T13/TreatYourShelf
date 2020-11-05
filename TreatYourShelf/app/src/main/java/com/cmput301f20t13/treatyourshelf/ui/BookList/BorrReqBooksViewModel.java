package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.database.BookRepository;

import java.util.List;

/**
 * This class was copied exactly from BookListViewModel.
 * The only difference is the data that is stored in the list,
 * AND which fragment opens up when the user taps on a book in the list. (Borrower or Owner)
 *
 */
public class BorrReqBooksViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    public LiveData<List<Book>> bookList;

    public BorrReqBooksViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        bookList = bookRepository.getBookList();
    }

    public void insert(Book book){
        bookRepository.insert(book);
    }
}