package com.cmput301f20t13.treatyourshelf.ui.search_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListAdapter;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Referenced: https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview

public class SearchFragment extends Fragment {
    private BookListAdapter searchListAdapter;
    private BookListViewModel searchListViewModel;

    private List<Book> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_page, container, false);



        RecyclerView bookRv = view.findViewById(R.id.search_list_rv);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        searchListAdapter = new BookListAdapter(new ArrayList<Book>());
        bookRv.setAdapter(searchListAdapter);
        searchListViewModel = new ViewModelProvider(this).get(BookListViewModel.class);

//        searchListViewModel.bookList.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
//            @Override
//            public void onChanged(List<Book> books) {
//                if (books.isEmpty()) {
//                    // Initial list will be empty due to no data stored in the app, therefore we insert data into the database
//                }
//
//                searchListAdapter.setBookList(books);
//            }
//        });

        return view;
    }
}
