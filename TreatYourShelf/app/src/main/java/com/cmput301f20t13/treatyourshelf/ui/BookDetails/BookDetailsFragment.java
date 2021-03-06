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
import com.cmput301f20t13.treatyourshelf.data.Notification;
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
    private Book currentBook = null;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

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
        TextView bookBorrower = view.findViewById(R.id.book_borrower);
        Button requestBt = view.findViewById(R.id.book_request_button);
        Button viewRequestBt = view.findViewById(R.id.view_requests_button);
        ImageButton closeBt = view.findViewById(R.id.close_bookdetails);

        /*Show or hide the request button and view request button*/
/*        if (category == 0) {
            // User is not allowed to edit book
            requestBt.setVisibility(View.VISIBLE);
            viewRequestBt.setVisibility(View.GONE);
        } else {
            // User is allowed to edit book

            //requestBt.setVisibility(View.INVISIBLE);
            requestBt.setVisibility(View.GONE);
            viewRequestBt.setVisibility(View.VISIBLE);
        }*/

        bookOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = BookDetailsFragmentDirections.actionBookDetailsFragmentToProfileFragment()
                        .setEmail(bookOwner.getText().toString());
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
            }
        });

        bookBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = BookDetailsFragmentDirections.actionBookDetailsFragmentToProfileFragment()
                        .setEmail(bookBorrower.getText().toString());
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
            }
        });

        BookImagesAdapter bookImagesAdapter = new BookImagesAdapter(new ArrayList<>(), requireContext());
        viewPager2.setAdapter(bookImagesAdapter);
        wormDotsIndicator.setViewPager2(viewPager2);

        /*View Models - where the fragment retrieves its data from*/
        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity()).get(RequestListViewModel.class);
        RequestDetailsViewModel requestDetailsViewModel = new ViewModelProvider(requireActivity()).get(RequestDetailsViewModel.class);
        BookDetailsViewModel bookDetailsViewModel = new ViewModelProvider(requireActivity()).get(BookDetailsViewModel.class);
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.getBookByIsbnLiveData(Isbn).observe(getViewLifecycleOwner(), Observable -> {
        });


        /*Set the book details using the book list view model*/
        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (!bookList.isEmpty()) {
                Book book = bookList.get(0);
                currentBook = book;
                bookTitle.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                bookDescription.setText(book.getDescription());
                bookOwner.setText(book.getOwner());
                bookBorrower.setText(book.getBorrower());
                bookIsbn.setText(book.getIsbn());
                bookStatus.setText(Utils.capitalizeString(book.getStatus().toUpperCase()));
                checkOwner(book.getOwner(), requestBt, viewRequestBt);
                if(book.getImageUrls() != null){
                    bookImagesAdapter.setImages(book.getImageUrls());
                }


                if (category == 1) {
                    bookDetailsViewModel.setBook(book);
                }
            } else {
                Log.d("TAG", "waiting for info");
            }
        });

        /*Request Button - makes a request on the current book if it is available or requested status*/
        requestBt.setOnClickListener(v -> {
            if (currentBook.getStatus().equals("available") || currentBook.getStatus().equals("requested")){
                new AlertDialog.Builder(getContext())
                        .setMessage("Would you like to request this book?")
                        .setPositiveButton("YES", (dialog, id) -> {
                            dialog.cancel();
                            requestListViewModel.requestBook(currentBook, user.getEmail()); //creates a request
                            requestDetailsViewModel.updateBookStatusByIsbn(Isbn, "requested"); //updates the books status
                            Notification notification =
                                    new Notification("A request has been made on your book", "Woo :)",
                                            Utils.emailStripper(currentBook.getOwner()));
                            Utils.sendNotification(notification.getNotification(), requireContext());
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

    public void checkOwner(String owner, Button requestBt, Button viewRequestBt){
        if (userEmail.equals(owner)){
            viewRequestBt.setVisibility(View.VISIBLE);
            requestBt.setVisibility(View.GONE);
        } else {
            viewRequestBt.setVisibility(View.GONE);
            requestBt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
