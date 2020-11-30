package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * the fragment that allows a borrower to specify a keyword, and search for all books that
 * are not currently accepted or borrowed whose description contains the keyword.
 */
public class BookSearchFragment extends Fragment {
    private BookSearchAdapter bookSearchAdapter;

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
        //test profile as a placeholder, do not want to return own books for a borrower
        Profile testProfile = new Profile();
        String owner = testProfile.getUsername();
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookSearchViewModel.liveBookList.observe(getViewLifecycleOwner(), Observable -> {});

        bookSearchViewModel.liveBookList.observe(getViewLifecycleOwner(), liveBookList ->{
            bookSearchAdapter.setBookList(liveBookList);
            bookRv.setAdapter(bookSearchAdapter);

            //failed here, java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
            // issue is currently doe not return anything
            //System.out.println(liveBookList.get(0));

        });
//        bookSearchViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), Observable -> {});
//
//        bookSearchViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), bookList -> {
//            if (bookList != null ) {
//                bookSearchAdapter.clear();
//                bookSearchAdapter.setBookList(bookList);
//                bookRv.setAdapter(bookSearchAdapter);
//            }
//            else {
//                Log.d("TAG", "waiting for info");
//            }
//        });

        //operation for when search button is clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = (String) searchBar.getText().toString();
                bookSearchViewModel.getBookSearch(keyword, owner);
            }
        });

        return view;
    }
}