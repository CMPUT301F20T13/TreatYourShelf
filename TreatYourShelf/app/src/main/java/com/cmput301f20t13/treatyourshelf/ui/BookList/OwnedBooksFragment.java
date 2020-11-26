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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.GalleryViewAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OwnedBooksFragment extends Fragment {
    private BookListAdapter bookListAdapter;
    // chipGroup is an easy way to store all the chips ids
    private ChipGroup chipGroup;
    // unfilteredBookList stores all the owners books so they can be filtered later
    private final ArrayList<Book> unfilteredBookList = new ArrayList<>();
    // HashMap to map chip ids to string that represents the status that the chip filters
    private final HashMap<Integer, String> filterIdToName = new HashMap<>();
    private String ownerEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            ownerEmail = user.getEmail();
            Log.e("Test", ownerEmail);
        }
        // Make loading circle visible
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

        // Set up recycler view object
        RecyclerView bookRv = view.findViewById(R.id.book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize BookListAdapter
        bookListAdapter = new BookListAdapter(new ArrayList<>());
        bookListAdapter.setOnClick((BookListAdapter.OnItemClicked) (position, book) -> {
            NavDirections action = AllBooksFragmentDirections.actionBookListFragmentToBookDetailsFragment().setISBN(book.getIsbn()).setCategory(1);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
        });

        bookRv.setAdapter(bookListAdapter);
        // Set up BookListViewModel and live data
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.clearLiveData();
        bookListViewModel.getBookByOwnerLiveData(ownerEmail).observe(getViewLifecycleOwner(), Observable -> {
        });
        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null) {

                // Reset adapter by setting it with a new list
                bookListAdapter.setBookList(new ArrayList<>());
                // Clear unfilteredBookList and set it to bookList
                unfilteredBookList.clear();
                bookListAdapter.setBookList(bookList);
                setUnfilteredBookList(bookList);
                // Make loading circle gone
                progressBar.setVisibility(View.GONE);
            } else {
                Log.d("TAG", "waiting for info");
            }
        });

        // Add onCheckedChangeListener for each of the chips. I wish I knew a cleaner way to do this
        availableChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                registerFilterChanged();
            }
        });
        requestedChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                registerFilterChanged();
            }
        });
        acceptedChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                registerFilterChanged();
            }
        });
        borrowedChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                registerFilterChanged();
            }
        });

        return view;
    }

    /**
     * setUnfilteredBookList will set unfilteredBookList to a list of books and register change
     *
     * @param bookList
     */
    private void setUnfilteredBookList(List<Book> bookList) {
        unfilteredBookList.addAll(bookList);
        registerFilterChanged();
    }

    /**
     * registerFilterChanged
     * called when the filters have been changed or unfilteredBookList changes
     */
    private void registerFilterChanged() {
        // Only keep books in list that have status in checkedFilters
        List<Book> found = new ArrayList<>();
        // Get the ids of all the chips
        List<Integer> checkedChipIds = chipGroup.getCheckedChipIds();
        // Array to store strings that represent which filters are selected
        ArrayList<String> checkedFilters = new ArrayList<>();
        for (Integer id : checkedChipIds) {
            checkedFilters.add(filterIdToName.get(id));
        }
        // Go through unfilteredBookList and keep ones that match checked filters
        for (Book book : unfilteredBookList) {
            if (checkedFilters.contains(book.getStatus())) {
                found.add(book);
            }
        }
        // clear and set book list for adapter
        bookListAdapter.clear();
        bookListAdapter.setBookList(found);
    }
}
