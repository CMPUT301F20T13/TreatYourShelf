package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BookListViewModel extends ViewModel {

    private BookListRepository repository = new BookListRepository();
    public MutableLiveData<List<Book>> bookList = new MutableLiveData<List<Book>>();
    BookListLiveData liveData = null;

    public LiveData<List<Book>> getBookListLiveData() {
        liveData = repository.getFirestoreLiveData();
        return liveData;
    }

    public LiveData<List<Book>> getBookList() {
        return liveData.bookList;
    }
}
