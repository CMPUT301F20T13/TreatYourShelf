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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Observable;

public class BookListFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    private BookListViewModel bookListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        bookListViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BookListViewModel.class);
        ArrayList<Book> bookArray = new ArrayList<>();
        bookListAdapter = new BookListAdapter(bookArray);

        bookListViewModel.getBookListLiveData().observe(getViewLifecycleOwner(), Observable -> {});

        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null ) {
                bookListAdapter.clear();
                bookListAdapter.setBookList(bookList);
                RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
                bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                bookRv.setAdapter(bookListAdapter);
                progressBar.setVisibility(View.GONE);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });


        return view;
    }

}
