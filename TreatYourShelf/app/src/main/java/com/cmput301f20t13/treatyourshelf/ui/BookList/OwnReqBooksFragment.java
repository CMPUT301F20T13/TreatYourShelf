package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;

/**
 * This class was copied exactly from BookListFragment.
 * The only difference is the data that is stored in the list,
 * AND which fragment opens up when the user taps on a book in the list. (Borrower or Owner)
 *
 */
public class OwnReqBooksFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    private OwnReqBooksViewModel ownReqBooksViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListAdapter = new BookListAdapter(new ArrayList<>());
        bookRv.setAdapter(bookListAdapter);
        ownReqBooksViewModel = new ViewModelProvider(this).get(OwnReqBooksViewModel.class);

        ownReqBooksViewModel.bookList.observe(getViewLifecycleOwner(), books -> {
            if (books.isEmpty()) {
                // Initial list will be empty due to no data stored in the app, therefore we insert data into the database
                setInitialData();
            }

            bookListAdapter.setBookList(books);
        });

        return view;
    }

    /**
     * The list of books displayed will be all books currently requested by
     * the borrower.
     */
    private void setInitialData() {

        Book book = new Book("This is the Owners Requested Books list", "Me");
        ownReqBooksViewModel.insert(book);
        book = new Book("Star Wars: Thrawn Ascendancy (Book I: Chaos Rising)", "Timothy Zahn");
        ownReqBooksViewModel.insert(book);

    }
}
