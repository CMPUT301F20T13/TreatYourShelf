package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.database.BookRepository;

import java.util.List;

public class BookDetailsViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    public LiveData<List<Book>> bookList;

    public BookDetailsViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        bookList = bookRepository.getBookList();
    }

    public void insert(Book book){
        bookRepository.insert(book);
    }

}
