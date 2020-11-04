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
import com.cmput301f20t13.treatyourshelf.ui.BookList.BorrReqBooksViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookDetailsFragment extends Fragment {
    private Book book;
    private BookDetailsViewModel bookDetailsViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        ImageView image = view.findViewById(R.id.book_image);
        MaterialCardView cardView = view.findViewById(R.id.book_details_cardview);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);
        FloatingActionButton requestButton = view.findViewById(R.id.book_request_button);
        ImageButton editButton = view.findViewById(R.id.book_edit_button);

        //Tab Layout that includes a Summary tab and Details Tab
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.book_details_view_pager);
        tabLayout.setupWithViewPager(viewPager);

        BookDetailsDtab detailsFragment = new BookDetailsDtab();
        BookDetailsStab summaryFragment = new BookDetailsStab();

        BookViewPagerAdapter viewPagerAdapter = new BookViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(summaryFragment, "summary");
        viewPagerAdapter.addFragment(detailsFragment, "details");
        viewPager.setAdapter(viewPagerAdapter);

        //View Model
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);


        /*TODO depending on the user/when fragment gets instantiate, hide or show the request button*/
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookDetailsViewModel.requestBook();
            }
        });

        /*TODO depending on the user/when fragment gets instantiate, hide or show the edit button*/
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookDetailsViewModel.editBook();
            }
        });


        return view;
    }





}
