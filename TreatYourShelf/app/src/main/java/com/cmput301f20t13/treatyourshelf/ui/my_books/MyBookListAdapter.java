package com.cmput301f20t13.treatyourshelf.ui.my_books;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;

public class MyBookListAdapter extends RecyclerView.Adapter<MyBookListAdapter.MyViewHolder>{
    private List<Book> bookList;
    private OnItemClicked onClick;

    public MyBookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public MyBookListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new MyBookListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookListAdapter.MyViewHolder holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
        }
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    public void setOnClick(MyBookListAdapter.OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}
