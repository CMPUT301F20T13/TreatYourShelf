package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cmput301f20t13.treatyourshelf.R;


public class RequestDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_details, container, false);
        TextView requestedUser = view.findViewById(R.id.user_request);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView isbn = view.findViewById(R.id.book_isbn);
        TextView owner = view.findViewById(R.id.book_owner);
        Button acceptButton = view.findViewById(R.id.accept_button);
        Button declineButton = view.findViewById(R.id.decline_button);

        return view;
    }



}




