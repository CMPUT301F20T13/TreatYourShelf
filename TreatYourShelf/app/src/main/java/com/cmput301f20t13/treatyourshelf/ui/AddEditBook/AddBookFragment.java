package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
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
import com.cmput301f20t13.treatyourshelf.ui.BookList.AllBooksFragmentDirections;
import com.cmput301f20t13.treatyourshelf.ui.camera.CameraXFragmentDirections;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

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
        ImageButton menuBt = view.findViewById(R.id.add_edit_book_menu_bt);
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
                isbnEt.setText(book.getIsbn());
                List<ImageFilePathSelector> bookImages = new ArrayList<>();
                for (String imageUrl : book.getImageUrls()
                ) {
                    ImageFilePathSelector imageFilePathSelector = new ImageFilePathSelector();
                    imageFilePathSelector.setImageFilePath(Uri.parse(imageUrl));
                    imageFilePathSelector.setImageSelectedState(0);
                    imageFilePathSelector.setImageUrl(true);
                    bookImages.add(imageFilePathSelector);
                }
                addBookViewModel.selectedImages.setValue(bookImages);
            }
            menuBt.setVisibility(View.VISIBLE);
        }


        selectedImagesRv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        SelectedImagesAdapter selectedImagesAdapter = new SelectedImagesAdapter(requireContext(), new ArrayList<>());
        selectedImagesAdapter.setOnClick((position, itemView) -> {
            // Clicked on Selected Image, Show popup menu
            PopupMenu popup = new PopupMenu(requireContext(), itemView);
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.delete_book) {
                    // Delete Image
                    addBookViewModel.deleteImage(position);
                    return true;
                }
                return false;
            });


            popup.getMenuInflater().inflate(R.menu.edit_book_menu, popup.getMenu());
            popup.show();
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

        addBookViewModel.scannedIsbn.observe(getViewLifecycleOwner(), isbn -> {
            if (isbn != null && !isbn.equals("")) {
                isbnEt.setText(isbn);
            }


        });
        addBookViewModel.selectedImages.observe(getViewLifecycleOwner(), selectedImages ->
        {

            selectedImagesAdapter.refreshImages(selectedImages);
            if (selectedImages != null && !selectedImages.isEmpty()) {
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
                if (!titleEt.getText().toString().isEmpty() && !authorEt.getText().toString().isEmpty() && !isbnEt.getText().toString().isEmpty() && isbnEt.getText().toString().length() == 13) {


                    Book book = new Book(titleEt.getText().toString(), authorEt.getText().toString(), isbnEt.getText().toString(), descEt.getText().toString(), ownerTv.getText().toString(), null,
                            "available");
                    if (category == 1) {
                        Book oldBook = bookDetailsViewModel.getSelectedBook();
                        String oldIsbn = oldBook.getIsbn();
                        addBookViewModel.editBook(book, oldIsbn);
                        NavDirections action = AddBookFragmentDirections.actionAddBookFragmentToBookDetailsFragment().setISBN(book.getIsbn()).setCategory(1);
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);
                    } else {
                        addBookViewModel.addBook(book);
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.ownedBooksFragment);
                    }

                } else {
                    if (isbnEt.getText().toString().length() != 13) {
                        Toast.makeText(requireContext(), "Please verify that your isbn is 13 characters long", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        menuBt.setOnClickListener(view1 -> {
            PopupMenu popup = new PopupMenu(requireContext(), view1);
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.delete_book) {
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Delete Book")
                            .setMessage("Are you sure you want to delete this book? This action is irreversible.")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Book book = bookDetailsViewModel.getSelectedBook();
                                    addBookViewModel.deleteBook(book.getIsbn());

                                    NavDirections action;
                                    if (category == 1) {
                                        action = AddBookFragmentDirections.actionAddBookFragmentToOwnedBooksFragment();
                                    } else {
                                        action = AddBookFragmentDirections.actionAddBookFragmentToBookListFragment();
                                    }
                                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action);

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                    return true;
                }
                return false;
            });


            popup.getMenuInflater().inflate(R.menu.edit_book_menu, popup.getMenu());
            popup.show();
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
//
//        });


        return view;
    }

    @Override
    public void onDestroyView() {
        addBookViewModel.clearState();
        super.onDestroyView();
    }


}
