package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListRepository;

public class AddBookViewModel extends ViewModel {
    private final BookListRepository repository = new BookListRepository();
    AddBookLiveData liveData = null;

    public AddBookLiveData getBookBYIsbn(String isbn) {
        liveData = repository.getBookByIsbnLiveData(isbn);
        return liveData;
    }

    public LiveData<Book> getBook() {
        return liveData.book;
    }

}
