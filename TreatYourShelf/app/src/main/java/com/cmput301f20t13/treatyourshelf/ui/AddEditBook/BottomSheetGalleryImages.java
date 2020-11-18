package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class BottomSheetGalleryImages extends BottomSheetDialogFragment {

    private AddBookViewModel addBookViewModel;
    private GalleryViewAdapter galleryViewAdapter;
    private List<ImageFilePathSelector> imageFilePathSelectorList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_gallery_images, container, false);
        addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            // No explanation needed; request the permission
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        } else {
            //Permission already granted
            addBookViewModel.getGalleryImages();
        }


        GridView gridView = view.findViewById(R.id.gallery_gv);
        galleryViewAdapter = new GalleryViewAdapter(requireContext(), imageFilePathSelectorList);

        gridView.setAdapter(galleryViewAdapter);

        addBookViewModel.listMutableLiveDataGalleryImages.observe(getViewLifecycleOwner(), galleryImageList -> {
            galleryViewAdapter.refreshImages(galleryImageList);
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // storage-related task you need to do.// load images
                addBookViewModel.getGalleryImages();
            } else {
                // permission denied, dimiss dialog
                dismiss();
            }
        }
    }
}
