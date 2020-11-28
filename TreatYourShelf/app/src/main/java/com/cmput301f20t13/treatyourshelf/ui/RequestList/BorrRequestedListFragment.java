package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * RequestListFragment displays all the requested books from borrowers perspective
 * Selecting the request will navigate to the RequestDetailsFragment
 */
public class BorrRequestedListFragment extends Fragment {

    private RequestListAdapter requestListAdapter;
    private String userEmail;

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
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            userEmail = user.getEmail();
        }

        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity()).get(RequestListViewModel.class);
        ArrayList<Request> requestArray = new ArrayList<>();
        requestListAdapter = new RequestListAdapter(requestArray, requestListViewModel);

        requestListViewModel.getRequestByRequesterLiveData(userEmail).observe(getViewLifecycleOwner(), Observable -> {});

        requestListViewModel.getRequestList().observe(getViewLifecycleOwner(), requestList -> {
            if (requestList != null ) {
                //requestListAdapter.clear();
                requestListAdapter.setRequestList(requestList);
                RecyclerView requestRv = view.findViewById(R.id.request_list_rv);
                requestRv.setLayoutManager(new LinearLayoutManager(getContext()));
                requestRv.setAdapter(requestListAdapter);
                progressBar.setVisibility(View.GONE);
            }
            else {
                Log.d("TAG", "waiting for info");
            }
        });

        return view;
    }
}