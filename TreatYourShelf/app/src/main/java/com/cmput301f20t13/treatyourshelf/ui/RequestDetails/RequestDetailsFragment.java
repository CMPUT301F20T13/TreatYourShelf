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
 * Once accepted, the owner can specify a location for the borrower to pickup
 * Scanning the ISBN of the book as either owner or borrower will
 * update both the request status and the book status
 */
public class RequestDetailsFragment extends Fragment {
    private RequestDetailsViewModel requestDetailsViewModel;
    private RequestListViewModel requestListViewModel;
    private String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private String requestStatus = "";
    private String coordinates = "";

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
        TextView location = view.findViewById(R.id.set_location);

        BookImagesAdapter requestImagesAdapter = new BookImagesAdapter(new ArrayList<>(), requireContext());
        viewPager2.setAdapter(requestImagesAdapter);
        wormDotsIndicator.setViewPager2(viewPager2);

        /*Buttons*/
        Button acceptButton = view.findViewById(R.id.accept_button);
        Button declineButton = view.findViewById(R.id.decline_button);
        LinearLayout requestButtons = view.findViewById(R.id.request_buttons);
        Button giveBookButton = view.findViewById(R.id.give_book_button);
        Button confirmBorrowedButton = view.findViewById(R.id.confirm_borrowed_button);
        Button confirmReturnedButton = view.findViewById(R.id.confirm_returned_button);
        Button returnBookButton = view.findViewById(R.id.return_book_button);
        Button viewLocButton = view.findViewById(R.id.view_location_button);

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
                requester.setText(request.getRequester());
                isbn.setText(request.getIsbn());
                owner.setText(request.getOwner());
                status.setText(request.getStatus());
                requestStatus = request.getStatus();
                checkStatus(request.getStatus(), request.getOwner(), request.getRequester(),
                        requestButtons, giveBookButton, confirmBorrowedButton,
                        confirmReturnedButton, returnBookButton);
                title.setText(request.getTitle());
                author.setText(request.getAuthor());
                location.setText(request.getLocation());
                if (!request.getLocation().equals("")){
                    coordinates = request.getLocation();
                    viewLocButton.setVisibility(VISIBLE);
                }
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
                        if (rq.getRequester().equals(requesterString)) {continue;}
                        requestListViewModel.removeRequest(rq.getIsbn(), rq.getOwner(), rq.getRequester());
                        /*TODO notify requesters of declined request*/
                    }
                }
            });
            /*TODO - navigate to location fragment, set argument of owner here*/
            NavDirections action = RequestDetailsFragmentDirections
                    .actionRequestDetailsFragmentToMapsFragmentOwner()
                    .setISBN(isbnString)
                    .setREQUESTER(requesterString);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);

            requestListViewModel.updateStatusByIsbn(requesterString, isbnString, "accepted");
            requestDetailsViewModel.updateBookStatusByIsbn(isbnString, "accepted");
            Toast.makeText(getContext(), "Request Accepted!", Toast.LENGTH_SHORT).show();
            /*TODO notify requester of accepted request*/
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
            navigateCameraFragment(2, view);
        });
        /* Updates current borrower to requester
         * Updates book status to "borrowed"
         * Updates request status to "pending borrowed" */
        if (requestStatus.equals("accepted")) {
            requestDetailsViewModel.ownBorrowedScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                if (requestDetailsViewModel.getOwnerScannedCheck()) {
                    if (isbnScanned != null && !isbnScanned.equals("") && isbnScanned.equals(isbnString)) {
                        requestDetailsViewModel.updateRequestStatusByIsbn(isbnScanned, requesterString, "pending borrowed");
                        requestDetailsViewModel.updateBookStatusByIsbn(isbnScanned, "borrowed");
                        requestDetailsViewModel.updateBookBorrower(isbnScanned, requesterString);
                        Toast.makeText(getContext(), "Book status updated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Please try again. Isbn Scanned: " + isbnScanned, Toast.LENGTH_LONG).show();
                    }
                    requestDetailsViewModel.resetOwnerScannedCheck();
                }
            });
        }


        /*Confirm Borrowed - the borrower scans the book to confirm the book as borrowed*/
        confirmBorrowedButton.setOnClickListener(v -> {
            navigateCameraFragment(3, view);
        });

        /*Borrower receiving the book updates the request status*/
        if (requestStatus.equals("pending borrowed")) {
            requestDetailsViewModel.borBorrowedScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                if (requestDetailsViewModel.getBorrowerScannedCheck()) {
                    if (isbnScanned != null && !isbnScanned.equals("") && isbnScanned.equals(isbnString)) {
                        requestDetailsViewModel.updateRequestStatusByIsbn(isbnScanned, requesterString, "borrowed");
                        Toast.makeText(getContext(), "Request status updated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Please try again. Isbn Scanned: "+ isbnScanned, Toast.LENGTH_LONG).show();
                    }
                    requestDetailsViewModel.resetBorrowerScannedCheck();
                }
            });
        }



        /*Return Book - borrower returns the book updates the request details*/
        returnBookButton.setOnClickListener(v -> {
            navigateCameraFragment(4, view);
        });

        /*Borrower scans the book to denote they want to return the book*/
        if (requestStatus.equals("borrowed")) {
            requestDetailsViewModel.borReturnedScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                if (requestDetailsViewModel.getBorrowerScannedCheck()) {
                    if (isbnScanned != null && !isbnScanned.equals("") && isbnScanned.equals(isbnString)) {
                        requestDetailsViewModel
                                .updateRequestStatusByIsbn(isbnScanned, requesterString, "pending return");
                        Toast.makeText(getContext(), "Request status updated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Please try again. Isbn Scanned: "+ isbnScanned, Toast.LENGTH_LONG).show();
                    }
                    requestDetailsViewModel.resetBorrowerScannedCheck();

                }
            });
        }



        /*Confirm Returned - the owner scans the book to confirm the book returned and available*/
        confirmReturnedButton.setOnClickListener(v -> {
            navigateCameraFragment(5, view);
        });

        /*Owner confirms the book returned from borrower and updates book status to available while removing request*/
        if (requestStatus.equals("pending return")) {
            requestDetailsViewModel.ownReturnedScannedIsbn.observe(getViewLifecycleOwner(), isbnScanned -> {
                if (requestDetailsViewModel.getOwnerScannedCheck()) {
                    if (isbnScanned != null && !isbnScanned.equals("") && isbnScanned.equals(isbnString)) {
                        requestDetailsViewModel.updateBookStatusByIsbn(isbnScanned, "available");
                        requestDetailsViewModel.updateBookBorrower(isbnScanned, "");
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                        NavDirections action = RequestDetailsFragmentDirections
                                .actionRequestDetailsFragmentToBookDetailsFragment().setISBN(isbnScanned);
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
                        requestListViewModel
                                .removeRequest(isbnScanned, ownerString, requesterString);
                        Toast.makeText(getContext(), "Book status updated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Please try again. Isbn Scanned: " + isbnScanned, Toast.LENGTH_LONG).show();
                    }
                    requestDetailsViewModel.resetOwnerScannedCheck();
                }
            });
        }


        viewLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = RequestDetailsFragmentDirections
                        .actionRequestDetailsFragmentToMapsFragmentBorrower()
                        .setCOORD(coordinates);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
            }
        });

            return view;
        }

        @Override
    public void onDestroyView() {
        requestDetailsViewModel.clearState();
        super.onDestroyView();
    }


    /**
     * Checks the status of the request to hide or show buttons
     * @param status - status of the request
     * @param requestButtons - the accept and decline buttons
     */
    public void checkStatus(String status, String owner, String requester, LinearLayout requestButtons,
                            Button giveBookButton, Button confirmBorrowedButton,
                            Button confirmReturnedButton,Button returnBookButton){
        /*If the user is the owner of the book requested*/
        if (user.equals(owner)) {
            switch(status){
                case "requested":
                    requestButtons.setVisibility(VISIBLE); //accept/decline
                    break;
                case "accepted":
                    requestButtons.setVisibility(GONE);
                    giveBookButton.setVisibility(VISIBLE);
                    break;
                case "pending borrowed":
                    giveBookButton.setVisibility(GONE);
                    break;
                case "pending return":
                    confirmReturnedButton.setVisibility(VISIBLE);
                    break;
            }
        /*If the user is the requester of the book*/
        } else if (user.equals(requester)){
            switch(status){
                case "pending borrowed":
                    confirmBorrowedButton.setVisibility(VISIBLE);
                    break;
                case "borrowed":
                    confirmBorrowedButton.setVisibility(GONE);
                    returnBookButton.setVisibility(VISIBLE);
                    break;
                case "pending return":
                    returnBookButton.setVisibility(GONE);
                    break;
            }
        }
    }


    /**
     * Navigates to the CameraFragment to scan the ISBN
     * Service Code 2 = owner scanned to denote borrowed
     * Service Code 3 = borrower scanned to confirm borrowed
     * Service Code 4 = borrower scanned to return a book
     * Service Code 5 = owner scanned to confirm book returned
     * @param serviceCode - determines who is scanning the book
     * @param view - takes the current view to hide the keyboard
     */
    public void navigateCameraFragment(int serviceCode, View view){
        Utils.hideKeyboardFrom(requireContext(), view);
        NavDirections action = RequestDetailsFragmentDirections
                .actionRequestDetailsFragmentToCameraXFragment().setServiceCode(serviceCode);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
    }

}




