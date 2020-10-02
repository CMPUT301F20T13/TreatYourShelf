package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {
    private BookListAdapter bookListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListAdapter = createAdapter();
        bookRv.setAdapter(bookListAdapter);


        return view;
    }

    private BookListAdapter createAdapter() {
        ArrayList<Book> books = new ArrayList<>();

        Book book = new Book("Eat a Peach", "Gabe Ulla and David Chang");
        books.add(book);
        book = new Book("A Killing Frost", "Seanan McGuire");
        books.add(book);
        book = new Book("Our Malady", "Timothy Snyder");
        books.add(book);
        book = new Book("Donald Trump v. The United States", "Michael S. Schmidt");
        books.add(book);
        book = new Book("Star Wars: Thrawn Ascendancy (Book I: Chaos Rising)", "Timothy Zahn");
        books.add(book);
        book = new Book("Fifty Words for Rain", "Asha Lemmie");
        books.add(book);

        return new BookListAdapter(books);
    }
}
