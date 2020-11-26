package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListRepository;


import java.util.List;

public class BookDetailsViewModel extends ViewModel {
    private final BookDetailsRepository repository = new BookDetailsRepository();

    public void requestBook(Book book, String requester) {
        repository.addRequest(book,requester);

    }
}
