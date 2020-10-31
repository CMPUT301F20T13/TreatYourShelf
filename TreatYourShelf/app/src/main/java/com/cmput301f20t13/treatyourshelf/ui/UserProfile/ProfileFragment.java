package com.cmput301f20t13.treatyourshelf.ui.UserProfile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private BookListAdapter bookListAdapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView image = view.findViewById(R.id.profile_image);
        TextView username = view.findViewById(R.id.profile_username);

        TableLayout profileInfo = view.findViewById(R.id.profile_info_table);
        TextView email = view.findViewById(R.id.profile_email);
        TextView phone = view.findViewById(R.id.profile_phone);
        // TODO: This should be set with intent of fragment
        String profileUsername = "test_username";
        db.collection("users")
                .whereEqualTo("username", profileUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            username.setText((String) document.getData().getOrDefault("username", "default"));
                            email.setText((String) document.getData().getOrDefault("email", "No Email Address"));
                            phone.setText((String) document.getData().getOrDefault("phoneNumber", "No Phone number"));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

        // TODO: Make it so this block of code does not need to be repeated in other places?
        RecyclerView bookRv = view.findViewById(R.id.profile_book_list_rv);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListAdapter = new BookListAdapter(new ArrayList<>());
        bookRv.setAdapter(bookListAdapter);
        db.collection("books")
                .whereEqualTo("ownerUsername", profileUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String title;
                        String author;
                        List<Book> books = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            title = (String) document.getData().getOrDefault("title", "default title");
                            author = (String) document.getData().getOrDefault("author", "default author");
                            books.add(new Book(title, author));
                        }
                        bookListAdapter.setBookList(books);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

        Button editButton = view.findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(v -> profileViewModel.editProfile());

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);


        return view;
    }


}
