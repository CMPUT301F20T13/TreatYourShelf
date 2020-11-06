package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmput301f20t13.treatyourshelf.R;

import org.w3c.dom.Text;

/**
 * BookDetailsDtab holds and displays the details of the book which include:
 * isbn, the book owner, and the current borrower of the book
 */
public class BookDetailsDtab extends Fragment {

    public BookDetailsDtab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details_dtab, container, false);
        TextView isbn = view.findViewById(R.id.book_isbn);
        TextView owner = view.findViewById(R.id.book_owner);
        TextView currentBorrower = view.findViewById(R.id.book_borrower);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String isbnString = bundle.getString("isbn");
            String ownerString = bundle.getString("owner");
            String currentBorrowerString = bundle.getString("borrower");

            isbn.setText(isbnString);
            owner.setText(ownerString);
            currentBorrower.setText(currentBorrowerString);

        }

        return view;
    }

}