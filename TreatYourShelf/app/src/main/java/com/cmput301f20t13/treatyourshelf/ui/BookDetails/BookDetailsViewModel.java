package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.AddBookLiveData;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.AddBookRepository;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.ImageFilePathSelector;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.LoadGalleryImagesTask;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.TaskRunner;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BookDetailsViewModel extends AndroidViewModel {
    private final BookListRepository bookListRepository = new BookListRepository();
    private final BookDetailsRepository bookDetailsRepository = new BookDetailsRepository();
    BookListLiveData bookListLiveData = null;
    BookDetailsLiveData bookDetailsLiveData = null;

    public BookDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Book>> getBookByIsbnLiveData(String isbn) {
        bookListLiveData = bookListRepository.getBookByIsbnLiveData(isbn);
        return bookListLiveData;
    }

    public LiveData<Book> getBookByIsbnOwner(String isbn, String owner){
        bookDetailsLiveData = bookDetailsRepository.getBookByIsbnOwner(isbn, owner);
        return bookDetailsLiveData;
    }

    public LiveData<Book> getBook() {
        return bookDetailsLiveData.book;
    }

    public LiveData<List<Book>> getBookList() {
        return bookListLiveData.bookList;
    }


    public void clearLiveData() {
        bookListLiveData = null;
        bookDetailsLiveData = null;
    }
}
