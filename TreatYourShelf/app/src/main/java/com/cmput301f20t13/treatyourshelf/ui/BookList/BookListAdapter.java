package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    private List<Book> bookList;


    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;

    }

    @NonNull
    @Override
    public BookListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.MyViewHolder holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());
        holder.bookItem.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_bookListFragment_to_bookDetailsFragment);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void clear() {
        bookList.clear();
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView author;
        private final MaterialCardView bookItem;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            bookItem = itemView.findViewById(R.id.book_list_item_cardview);
        }
    }
}

