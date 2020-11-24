package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;

import java.util.List;

public class SelectedImagesAdapter extends RecyclerView.Adapter<SelectedImagesAdapter.MyViewHolder> {

    private List<ImageFilePathSelector> images;
    private final Context mContext;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // 1
    public SelectedImagesAdapter(Context context, List<ImageFilePathSelector> imageFilePaths) {
        this.mContext = context;
        images = imageFilePaths;
    }

    @NonNull
    @Override
    public SelectedImagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_image, parent, false);
        return new SelectedImagesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedImagesAdapter.MyViewHolder holder, int position) {
        final ImageFilePathSelector imageFilePathSelector = images.get(position);


        holder.itemView.setOnClickListener(v -> onClick.onItemClick(position));
        Glide.with(mContext).load(images.get(position).getImageFilePath())
                .placeholder(circularProgressDrawableFactory(mContext))
                .into(holder.selectedImage);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView selectedImage;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            selectedImage = itemView.findViewById(R.id.selected_image);

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

    public void setOnClick(SelectedImagesAdapter.OnItemClicked onClick) {
        this.onClick = onClick;
    }

}

