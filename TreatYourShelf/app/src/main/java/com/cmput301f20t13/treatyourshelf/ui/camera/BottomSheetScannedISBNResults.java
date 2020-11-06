package com.cmput301f20t13.treatyourshelf.ui.camera;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItem;
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItemAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetScannedISBNResults extends BottomSheetDialogFragment {

    private OnDialogDismissedListener onDismissListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_barcode_scanning_results, container, false);

        ImageView closeBottomSheet = view.findViewById(R.id.close_bottomsheet);
        Button viewBookDetailsBt = view.findViewById(R.id.view_details_bt);
        viewBookDetailsBt.setOnClickListener(view1 -> {
            // Want to navigate to Book Details Screen
            NavDirections action = CameraXFragmentDirections.actionCameraXFragmentToBookDetailsFragment().setISBN(0);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
            dismiss();

        });
        closeBottomSheet.setOnClickListener(view1 -> dismiss());
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        onDismissListener.onDialogDismissed();
    }

    public interface OnDialogDismissedListener {
        void onDialogDismissed();
    }

    public void setDissmissListener(OnDialogDismissedListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

}
