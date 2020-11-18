package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListRepository;

import java.util.List;

public class AddBookViewModel extends AndroidViewModel {
    private final AddBookRepository repository = new AddBookRepository();
    MutableLiveData<String> scannedIsbn = new MutableLiveData<>();
    AddBookLiveData liveData = null;
    MutableLiveData<List<ImageFilePathSelector>> listMutableLiveDataGalleryImages = new MutableLiveData<>();

    public AddBookViewModel(@NonNull Application application) {
        super(application);
    }


    public AddBookLiveData getBookBYIsbn(String isbn) {
        liveData = repository.getBookByIsbnLiveData(isbn);
        return liveData;
    }

    public LiveData<Book> getBook() {
        return liveData.book;
    }

    public void addBook(Book book) {
        repository.addBook(book);
    }

    public void deleteBook(String isbn) {
        repository.deleteBook(isbn);
    }

    public void editBook(Book book) {
        repository.editBook(book);
    }

    public void clearScannedIsbn() {
        scannedIsbn = new MutableLiveData<>();
    }

    public void setScannedIsbn(String scannedIsbn) {
        this.scannedIsbn.postValue(scannedIsbn);
    }


    public void getGalleryImages() {
        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new LoadGalleryImagesTask(getApplication().getApplicationContext()), (data) -> {
            listMutableLiveDataGalleryImages.setValue(data);
        });
    }
}
