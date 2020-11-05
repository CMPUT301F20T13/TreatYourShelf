package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BookDetailsFragment extends Fragment {
    private BookDetailsViewModel bookDetailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        ImageView image = view.findViewById(R.id.book_image);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);

        //Tab Layout that includes a Summary tab and Details Tab
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.book_details_view_pager);
        tabLayout.setupWithViewPager(viewPager);
        //Fragments within the Tab Layout
        BookDetailsDtab detailsFragment = new BookDetailsDtab();
        BookDetailsStab summaryFragment = new BookDetailsStab();

        //View Model
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        BookListViewModel bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        bookListViewModel.getSelected().observe(getViewLifecycleOwner(), book ->{
            title.setText(book.getTitle());
            author.setText(book.getAuthor());

            Bundle descBundle = new Bundle();
            descBundle.putString("description", book.getDescription());
            detailsFragment.setArguments(descBundle);

            Bundle detailBundle = new Bundle();
            detailBundle.putString("isbn", book.getIsbn());
            detailBundle.putString("owner", book.getOwner());
            detailBundle.putString("borrower", book.getBorrower());
            summaryFragment.setArguments(detailBundle);

        });

        BookViewPagerAdapter viewPagerAdapter = new BookViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(summaryFragment, "summary");
        viewPagerAdapter.addFragment(detailsFragment, "details");
        viewPager.setAdapter(viewPagerAdapter);

        FloatingActionButton requestButton = view.findViewById(R.id.book_request_button);
        if (bookListViewModel.requestSelected){requestButton.setVisibility(View.VISIBLE);}
        requestButton.setOnClickListener(v -> bookDetailsViewModel.requestBook());


        ImageButton editButton = view.findViewById(R.id.book_edit_button);
        if (bookListViewModel.editSelected){requestButton.setVisibility(View.VISIBLE);}
        editButton.setOnClickListener(v -> bookDetailsViewModel.editBook());


        return view;
    }



}
