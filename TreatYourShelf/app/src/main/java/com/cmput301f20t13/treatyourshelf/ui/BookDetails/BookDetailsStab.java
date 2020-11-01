package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmput301f20t13.treatyourshelf.R;


public class BookDetailsStab extends Fragment {

    public BookDetailsStab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details_stab, container, false);
        TextView description = view.findViewById(R.id.book_description);


        if (getArguments() != null) {
            String descString = getArguments().getString("description");
            description.setText(descString);
        }

        return view;
    }
}