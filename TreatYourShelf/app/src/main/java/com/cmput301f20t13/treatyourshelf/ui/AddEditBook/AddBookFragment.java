package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.io.Serializable;

public class AddBookFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_book, container, false);
        EditText txtTitle = (EditText) view.findViewById(R.id.title);
        EditText txtAuthor = (EditText) view.findViewById(R.id.author);
        EditText txtDesc = (EditText) view.findViewById(R.id.desc);
        EditText txtIsbn = (EditText) view.findViewById(R.id.isbn);
        Button addButton = (Button) view.findViewById(R.id.addbutton);
        Button editButton = (Button) view.findViewById(R.id.editbutton);
        Button deleteButton = (Button) view.findViewById(R.id.deletebutton);

        addButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        final int selected;
        /*
        for editing an existing book
         */
        if(getArguments() != null) {
            Book book = (Book) getArguments().getSerializable("book");
            selected = getArguments().getInt("selected");
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtDesc.setText(book.getDescription());
            txtIsbn.setText(book.getIsbn());
            addButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }


        /*
        adding a book
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(txtTitle.getText().toString(), txtAuthor.getText().toString());
                book.setDescription(txtDesc.getText().toString());
                book.setIsbn(txtIsbn.getText().toString());
                Toast.makeText(getActivity(),"Do You Smell Burnt Toast?",Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        return view;
    }


    /*
    for editing an existing book
     */
    static AddBookFragment newInstance (int selected, Book book) {
        Bundle args = new Bundle();
        args.putSerializable("book", book);
        args.putInt("selected", selected);
        AddBookFragment fragment = new AddBookFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
