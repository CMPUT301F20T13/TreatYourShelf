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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.Utils;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.AddBookFragmentDirections;
import com.cmput301f20t13.treatyourshelf.ui.BookDetails.BookImagesAdapter;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.security.acl.Group;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * RequestDetailsFragment displays the request details for a specific book
 * The owner can decide to accept or decline the request
 */
public class RequestDetailsFragment extends Fragment {
    private Request currentRequest;
    private RequestDetailsViewModel requestDetailsViewModel;
    private RequestListViewModel requestListViewModel;
    private String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();

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
        ViewPager2 viewPager2 = view.findViewById(R.id.request_image_vp2);
        WormDotsIndicator wormDotsIndicator = view.findViewById(R.id.worm2_dots_indicator);
        TextView requester = view.findViewById(R.id.user_request);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView isbn = view.findViewById(R.id.book_isbn);
        TextView owner = view.findViewById(R.id.book_owner);
        TextView status = view.findViewById(R.id.book_request_status);

        BookImagesAdapter requestImagesAdapter = new BookImagesAdapter(new ArrayList<>(), requireContext());
        viewPager2.setAdapter(requestImagesAdapter);
        wormDotsIndicator.setViewPager2(viewPager2);

        /*Buttons*/
        Button acceptButton = view.findViewById(R.id.accept_button);
        Button declineButton = view.findViewById(R.id.decline_button);
        LinearLayout requestButtons = view.findViewById(R.id.request_buttons);
        Button giveBookButton = view.findViewById(R.id.give_book_button);
        Button receiveBookButton = view.findViewById(R.id.receive_book_button);
        Button returnBookButton = view.findViewById(R.id.return_book_button);

        /*Retrieve the isbn, requester and owner from fragment arguments*/
        assert this.getArguments() != null;
        String isbnString = this.getArguments().getString("ISBN");
        String requesterString = this.getArguments().getString("REQUESTER");
        String ownerString = this.getArguments().getString("OWNER");

        /*View Models*/
        requestListViewModel = new ViewModelProvider(requireActivity())
                .get(RequestListViewModel.class);
        requestDetailsViewModel = new ViewModelProvider(requireActivity())
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
                checkStatus(request.getStatus(), requestButtons, giveBookButton,
                        receiveBookButton, returnBookButton);
                title.setText(request.getTitle());
                author.setText(request.getAuthor());
                if(request.getImageUrls() != null){
                    requestImagesAdapter.setImages(request.getImageUrls());
                }
            }
        });


        /*Accept Button - accepts the current request and removes all other requests for the book*/
        acceptButton.setOnClickListener(v -> {
            requestListViewModel.getRequestList().observe(getViewLifecycleOwner(), requestList -> {
                if (requestList != null){
                    for (Request rq : requestList){
                        if (currentRequest != null && equalTo(rq, currentRequest)){ continue; }
                        requestListViewModel.removeRequest(rq.getIsbn(), rq.getOwner(), rq.getRequester());
                        /*TODO notify requesters of declined request*/
                    }
                }
            });
            /*TODO - navigate to location fragment, set argument of owner here*/

            requestListViewModel.updateStatusByIsbn(requesterString, isbnString, "accepted");
            requestDetailsViewModel.updateBookStatusByIsbn(isbnString, "accepted");
            Toast.makeText(getContext(), "Request Accepted!", Toast.LENGTH_SHORT).show();
            /*TODO notify requester of accepted request*/
            requestButtons.setVisibility(GONE);
            giveBookButton.setVisibility(VISIBLE); //scan the book isbn
        });


        /*Decline Button - removes the request from the request list*/
        declineButton.setOnClickListener(v -> {
            requestListViewModel
                    .removeRequest(isbnString, ownerString, requesterString);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
            Toast.makeText(getContext(), "Request Declined", Toast.LENGTH_SHORT).show();
            /*TODO notify requester of declined request*/
        });


        /*Give Book Button - the owner scans the book to denote the book as borrowed*/
        giveBookButton.setOnClickListener(v -> {
            /*Navigate to camera fragment, scan isbn to update book status*/
            navigateCameraFragment(2, view);
            /*Change current borrower to requester for the book*/
        });

        requestDetailsViewModel.ownerScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
            if(isbnScanned != null && !isbnScanned.equals("")) {
                requestDetailsViewModel.updateBookStatusByIsbn(isbnScanned, "borrowed");
                requestDetailsViewModel.updateBookBorrower(isbnScanned, requesterString);
                requestDetailsViewModel.ownerScanned = true;
                Toast.makeText(getContext(), "Book status updated!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Please scan again", Toast.LENGTH_LONG).show();
            }
        });

        /*Received Book - the borrower scans the book to denote the request as borrowed*/
        receiveBookButton.setOnClickListener(v -> {
            /*Navigate to camera fragment, scan isbn to update request status*/
            navigateCameraFragment(3, view);

            /*TODO specify if owner or borrower to set conditionals*/
            /*Owner receiving the returned book from borrower updates the request details*/
            requestDetailsViewModel.ownerScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                requestDetailsViewModel.updateRequestStatusByIsbn(isbnScanned, requesterString, "available"); });

            /*Borrower receiving the book updates the request details*/
            requestDetailsViewModel.borrowerScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                requestDetailsViewModel.updateRequestStatusByIsbn(isbnScanned, requesterString, "borrowed"); });
            Toast.makeText(getContext(), "Request status updated!", Toast.LENGTH_LONG).show();
        });


        /*Return Book - borrower returns the book updates the request details*/
        returnBookButton.setOnClickListener(v -> {
            navigateCameraFragment(2, view);
            requestDetailsViewModel.borrowerScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                requestDetailsViewModel
                        .updateRequestStatusByIsbn(isbnScanned, requesterString, "available");
                requestListViewModel
                        .removeRequest(isbnScanned, ownerString, requesterString);});
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
            Toast.makeText(getContext(), "Book returned", Toast.LENGTH_LONG).show();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        requestDetailsViewModel.clearState();
        super.onDestroyView();
    }


    /**
     * Checks the request of the status and hides the request buttons
     * @param status - status of the request
     * @param requestButtons - the accept and decline buttons
     */
    public void checkStatus(String status, LinearLayout requestButtons,
                            Button giveBookButton, Button receiveBookButton, Button returnBookButton){
        /*If the user is the owner of the book requested*/
        if (user.equals(currentRequest.getOwner())) {
            switch(currentRequest.getStatus()){
                case "requested":
                    requestButtons.setVisibility(VISIBLE); //accept/decline
                    break;
                case "accepted":
                    if(!requestDetailsViewModel.ownerScanned) {
                        giveBookButton.setVisibility(VISIBLE);
                    }
                    break;
                case "borrowed":
                    receiveBookButton.setVisibility(VISIBLE);
                    break;
            }
        /*If the user is the requester of the book*/
        } else if (user.equals(currentRequest.getRequester())){
            switch(currentRequest.getStatus()){
                case "requested":
                    break;
                case "accepted":
                    receiveBookButton.setVisibility(VISIBLE);
                    break;
                case "borrowed":
                    returnBookButton.setVisibility(VISIBLE);
                    break;
            }
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

    public void navigateCameraFragment(int serviceCode, View view){
        Utils.hideKeyboardFrom(requireContext(), view);
        NavDirections action = RequestDetailsFragmentDirections.actionRequestDetailsFragmentToCameraXFragment().setServiceCode(3);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
    }

}




