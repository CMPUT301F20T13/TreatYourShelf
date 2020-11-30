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

public class BottomSheetImageSourceSelection extends BottomSheetDialogFragment {

    private AddBookViewModel addBookViewModel;
    private GalleryViewAdapter galleryViewAdapter;
    private List<ImageFilePathSelector> imageFilePathSelectorList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_image_selection_option, container, false);
        addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);

        return view;
    }

}