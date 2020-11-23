package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OwnedBooksFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    private ChipGroup chipGroup;
    private ArrayList<Book> unfilteredBookList;
    private final HashMap<Integer, String> filterIdToName = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        // Make the chip group for filters visible
        chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setVisibility(View.VISIBLE);
        // Make objects for all the chips
        Chip availableChip = view.findViewById(R.id.availableChip);
        Chip requestedChip = view.findViewById(R.id.requestedChip);
        Chip acceptedChip = view.findViewById(R.id.acceptedChip);
        Chip borrowedChip = view.findViewById(R.id.borrowedChip);
        // Initialized hashmap with id's and strings
        filterIdToName.put(availableChip.getId(), "available");
        filterIdToName.put(requestedChip.getId(), "requested");
        filterIdToName.put(acceptedChip.getId(), "accepted");
        filterIdToName.put(borrowedChip.getId(), "borrowed");

        String owner = "user1"; // TODO: set owner with intent of fragment

        //
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        ArrayList<Book> bookArray = new ArrayList<>();
        bookListAdapter = new BookListAdapter(bookArray);
        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListViewModel.clearLiveData();
        bookListViewModel.getBookByOwnerLiveData(owner).observe(getViewLifecycleOwner(), Observable -> {});

        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null ) {
                bookListAdapter.clear();
                bookListAdapter.setBookList(bookList);
                unfilteredBookList = new ArrayList<>();
                setUnfilteredBookList(bookList);
                registerFilterChanged();
                bookRv.setAdapter(bookListAdapter);
                progressBar.setVisibility(View.GONE);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        availableChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OwnedBooksFragment.this.registerFilterChanged();
            }
        });
        requestedChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OwnedBooksFragment.this.registerFilterChanged();
            }
        });
        acceptedChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OwnedBooksFragment.this.registerFilterChanged();
            }
        });
        borrowedChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OwnedBooksFragment.this.registerFilterChanged();
            }
        });

        return view;
    }

    private void setUnfilteredBookList(List<Book> bookList){
        unfilteredBookList.addAll(bookList);
    }
    private void registerFilterChanged(){
        /* Only keep books in list that have status in checkedFilters */
        List<Book> found = new ArrayList<>();
        List<Integer> checkedChipIds = chipGroup.getCheckedChipIds();
        ArrayList<String> checkedFilters = new ArrayList<>();
        for (Integer id : checkedChipIds){
            checkedFilters.add(filterIdToName.get(id));
        }
        for (Book book : unfilteredBookList){
            if (checkedFilters.contains(book.getStatus())){
                found.add(book);
            }
        }
        bookListAdapter.clear();
        bookListAdapter.setBookList(found);
    }
}
