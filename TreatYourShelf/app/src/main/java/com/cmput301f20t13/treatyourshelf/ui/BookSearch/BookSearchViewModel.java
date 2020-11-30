package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;

/**
 * the viewmodel used by the BookSearchFragment, contains the methods called by the BooksearchFragment
 * for searching
 */
public class BookSearchViewModel extends ViewModel {

    private final BookSearchRepository repository = new BookSearchRepository();
    BookSearchLiveData liveData = null;
    MutableLiveData<List<Book>> liveBookList = new MutableLiveData<>();

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

    /**
     * calls the repository function that provides the searching, updates
     * the MutableLiveData liveBookList with the results
     * @param keyword (String): user entered keyword
     * @param owner (String): the username of the current user
     */
    public void getBookSearch(String keyword, String owner) {
        //failed here
        liveBookList.setValue(repository.searchBooks(keyword, owner));
    }

    public LiveData<List<Book>> getAllBooksLiveData() {
        liveData = repository.getAllBooksLiveData();
        return liveData;
    }

    public LiveData<List<Book>> getBookList() {
        return liveData.bookList;
    }
}
