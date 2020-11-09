package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListViewModel;

/**
 * RequestDetailsFragment displays the request details for a specific book
 * The owner can decide to accept or decline the request
 */
public class RequestDetailsFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_details, container, false);
        TextView requester = view.findViewById(R.id.user_request);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView isbn = view.findViewById(R.id.book_isbn);
        TextView owner = view.findViewById(R.id.book_owner);
        TextView status = view.findViewById(R.id.book_request_status);
        assert this.getArguments() != null;
        String isbnString = this.getArguments().getString("ISBN");
        String requesterString = this.getArguments().getString("REQUESTER");

        /*requestListViewModel sets the requester, isbn and owner*/
        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity())
                .get(RequestListViewModel.class);
        requestListViewModel.getRequestByIsbnLiveData(isbnString)
                .observe(getViewLifecycleOwner(), Observable -> {});
        requestListViewModel.getRequestList().observe(getViewLifecycleOwner(), requestList -> {
            if (requestList != null) {
                Request request = requestList.get(0);
                requester.setText(request.getRequester());
                isbn.setText(request.getIsbn());
                owner.setText(request.getOwner());
                status.setText(request.getStatus());
            }
        });

        /*bookListViewModel sets the title and author*/
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity())
                .get(BookListViewModel.class);
        bookListViewModel.getBookByIsbnLiveData(isbnString)
                .observe(getViewLifecycleOwner(), Observable -> {});
        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (bookList != null) {
                Book book = bookList.get(0);
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
            }
        });


        Button acceptButton = view.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(v -> {
            requestListViewModel.updateStatusByIsbn(requesterString, isbnString, "Accepted");

            requestListViewModel.getRequestList().observe(getViewLifecycleOwner(), requestList -> {
                if (requestList != null){
                    for (Request request : requestList){
                        if (request.getRequester().equals(requesterString)){
                            continue;
                        }
                        requestListViewModel
                                .updateStatusByIsbn(request.getRequester(), request.getIsbn(), "Declined");
                    }
                }
            });

            Toast.makeText(getContext(), "Request Accepted!", Toast.LENGTH_SHORT).show();
        });


        Button declineButton = view.findViewById(R.id.decline_button);
        declineButton.setOnClickListener(v -> {requestListViewModel
                .updateStatusByIsbn(requesterString, isbnString, "Declined");
        Toast.makeText(getContext(), "Request Declined", Toast.LENGTH_SHORT).show();
        });



        return view;
    }



}




