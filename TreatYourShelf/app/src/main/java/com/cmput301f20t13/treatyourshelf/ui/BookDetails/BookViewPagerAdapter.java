package com.cmput301f20t13.treatyourshelf.ui.BookDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * BookViewPagerAdapter sets the fragments in book details onto the tab layout
 */
public class BookViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

    /**
     * Creates the adapter
     * @param fm the fragment manager
     * @param behavior behavior of the adapter
     */
    public BookViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

    /**
     * Adds the fragment and title to the lists
     * @param fragment fragment to manage
     * @param title title of the tab
     */
    public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }

}
