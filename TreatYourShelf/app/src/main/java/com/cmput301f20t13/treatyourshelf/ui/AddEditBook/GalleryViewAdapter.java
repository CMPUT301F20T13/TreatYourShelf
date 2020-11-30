package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
//
//import com.anthonymarkenterpises.showup.R;
//import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;
import java.util.List;

/**
 * an adapter for the galleryview
 */
public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.MyViewHolder> {

    private List<ImageFilePathSelector> images;
    private final Context mContext;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // 1
    public GalleryViewAdapter(Context context, List<ImageFilePathSelector> imageFilePaths) {
        this.mContext = context;
        images = imageFilePaths;
    }

    @NonNull
    @Override
    public GalleryViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_image, parent, false);
        return new GalleryViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewAdapter.MyViewHolder holder, int position) {
        final ImageFilePathSelector imageFilePathSelector = images.get(position);


        if (imageFilePathSelector.getImageSelectedState() == 1) {
            holder.galleryImage.setColorFilter(Color.argb(150, 200, 200, 200));
        } else {
            holder.galleryImage.setColorFilter(null);
        }
        holder.itemView.setOnClickListener(v -> onClick.onItemClick(position));
        Glide.with(mContext).load(images.get(position).getImageFilePath())
                .placeholder(circularProgressDrawableFactory(mContext))
                .into(holder.galleryImage);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView galleryImage;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            galleryImage = itemView.findViewById(R.id.galleryimage);

        }
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public void refreshImages(List<ImageFilePathSelector> images) {
        this.images.clear();
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    private CircularProgressDrawable circularProgressDrawableFactory(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary, null));
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public void setOnClick(GalleryViewAdapter.OnItemClicked onClick) {
        this.onClick = onClick;
    }

}

