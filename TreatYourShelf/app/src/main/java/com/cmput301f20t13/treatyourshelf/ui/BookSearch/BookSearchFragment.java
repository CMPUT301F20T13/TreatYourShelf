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

import java.util.ArrayList;
import java.util.List;

public class BookSearchFragment extends Fragment {
    private BookSearchAdapter bookSearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_book, container, false);

        BookSearchViewModel bookSearchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BookSearchViewModel.class);
        ArrayList<Book> bookArray = new ArrayList<>();
        bookSearchAdapter = new BookSearchAdapter(bookArray);
        EditText searchBar = view.findViewById(R.id.search_text);
        Button searchButton = view.findViewById(R.id.search_button);

        bookSearchViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), Observable -> {});

        bookSearchViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null ) {
                bookSearchAdapter.clear();
                bookSearchAdapter.setBookList(bookList);
                RecyclerView bookRv = view.findViewById(R.id.search_list_rv);
                bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                bookRv.setAdapter(bookSearchAdapter);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = (String) searchBar.getText().toString();

                if (bookSearchViewModel.getBookByTitleLiveData(keyword) != null) {
                    bookSearchAdapter.clear();
                    List<Book> bookList = new ArrayList<>();
                    bookSearchViewModel.getBookByTitleLiveData(keyword).observe(getViewLifecycleOwner(), books -> {

                    });
                    bookList.addAll((List<Book>) bookSearchViewModel.getBookByOwnerLiveData(keyword));
                    bookList.addAll((List<Book>) bookSearchViewModel.getBookByIsbnLiveData(keyword));
                    bookSearchAdapter.setBookList(bookList);
                    RecyclerView bookRv = view.findViewById(R.id.search_list_rv);
                    bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    bookRv.setAdapter(bookSearchAdapter);

                } else if (bookSearchViewModel.getBookByOwnerLiveData(keyword) != null) {
                    bookSearchAdapter.clear();
                    List<Book> bookList = (List<Book>) bookSearchViewModel.getBookByOwnerLiveData(keyword);
                    bookList.addAll((List<Book>) bookSearchViewModel.getBookByTitleLiveData(keyword));
                    bookList.addAll((List<Book>) bookSearchViewModel.getBookByIsbnLiveData(keyword));
                    bookSearchAdapter.setBookList(bookList);
                    RecyclerView bookRv = view.findViewById(R.id.search_list_rv);
                    bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    bookRv.setAdapter(bookSearchAdapter);

                } else if (bookSearchViewModel.getBookByIsbnLiveData(keyword) != null) {
                    bookSearchAdapter.clear();
                    List<Book> bookList = (List<Book>) bookSearchViewModel.getBookByIsbnLiveData(keyword);
                    bookList.addAll((List<Book>) bookSearchViewModel.getBookByTitleLiveData(keyword));
                    bookList.addAll((List<Book>) bookSearchViewModel.getBookByOwnerLiveData(keyword));
                    bookSearchAdapter.setBookList(bookList);
                    RecyclerView bookRv = view.findViewById(R.id.search_list_rv);
                    bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    bookRv.setAdapter(bookSearchAdapter);
                } else {
                    bookSearchAdapter.clear();
                }
            }
        });

        return view;
    }
}