package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListViewModel;

import java.security.acl.Group;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * RequestDetailsFragment displays the request details for a specific book
 * The owner can decide to accept or decline the request
 */
public class RequestDetailsFragment extends Fragment {
    private Request currentRequest;

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
        Button acceptButton = view.findViewById(R.id.accept_button);
        Button declineButton = view.findViewById(R.id.decline_button);
        LinearLayout requestButtons = view.findViewById(R.id.request_buttons);

        /*Retrieve the isbn, requester and owner from fragment arguments*/
        assert this.getArguments() != null;
        String isbnString = this.getArguments().getString("ISBN");
        String requesterString = this.getArguments().getString("REQUESTER");
        String ownerString = this.getArguments().getString("OWNER");

        /*View Models*/
        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity())
                .get(RequestListViewModel.class);
        RequestDetailsViewModel requestDetailsViewModel = new ViewModelProvider(requireActivity())
                 .get(RequestDetailsViewModel.class);

        /*Get and set the request details on the fragment*/
        requestDetailsViewModel.getRequestLiveData(isbnString, requesterString, ownerString)
                .observe(getViewLifecycleOwner(), Observable -> {});
        requestDetailsViewModel.getRequest().observe(getViewLifecycleOwner(), request -> {
        if (request != null) {
                currentRequest = request;
                requester.setText(request.getRequester());
                isbn.setText(request.getIsbn());
                owner.setText(request.getOwner());
                status.setText(request.getStatus());
                checkStatus(request.getStatus(), requestButtons);
                title.setText(request.getTitle());
                author.setText(request.getAuthor());
            }
        });

        /*Accept Button - accepts the current request and removes all other requests for the book*/
        acceptButton.setOnClickListener(v -> {
            requestListViewModel.getRequestList().observe(getViewLifecycleOwner(), requestList -> {
                if (requestList != null){
                    for (Request rq : requestList){
                        if (currentRequest != null && equalTo(rq, currentRequest)){
                            continue;
                        }
                        requestListViewModel.removeRequest(rq.getIsbn(), rq.getOwner(), rq.getRequester());
                    }
                }
            });
            requestListViewModel.updateStatusByIsbn(requesterString, isbnString, "Accepted");
            Toast.makeText(getContext(), "Request Accepted!", Toast.LENGTH_SHORT).show();
            /*TODO notify requester of accepted request*/
            requestButtons.setVisibility(INVISIBLE);
        });


        /*Decline Button - removes the request from the request list*/
        declineButton.setOnClickListener(v -> {
            requestListViewModel
                    .removeRequest(isbnString, ownerString, requesterString);
            getActivity().onBackPressed();
            Toast.makeText(getContext(), "Request Declined", Toast.LENGTH_SHORT).show();
            /*TODO notify requester of declined request*/
        });


        return view;
    }

    /**
     * Checks the request of the status and hides the request buttons
     * @param status - status of the request
     * @param requestButtons - the accept and decline buttons
     */
    public void checkStatus(String status, LinearLayout requestButtons){
        if (status.equals("Requested")){
            requestButtons.setVisibility(VISIBLE);
        } else {
            requestButtons.setVisibility(INVISIBLE);
        }
    }

    /**
     * Compares rq1 if it is equal to rq2
     * @param rq1 the first request to compare
     * @param rq2 the second request to compare
     * @return returns true or false
     */
    public boolean equalTo(Request rq1, Request rq2){
        return rq1.getRequester().equals(rq2.getRequester()) &&
                rq1.getAuthor().equals(rq2.getAuthor()) &&
                rq1.getIsbn().equals(rq2.getIsbn()) &&
                rq1.getOwner().equals(rq2.getOwner()) &&
                rq1.getTitle().equals(rq2.getTitle());
    }


}




