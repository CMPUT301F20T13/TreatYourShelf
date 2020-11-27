package com.cmput301f20t13.treatyourshelf.ui.BookList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;

import java.util.List;

public class BookListViewModel extends ViewModel {

    private final BookListRepository repository = new BookListRepository();
    BookListLiveData liveData = null;
    public MutableLiveData<Book> selectedBook = new MutableLiveData<>();
    public boolean ownerList = true;

    public LiveData<List<Book>> getBookByIsbnLiveData(String isbn) {
        liveData = repository.getBookByIsbnLiveData(isbn);
        return liveData;
    }

    public LiveData<List<Book>> getBookByOwnerLiveData(String owner) {
        liveData = repository.getBookByOwnerLiveData(owner);
        ownerList = true;
        return liveData;
    }

    public LiveData<List<Book>> getAllBooksLiveData() {
        liveData = repository.getAllBooksLiveData();
        ownerList = false;
        return liveData;
    }

    public LiveData<List<Book>> getBookList() {
        return liveData.bookList;
    }

    public void select(Book book){selectedBook.setValue(book);}

    public LiveData<Book> getSelected() {return selectedBook;}

    public void clearLiveData() { liveData = null; }
}
