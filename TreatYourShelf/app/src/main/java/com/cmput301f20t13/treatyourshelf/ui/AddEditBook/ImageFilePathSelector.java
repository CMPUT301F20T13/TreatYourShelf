package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.net.Uri;

/**
 * selects the file path of the image a user selects
 */
public class ImageFilePathSelector {

    private Uri imageFilePath;
    private boolean isImageUrl = false;
    private int imageSelectedState;

    public Uri getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(Uri imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public int getImageSelectedState() {
        return imageSelectedState;
    }

    public void setImageSelectedState(int imageSelectedState) {
        this.imageSelectedState = imageSelectedState;
    }

    public void toggleSelectedState() {
        if (this.imageSelectedState == 1) {
            this.imageSelectedState = 0;
        } else {
            this.imageSelectedState = 1;
        }
    }

    public boolean isImageUrl() {
        return isImageUrl;
    }

    public void setImageUrl(boolean imageUrl) {
        isImageUrl = imageUrl;
    }
}
