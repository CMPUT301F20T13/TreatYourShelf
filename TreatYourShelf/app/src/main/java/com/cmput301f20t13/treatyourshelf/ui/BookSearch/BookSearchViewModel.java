package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;

public class BookSearchViewModel extends ViewModel {

    private final BookSearchRepository repository = new BookSearchRepository();
    BookSearchLiveData liveData = null;

    public BookSearchLiveData getBookByIsbnLiveData(String isbn) {
        liveData = repository.getBookByIsbnLiveData(isbn);
        return liveData;
    }

    public BookSearchLiveData getBookByOwnerLiveData(String owner) {
        liveData = repository.getBookByOwnerLiveData(owner);
        return liveData;
    }

    public BookSearchLiveData getBookByTitleLiveData(String title) {
        liveData = repository.getBookByOwnerLiveData(title);
        return liveData;
    }

    public LiveData<List<Book>> getAllBooksLiveData() {
        liveData = repository.getAllBooksLiveData();
        return liveData;
    }

    public LiveData<List<Book>> getBookList() {
        return liveData.bookList;
    }
}
