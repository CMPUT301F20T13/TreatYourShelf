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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;
import java.util.List;

public class AllBooksFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    // List of books that are consistent even if app is closed and reopened. May be filtered in future.
    private final ArrayList<Book> unfilteredBookList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        // Make loading circle visible
        ProgressBar progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        // Initialize BookListAdapter
        bookListAdapter = new BookListAdapter(new ArrayList<>());
        bookListAdapter.setOnClick((position, book) -> {
            NavDirections action = AllBooksFragmentDirections.actionBookListFragmentToBookDetailsFragment()
                    .setISBN(book.getIsbn())
                    .setOWNER(book.getOwner())
                    .setCategory(0);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
        });

        // Set up recycler view object
        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up BookListViewModel and live data
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.clearLiveData();
        bookListViewModel.getAllBooksLiveData().observe(getViewLifecycleOwner(), Observable -> {});
        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null ) {
                // Reset adapter by setting it with a new list
                bookListAdapter.setBookList(new ArrayList<>());
                // Clear unfilteredBookList and set it to bookList
                unfilteredBookList.clear();
                setUnfilteredBookList(bookList);
                // Clear adapter and set it to unfilteredBookList
                bookListAdapter.clear();
                bookListAdapter.setBookList(unfilteredBookList);
                // Set the adapter for the RecyclerView and make loading circle gone
                bookRv.setAdapter(bookListAdapter);
                progressBar.setVisibility(View.GONE);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        return view;
    }

    /**
     * setUnfilteredBookList will set unfilteredBookList to a list of books
     * @param bookList
     */
    private void setUnfilteredBookList(List<Book> bookList){
        unfilteredBookList.addAll(bookList);
    }

}
