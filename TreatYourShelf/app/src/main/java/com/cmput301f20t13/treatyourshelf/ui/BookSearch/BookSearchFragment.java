package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * the fragment that allows a borrower to specify a keyword, and search for all books that
 * are not currently accepted or borrowed whose description contains the keyword.
 */
public class BookSearchFragment extends Fragment {
    private BookSearchAdapter bookSearchAdapter;
    private List<Book> tempAllBooks;

    /**
     * Creates the fragment view
     *
     * @param inflater           inflates the view in the fragment
     * @param container          the viewgroup
     * @param savedInstanceState a bundle
     * @return returns the view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_book, container, false);

        BookSearchViewModel bookSearchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BookSearchViewModel.class);
        ArrayList<Book> bookArray = new ArrayList<>();
        bookSearchAdapter = new BookSearchAdapter(bookArray);
        EditText searchBar = view.findViewById(R.id.search_text);
        Button searchButton = view.findViewById(R.id.search_button);
        RecyclerView bookRv = view.findViewById(R.id.search_list_rv);
      
        Profile testProfile = new Profile();
        String owner = testProfile.getUsername();
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookSearchViewModel.liveBookList.observe(getViewLifecycleOwner(), Observable -> {
        });

        bookSearchViewModel.liveBookList.observe(getViewLifecycleOwner(), liveBookList -> {
            bookSearchAdapter.setBookList(liveBookList);
            bookRv.setAdapter(bookSearchAdapter);


        });

        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.clearLiveData();
        bookListViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), Observable -> {
        });
        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null) {
                // Reset adapter by setting it with a new list
                bookSearchViewModel.setAllBooks(bookList);
            } else {
                Log.d("TAG", "waiting for info");
            }
        });

        // Run code on every character change in the search bar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = searchBar.getText().toString();
                bookSearchViewModel.searchBooks(keyword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }
}