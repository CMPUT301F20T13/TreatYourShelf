package com.cmput301f20t13.treatyourshelf.ui.camera;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookDetails.BookDetailsFragmentArgs;
import com.cmput301f20t13.treatyourshelf.ui.BookDetails.BookDetailsViewModel;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItem;
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItemAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetScannedISBNResults extends BottomSheetDialogFragment {
    private String isbn;
    private BookDetailsViewModel bookDetailsViewModel;
    private OnDialogDismissedListener onDismissListener;

    BottomSheetScannedISBNResults(String isbn) {
        this.isbn = isbn;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_barcode_scanning_results, container, false);

        ImageView closeBottomSheet = view.findViewById(R.id.close_bottomsheet);
        Button viewBookDetailsBt = view.findViewById(R.id.view_details_bt);
        TextView resultsFound = view.findViewById(R.id.results_found);
        TextView bookAuthor = view.findViewById(R.id.found_book_author);
        TextView bookIsbn = view.findViewById(R.id.found_book_isbn);
        TextView bookOwner = view.findViewById(R.id.found_book_owner);
        TextView bookTitle = view.findViewById(R.id.found_book_title);
        TextView bookDescription = view.findViewById(R.id.found_book_description);

        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.getBookByIsbnLiveData(isbn ).observe(getViewLifecycleOwner(), Observable -> {
        });

        bookListViewModel.getBookList().observe(getViewLifecycleOwner(), bookList -> {
            if (!bookList.isEmpty()) {

                Book book = bookList.get(0);
                System.out.println(book.getTitle());
                resultsFound.setText("1 match found");
                bookTitle.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                bookIsbn.setText(book.getIsbn());
                bookDescription.setText(book.getDescription());
                bookOwner.setText(book.getOwner());

            } else {
                resultsFound.setText("No match found");
                Log.d("TAG", "waiting for info");
            }
        });
        viewBookDetailsBt.setOnClickListener(view1 -> {
            // Want to navigate to Book Details Screen
            NavDirections action = CameraXFragmentDirections.actionCameraXFragmentToBookDetailsFragment().setISBN(isbn);
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
