package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;

public class BottomSheetImageSourceSelection extends BottomSheetDialogFragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private AddBookViewModel addBookViewModel;
    private GalleryViewAdapter galleryViewAdapter;
    private String mCurrentPhotoPath;
    private List<ImageFilePathSelector> imageFilePathSelectorList = new ArrayList<>();
    private String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_image_selection_option, container, false);
        addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);


        ImageButton galleryBt = view.findViewById(R.id.select_Gallery);

        ImageButton cameraBt = view.findViewById(R.id.select_Camera);


        galleryBt.setOnClickListener(view1 -> {

            BottomSheetGalleryImages bottomSheetGalleryImages = new BottomSheetGalleryImages();
            bottomSheetGalleryImages.show(getChildFragmentManager(), null);
            this.dismiss();

        });

        cameraBt.setOnClickListener(view12 -> {

            if (allPermissionsGranted()) {
                startCamera();
            } else {
                requestPermissions(permissions, 11);
            }


        });

        return view;
    }

    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Error", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.example.android.fileprovider",
                        photoFile);


                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("We hit this checkpoint");
            galleryAddPic();
            ImageFilePathSelector imageFilePathSelector = new ImageFilePathSelector();
            imageFilePathSelector.setImageSelectedState(0);
            imageFilePathSelector.setImageFilePath(Uri.fromFile(new File(mCurrentPhotoPath)));

            addBookViewModel.setSelectedImage(imageFilePathSelector);
            addBookViewModel.addSelectedImageToAddBook();
            dismiss();
//            try {
////                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
////                mImageView.setImageBitmap(mImageBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        requireContext().sendBroadcast(mediaScanIntent);
    }

    private Boolean allPermissionsGranted() {


        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == 11) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // storage-related task you need to do.// load images
                startCamera();
            } else {
                // permission denied, dimiss dialog
                dismiss();
            }
        }
    }
}