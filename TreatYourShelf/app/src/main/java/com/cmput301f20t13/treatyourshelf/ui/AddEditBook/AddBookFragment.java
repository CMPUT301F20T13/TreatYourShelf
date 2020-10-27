package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        final int selected;
        if(getArguments() != null) {
            Book book = (Book) getArguments().getSerializable("book");
            selected = getArguments().getInt("selected");
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtDesc.setText(book.getDescription());
            txtIsbn.setText(book.getIsbn());
            Toast.makeText(getActivity(),"Do You Smell Burnt Toast?",Toast.LENGTH_SHORT).show();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(txtTitle.getText().toString(), txtAuthor.getText().toString());
                book.setDescription(txtDesc.getText().toString());
                book.setIsbn(txtIsbn.getText().toString());
            }
        });


        return view;
    }

    static AddBookFragment newInstance (int selected, Book book) {
        Bundle args = new Bundle();
        args.putSerializable("book", book);
        args.putInt("selected", selected);
        AddBookFragment fragment = new AddBookFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
