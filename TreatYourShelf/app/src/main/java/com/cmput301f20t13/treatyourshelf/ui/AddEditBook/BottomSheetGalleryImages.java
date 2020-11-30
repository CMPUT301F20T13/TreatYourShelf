package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * a gallery of images to choose an image for a book
 */
public class BottomSheetGalleryImages extends BottomSheetDialogFragment {

    private AddBookViewModel addBookViewModel;
    private GalleryViewAdapter galleryViewAdapter;
    private List<ImageFilePathSelector> imageFilePathSelectorList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_gallery_images, container, false);
        addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);
        ImageButton closeBottomSheet = view.findViewById(R.id.close_bottomsheet);
        Button selectImageBt = view.findViewById(R.id.select_image_bt);
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


        RecyclerView galleryView = view.findViewById(R.id.gallery_rv);
        galleryView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        galleryViewAdapter = new GalleryViewAdapter(requireContext(), imageFilePathSelectorList);
        galleryViewAdapter.setOnClick(new GalleryViewAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                // Item clicked
                ImageFilePathSelector selectedImage = addBookViewModel.galleryImagesLiveData.getValue().get(position);
                addBookViewModel.selectedImage.setValue(selectedImage);
                addBookViewModel.updateListWithSelectedItem(position);

            }
        });
        galleryView.setAdapter(galleryViewAdapter);

        addBookViewModel.galleryImagesLiveData.observe(getViewLifecycleOwner(), galleryImageList -> {
            galleryViewAdapter.refreshImages(galleryImageList);
        });
        addBookViewModel.selectedImage.observe(getViewLifecycleOwner(), selectedImage -> {
            selectImageBt.setEnabled(selectedImage != null);

        });

        selectImageBt.setOnClickListener(view1 -> {
            addBookViewModel.addSelectedImageToAddBook();
            dismiss();
        });
        closeBottomSheet.setOnClickListener(view1 -> {
            dismiss();
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

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //dialog dismissed, reset values in viewmodel
        addBookViewModel.resetSelectedImages();

    }
}
