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
import androidx.lifecycle.ViewModel;
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
import com.cmput301f20t13.treatyourshelf.ui.RequestDetails.RequestDetailsViewModel;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListFragmentDirections;
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
        String Owner = BookDetailsFragmentArgs.fromBundle(getArguments()).getOWNER();
        int category = BookDetailsFragmentArgs.fromBundle(getArguments()).getCategory();
        System.out.println("The ISBN is" + Isbn);

        ViewPager2 viewPager2 = view.findViewById(R.id.book_image_vp2);
        WormDotsIndicator wormDotsIndicator = view.findViewById(R.id.worm_dots_indicator);
        TextView bookTitle = view.findViewById(R.id.book_title);
        TextView bookAuthor = view.findViewById(R.id.book_author);
        TextView bookDescription = view.findViewById(R.id.book_description);
        TextView bookIsbn = view.findViewById(R.id.book_isbn);
        TextView bookOwner = view.findViewById(R.id.book_owner);
        TextView bookStatus = view.findViewById(R.id.book_status);
        Button requestBt = view.findViewById(R.id.book_request_button);
        Button viewRequestBt = view.findViewById(R.id.view_requests_button);
        ImageButton closeBt = view.findViewById(R.id.close_bookdetails);

        /*Show or hide the request button and view request button*/
        if (category == 0) {
            requestBt.setVisibility(View.VISIBLE);
            viewRequestBt.setVisibility(View.GONE);
        } else {
            requestBt.setVisibility(View.GONE);
            viewRequestBt.setVisibility(View.VISIBLE);
        }

        BookImagesAdapter bookImagesAdapter = new BookImagesAdapter(new ArrayList<>(), requireContext());
        viewPager2.setAdapter(bookImagesAdapter);
        wormDotsIndicator.setViewPager2(viewPager2);

        /*View Models - where the fragment retrieves its data from*/
        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity()).get(RequestListViewModel.class);
        RequestDetailsViewModel requestDetailsViewModel = new ViewModelProvider(requireActivity()).get(RequestDetailsViewModel.class);
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.getBookByIsbnLiveData(Isbn).observe(getViewLifecycleOwner(), Observable -> {
        });
        BookDetailsViewModel bookDetailsViewModel =
                new ViewModelProvider(requireActivity()).get(BookDetailsViewModel.class);
        bookDetailsViewModel.getBookByIsbnOwner(Isbn,Owner)
                .observe(getViewLifecycleOwner(), Observable -> {});

        /*Set the book details using the book list view model*/
        /*bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (!bookList.isEmpty()) {
                Book book = bookList.get(0);
                currentBook = book;
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
        });*/

        bookDetailsViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
            if (book != null) {
                currentBook = book;
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


        /*Request Button - makes a request on the current book if it is available or requested status*/
        requestBt.setOnClickListener(v -> {
            if (currentBook.getStatus().equals("Available") || currentBook.getStatus().equals("Requested")){
                new AlertDialog.Builder(getContext())
                        .setMessage("Would you like to request this book?")
                        .setPositiveButton("YES", (dialog, id) -> {
                            dialog.cancel();
                            requestListViewModel.requestBook(currentBook, user.getEmail()); //creates a request
                            requestDetailsViewModel.updateBookStatusByIsbn(Isbn, "Requested"); //updates the books status
                            Toast.makeText(getContext(), "Request sent!", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("NO", (dialog, which) -> dialog.cancel())
                        .show();
            } else {
                new AlertDialog.Builder(getContext())
                        .setMessage("This book is unavailable")
                        .setNegativeButton("CLOSE", (dialog, which) -> dialog.cancel())
                        .show();
            }
        });


        /*View Request - view the requests made on the current book*/
        viewRequestBt.setOnClickListener(v -> {
            NavDirections action =
                    BookDetailsFragmentDirections.actionBookDetailsFragmentToRequestListFragment()
                    .setISBN(Isbn);
            Navigation.findNavController(v).navigate(action);
        });


        /*Close Button - closes the book details fragment*/
        closeBt.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
        });


        return view;
    }


}
