package com.cmput301f20t13.treatyourshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.BottomSheetNavigationMenu;
import com.google.android.material.bottomappbar.BottomAppBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.setNavigationOnClickListener(view -> {

            BottomSheetNavigationMenu bottomSheetNavigationMenu = new BottomSheetNavigationMenu();
            bottomSheetNavigationMenu.show(getSupportFragmentManager(), null);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.bottom_app_search) {

            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.bookListFragment);
        }
        return true;

    }
}