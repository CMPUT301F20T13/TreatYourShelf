package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    private BookListViewModel bookListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListAdapter = new BookListAdapter(new ArrayList<Book>());
        bookRv.setAdapter(bookListAdapter);
        bookListViewModel = new ViewModelProvider(this).get(BookListViewModel.class);

        bookListViewModel.bookList.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                if (books.isEmpty()) {
                    // Initial list will be empty due to no data stored in the app, therefore we insert data into the database
                    setInitialData();
                }

                bookListAdapter.setBookList(books);
            }
        });

        return view;
    }

    private void setInitialData() {

        Book book = new Book("Eat a Peach", "Gabe Ulla and David Chang");
        bookListViewModel.insert(book);
        book = new Book("A Killing Frost", "Seanan McGuire");
        bookListViewModel.insert(book);
        book = new Book("Our Malady", "Timothy Snyder");
        bookListViewModel.insert(book);
        book = new Book("Donald Trump v. The United States", "Michael S. Schmidt");
        bookListViewModel.insert(book);
        book = new Book("Star Wars: Thrawn Ascendancy (Book I: Chaos Rising)", "Timothy Zahn");
        bookListViewModel.insert(book);
        book = new Book("Fifty Words for Rain", "Asha Lemmie");
        bookListViewModel.insert(book);

    }
}
