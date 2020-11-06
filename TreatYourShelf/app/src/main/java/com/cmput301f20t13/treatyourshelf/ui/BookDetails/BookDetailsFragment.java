package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * BookDetailsFragment displays the details of the book class.
 * Contains an edit button to change the details of the book.
 * Only appears when the user opens their my book list
 * Contains a request button for users to request the book
 * Only appears when the book list is not accessed from mybooks
 */
public class BookDetailsFragment extends Fragment {
    private BookDetailsViewModel bookDetailsViewModel;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        ImageView image = view.findViewById(R.id.book_image);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);

        String Isbn = BookDetailsFragmentArgs.fromBundle(getArguments()).getISBN();
        System.out.println("The ISBN is" + Isbn);
        /*Tab Layout that includes a Summary tab and Details Tab*/
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.book_details_view_pager);
        tabLayout.setupWithViewPager(viewPager);
        /*Fragments within the Tab Layout*/
        BookDetailsDtab detailsFragment = new BookDetailsDtab();
        BookDetailsStab summaryFragment = new BookDetailsStab();

        /*View Models - where the fragment retrieves its data from*/
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.getBookByIsbnLiveData(Isbn).observe(getViewLifecycleOwner(), Observable -> {
        });

        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (!bookList.isEmpty()) {

                Book book = bookList.get(0);
                System.out.println(book.getTitle());
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                status.setText(book.getStatus());
                changeStatusColor(status, book.getStatus());
                setSumFragBundle(book, summaryFragment);
                setDetFragBundle(book, detailsFragment);
            } else {
                Log.d("TAG", "waiting for info");
            }
        });


        /*ViewPagerAdapter - Attaches the fragment to the tablayout*/
        BookViewPagerAdapter viewPagerAdapter = new BookViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(summaryFragment, "summary");
        viewPagerAdapter.addFragment(detailsFragment, "details");
        viewPager.setAdapter(viewPagerAdapter);

        FloatingActionButton requestButton = view.findViewById(R.id.book_request_button);
        if (!bookListViewModel.ownerList) {
            requestButton.setVisibility(View.VISIBLE);
        }
        requestButton.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                .setMessage("Would you like to request this book?")
                .setPositiveButton("YES", (dialog, id) -> {
                    dialog.cancel();
                    /*TODO call edit book fragment to change status*/
                    Toast.makeText(getContext(), "Request sent!", Toast.LENGTH_LONG).show();
                })

                .setNegativeButton("NO", (dialog, which) -> dialog.cancel())
                .show());


        ImageButton editButton = view.findViewById(R.id.book_edit_button);
        if (bookListViewModel.ownerList) {
            editButton.setVisibility(View.VISIBLE);
        }
        editButton.setOnClickListener(v -> {
            // Not implemented yet
            /*TODO - call edit book fragment*/
        });

        return view;
    }


    /**
     * Changes the color of the status based on the text
     *
     * @param status the status of the book
     * @param book   candidate book
     */
    public void changeStatusColor(TextView status, String book) {
        if (book != null) {


            switch (book) {
                /*Changes the color of the status based on the text*/
                case "Available":
                case "Accepted":
                    status.setTextColor(0xFF00FF00); /*Green Color*/
                    break;
                case "Requested":
                    status.setTextColor(0xFF0000FF); /*Blue Color*/
                    break;
                case "Borrowed":
                    status.setTextColor(0xFFFF0000); /*Red Color*/
                    break;
            }
        }
    }


    /**
     * Sets the arguments on the summary fragment
     *
     * @param book            candidate book
     * @param summaryFragment fragment that contains the description of the book
     */
    public void setSumFragBundle(Book book, Fragment summaryFragment) {
        Bundle descBundle = new Bundle();
       // System.out.println(book.getDescription());
        descBundle.putString("description", book.getDescription());
        summaryFragment.setArguments(descBundle);
    }


    /**
     * Sets the argument son the details fragment
     *
     * @param book            candidate book
     * @param detailsFragment fragment that contains the isbn, owner, and borrower of book
     */
    public void setDetFragBundle(Book book, Fragment detailsFragment) {
        Bundle detailBundle = new Bundle();
        detailBundle.putString("isbn", book.getIsbn());
        detailBundle.putString("owner", book.getOwner());
        detailBundle.putString("borrower", book.getBorrower());
        detailsFragment.setArguments(detailBundle);
    }

}
