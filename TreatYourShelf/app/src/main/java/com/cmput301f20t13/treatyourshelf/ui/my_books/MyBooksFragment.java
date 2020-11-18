package com.cmput301f20t13.treatyourshelf.ui.my_books;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.ArrayList;
import java.util.List;

public class MyBooksFragment extends Fragment {
    private MyBookListAdapter myBookListAdapter;
    private MyBookViewModel myBookViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        RecyclerView bookList_rv = view.findViewById(R.id.book_list_rv);
        bookList_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        myBookListAdapter = new MyBookListAdapter(new ArrayList<Book>());
        bookList_rv.setAdapter(myBookListAdapter);
        myBookListAdapter.setOnClick(new MyBookListAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "Do You Smell Burnt Toast?", Toast.LENGTH_SHORT).show();
            }
        });

        myBookViewModel = new ViewModelProvider(this).get(MyBookViewModel.class);


        return view;
    }


}
