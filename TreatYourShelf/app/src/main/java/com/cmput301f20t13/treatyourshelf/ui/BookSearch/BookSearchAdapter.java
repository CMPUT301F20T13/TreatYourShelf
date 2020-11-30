package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;

/**
 * The adapter that binds a list of books to the recyclerview in BookSearchFragment
 */
public class BookSearchAdapter extends RecyclerView.Adapter<BookSearchAdapter.MyViewHolder> {

private List<Book> bookList;

public BookSearchAdapter(List<Book> bookList) {
        this.bookList = bookList;
        }

@NonNull
@Override
public BookSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new MyViewHolder(view);

        }

@Override
public void onBindViewHolder(@NonNull BookSearchAdapter.MyViewHolder holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());
        holder.isbn.setText(bookList.get(position).getIsbn());
        holder.owner.setText(bookList.get(position).getOwner());
        holder.status.setText(bookList.get(position).getStatus());
        }

@Override
public int getItemCount() {
        return bookList.size();
        }

public void clear(){
        bookList.clear();
        }

public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
        }

public static class MyViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView author;
    private final TextView isbn;
    private final TextView owner;
    private final TextView status;

    public MyViewHolder(@NonNull View itemView) {

        super(itemView);

        title = itemView.findViewById(R.id.book_title);
        author = itemView.findViewById(R.id.book_author);
        isbn = itemView.findViewById(R.id.book_isbn);
        owner = itemView.findViewById(R.id.book_owner);
        status = itemView.findViewById(R.id.book_status);
    }
}

}