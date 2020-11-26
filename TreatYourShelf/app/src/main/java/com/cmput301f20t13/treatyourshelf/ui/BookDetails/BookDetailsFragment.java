package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.Utils;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.AllBooksFragmentDirections;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * BookDetailsFragment displays the details of the book class.
 * Contains an edit button to change the details of the book.
 * Only appears when the user opens their my book list
 * Contains a request button for users to request the book
 * Only appears when the book list is not accessed from mybooks
 */
public class BookDetailsFragment extends Fragment {
    private BookDetailsViewModel bookDetailsViewModel;
    private Book currentBook;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        String Isbn = BookDetailsFragmentArgs.fromBundle(getArguments()).getISBN();
        int category = BookDetailsFragmentArgs.fromBundle(getArguments()).getCategory();
        System.out.println("The ISBN is" + Isbn);
        /*Tab Layout that includes a Summary tab and Details Tab*/


        // TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = view.findViewById(R.id.book_image_vp2);
        WormDotsIndicator wormDotsIndicator = view.findViewById(R.id.worm_dots_indicator);
        TextView bookTitle = view.findViewById(R.id.book_title);
        TextView bookAuthor = view.findViewById(R.id.book_author);
        TextView bookDescription = view.findViewById(R.id.book_description);
        TextView bookIsbn = view.findViewById(R.id.book_isbn);
        TextView bookOwner = view.findViewById(R.id.book_owner);
        TextView bookStatus = view.findViewById(R.id.book_status);
        Button requestBt = view.findViewById(R.id.book_request_button);
        ImageButton closeBt = view.findViewById(R.id.close_bookdetails);

        if (category == 0) {
            requestBt.setVisibility(View.VISIBLE);
        } else {
            requestBt.setVisibility(View.INVISIBLE);
        }
        // tabLayout.setupWithViewPager(viewPager);

        BookImagesAdapter bookImagesAdapter = new BookImagesAdapter(new ArrayList<>(), requireContext());
        viewPager2.setAdapter(bookImagesAdapter);
        wormDotsIndicator.setViewPager2(viewPager2);
        /*Fragments within the Tab Layout*/

        /*View Models - where the fragment retrieves its data from*/
        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity()).get(RequestListViewModel.class);
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.getBookByIsbnLiveData(Isbn).observe(getViewLifecycleOwner(), Observable -> {
        });

        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (!bookList.isEmpty()) {
                Book book = bookList.get(0);
                bookTitle.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                bookDescription.setText(book.getDescription());
                bookOwner.setText(book.getOwner());
                bookIsbn.setText(book.getIsbn());
                bookStatus.setText(Utils.capitalizeString(book.getStatus().toUpperCase()));
                bookImagesAdapter.setImages(book.getImageUrls());


            } else {
                Log.d("TAG", "waiting for info");
            }
        });
        requestBt.setOnClickListener(view1 -> {

        });
        closeBt.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
        });
        /*ViewPagerAdapter - Attaches the fragment to the tablayout*/


//        FloatingActionButton requestButton = view.findViewById(R.id.book_request_button);
//        if (!bookListViewModel.ownerList) {
//            requestButton.setVisibility(View.VISIBLE);
//        }
//        requestButton.setOnClickListener(v -> new AlertDialog.Builder(getContext())
//                .setMessage("Would you like to request this book?")
//                .setPositiveButton("YES", (dialog, id) -> {
//                    dialog.cancel();
//                    /*TODO call edit book fragment to change status*/
//                    Toast.makeText(getContext(), "Request sent!", Toast.LENGTH_LONG).show();
//                })
//
//                .setNegativeButton("NO", (dialog, which) -> dialog.cancel())
//                .show());


        //ImageButton editButton = view.findViewById(R.id.book_edit_button);
//        if (bookListViewModel.ownerList) {
//            editButton.setVisibility(View.VISIBLE);
//        }
//        editButton.setOnClickListener(v -> {
//            // Not implemented yet
//            /*TODO - call edit book fragment*/
//        });

        return view;
    }


}
