package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class LoadGalleryImagesTask implements Callable<List<ImageFilePathSelector>> {

    private Context context;

    public LoadGalleryImagesTask(Context context) {
        this.context = context;
    }

    @Override
    public List<ImageFilePathSelector> call() throws Exception {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        List<ImageFilePathSelector> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER};

        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);

        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);


        while (cursor.moveToNext()) {
            ImageFilePathSelector imageFilePathSelector = new ImageFilePathSelector();
            long id = cursor.getLong(idColumn);
            Uri contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            imageFilePathSelector.setImageFilePath(contentUri);
            imageFilePathSelector.setImageSelectedState(0);
            listOfAllImages.add(imageFilePathSelector);
        }

        // reverse order of array
        Collections.reverse(listOfAllImages);

        return new ArrayList<>(listOfAllImages);


    }
}
