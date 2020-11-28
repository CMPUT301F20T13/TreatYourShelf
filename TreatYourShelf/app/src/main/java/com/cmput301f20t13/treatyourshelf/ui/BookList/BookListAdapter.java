package com.cmput301f20t13.treatyourshelf.ui.BookList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    private List<Book> bookList;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position, Book book);
    }


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
        holder.itemView.setOnClickListener(v -> onClick.onItemClick(position, bookList.get(position)));

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

    public List<Book> getBookList() {
        return bookList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView author;
        private final MaterialCardView bookItem;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            bookItem = itemView.findViewById(R.id.search_list_item_cardview);
        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}

