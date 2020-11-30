package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.Utils;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.AllBooksFragmentDirections;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * adapter for the recyclerview used in the BookDetailsFragment
 */
public class BookImagesAdapter extends RecyclerView.Adapter<BookImagesAdapter.MyViewHolder> {

    private List<String> imageUrls;
    private Context context;

    public BookImagesAdapter(List<String> imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;

    }

    @NonNull
    @Override
    public BookImagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_image, parent, false);
        return new BookImagesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookImagesAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(imageUrls.get(position))
                .placeholder(Utils.circularProgressDrawableFactory(context))
                .into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public void clear() {
        imageUrls.clear();
    }

    public void setImages(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView bookImage;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
        }
    }

}

