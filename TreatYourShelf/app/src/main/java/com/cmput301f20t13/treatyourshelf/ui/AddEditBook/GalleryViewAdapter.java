package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

//import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
//
//import com.anthonymarkenterpises.showup.R;
//import com.bumptech.glide.Glide;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.cmput301f20t13.treatyourshelf.R;

import java.util.List;

public class GalleryViewAdapter extends BaseAdapter {

    private List<ImageFilePathSelector> images;
    private final Context mContext;


    // 1
    public GalleryViewAdapter(Context context, List<ImageFilePathSelector> imageFilePaths) {
        this.mContext = context;
        images = imageFilePaths;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void refreshImages(List<ImageFilePathSelector> images) {
        Log.e("do we hit?", "???");
        this.images.clear();
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_gallery_image, null);
        }

        final ImageFilePathSelector imageFilePathSelector = images.get(position);
        ImageView imageView = convertView.findViewById(R.id.galleryimage);


        if (imageFilePathSelector.getImageSelectedState() == 1) {
            imageView.setColorFilter(Color.argb(150, 200, 200, 200));
        } else {
            imageView.setColorFilter(null);
        }
        Glide.with(mContext).load(images.get(position).getImageFilePath())
                .placeholder(circularProgressDrawableFactory(mContext))
                .into(imageView);

        return convertView;
    }
    private CircularProgressDrawable circularProgressDrawableFactory(Context context){
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary,null));
        circularProgressDrawable.start();
        return  circularProgressDrawable;
    }

}

