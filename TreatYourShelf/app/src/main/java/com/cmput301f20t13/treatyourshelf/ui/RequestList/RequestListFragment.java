package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListAdapter;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListViewModel;

import java.util.ArrayList;

public class RequestListFragment extends Fragment {

    private RequestListAdapter requestListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        RequestListViewModel requestListViewModel = new ViewModelProvider(requireActivity()).get(RequestListViewModel.class);
        ArrayList<Request> requestArray = new ArrayList<>();
        requestListAdapter = new RequestListAdapter(requestArray, requestListViewModel);

/*        String bookId = requestListViewModel.getSelected().getBookId();
        requestListViewModel.getRequestByIdLiveData().observe(getViewLifecycleOwner(), Observable -> {});*/

        requestListViewModel.getRequestList().observe(getViewLifecycleOwner(), requestList -> {
            if (requestList != null ) {
                requestListAdapter.clear();
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