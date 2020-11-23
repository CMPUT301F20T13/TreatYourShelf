package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;
import java.util.List;

public class AllBooksFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    private ArrayList<Book> unfilteredBookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        ArrayList<Book> bookArray = new ArrayList<>();
        bookListAdapter = new BookListAdapter(bookArray);
        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListViewModel.clearLiveData();
        bookListViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), Observable -> {});

        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null ) {
                unfilteredBookList = new ArrayList<>();
                setUnfilteredBookList(bookList);
                bookListAdapter.clear();
                bookListAdapter.setBookList(unfilteredBookList);
                bookRv.setAdapter(bookListAdapter);
                progressBar.setVisibility(View.GONE);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        return view;
    }

    private void setUnfilteredBookList(List<Book> bookList){
        unfilteredBookList.addAll(bookList);
    }
}
