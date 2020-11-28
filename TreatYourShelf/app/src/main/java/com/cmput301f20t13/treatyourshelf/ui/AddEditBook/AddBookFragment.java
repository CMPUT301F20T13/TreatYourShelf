package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.Utils;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookDetails.BookDetailsFragmentArgs;
import com.cmput301f20t13.treatyourshelf.ui.BookDetails.BookDetailsViewModel;
import com.cmput301f20t13.treatyourshelf.ui.camera.CameraXFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AddBookFragment extends Fragment {

    private AddBookViewModel addBookViewModel;
    private BookDetailsViewModel bookDetailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_book, container, false);
        EditText titleEt = view.findViewById(R.id.add_book_title_et);
        EditText authorEt = view.findViewById(R.id.add_book_author_et);
        EditText descEt = view.findViewById(R.id.add_book_description_et);
        EditText isbnEt = view.findViewById(R.id.add_book_isbn_et);
        TextView ownerTv = view.findViewById(R.id.add_book_owner_tv);
        TextView addImagesBt = view.findViewById(R.id.add_book_pictures_button);
        Button saveBt = view.findViewById(R.id.add_book_save_button);
        ImageButton scanIsbnBt = view.findViewById(R.id.add_book_scan_isbn_button);
        ImageButton closeBt = view.findViewById(R.id.close_fragmentBt);
        RecyclerView selectedImagesRv = view.findViewById(R.id.selected_images_rv);
        addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);
        int category = BookDetailsFragmentArgs.fromBundle(getArguments()).getCategory();


        if (category == 1) {
            // Load book with old info
            bookDetailsViewModel = new ViewModelProvider(requireActivity()).get(BookDetailsViewModel.class);
            Book book = bookDetailsViewModel.getSelectedBook();
            if (book != null) {
                titleEt.setText(book.getTitle());
                authorEt.setText(book.getAuthor());
                descEt.setText(book.getDescription());
                addBookViewModel.scannedIsbn.setValue(book.getIsbn());
            }
        }


        selectedImagesRv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        SelectedImagesAdapter selectedImagesAdapter = new SelectedImagesAdapter(requireContext(), new ArrayList<>());
        selectedImagesAdapter.setOnClick(position -> {
            // Clicked on Selected Image
        });
        selectedImagesRv.setAdapter(selectedImagesAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();

            ownerTv.setText(email);
        }

        scanIsbnBt.setOnClickListener(view1 -> {
            // Open up fragment
            Utils.hideKeyboardFrom(requireContext(), view);
            NavDirections action = AddBookFragmentDirections.actionAddBookFragmentToCameraXFragment().setServiceCode(1);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
        });

        addImagesBt.setOnClickListener(view1 -> {
            BottomSheetGalleryImages bottomSheetGalleryImages = new BottomSheetGalleryImages();
            bottomSheetGalleryImages.show(getChildFragmentManager(), null);

        });

        addBookViewModel.scannedIsbn.observe(getViewLifecycleOwner(), isbnEt::setText);
        addBookViewModel.selectedImages.observe(getViewLifecycleOwner(), selectedImages ->
        {
            if (!selectedImages.isEmpty()) {
                selectedImagesAdapter.refreshImages(selectedImages);
                selectedImagesRv.smoothScrollToPosition(selectedImages.size() - 1);
            }
        });

        closeBt.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
        });

//      Saving addition to database
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(titleEt.getText().toString(), authorEt.getText().toString(), isbnEt.getText().toString(), descEt.getText().toString(), ownerTv.getText().toString(), null,
                        "available");
                if (category == 1) {
                    addBookViewModel.editBook(book);
                } else {
                    addBookViewModel.addBook(book);
                }

                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();


            }
        });

//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Book book = new Book(txtTitle.getText().toString(), txtAuthor.getText().toString(), txtIsbn.getText().toString());
//                book.setDescription(txtDesc.getText().toString());
//                addBookViewModel.editBook(book);
////                Toast.makeText(getActivity(),"book edited",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(getContext())
//                        .setTitle("Delete entry")
//                        .setMessage("Are you sure you want to delete this entry?")
//
//                        // Specifying a listener allows you to take an action before dismissing the dialog.
//                        // The dialog is automatically dismissed when a dialog button is clicked.
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Book book = new Book(txtTitle.getText().toString(), txtAuthor.getText().toString(), txtIsbn.getText().toString());
//                                addBookViewModel.deleteBook(book.getIsbn());
////                                Toast.makeText(getActivity(),"book deleted",Toast.LENGTH_SHORT).show();
//                            }
//                        })
//
//                        // A null listener allows the button to dismiss the dialog and take no further action.
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//            }
//        });


        return view;
    }

    @Override
    public void onDestroyView() {
        addBookViewModel.clearState();
        super.onDestroyView();
    }


}
