package com.cmput301f20t13.treatyourshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.BottomSheetNavigationMenu;
import com.cmput301f20t13.treatyourshelf.ui.settings.BottomSheetSettingsMenu;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this.getBaseContext());
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        FloatingActionButton fab = findViewById(R.id.fab);
        bottomAppBar.setNavigationOnClickListener(view -> {

            BottomSheetNavigationMenu bottomSheetNavigationMenu = new BottomSheetNavigationMenu();
            bottomSheetNavigationMenu.show(getSupportFragmentManager(), null);
        });


        Navigation.findNavController(this, R.id.nav_host_fragment).addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {

                case R.id.cameraXFragment: {
                    // Want to remove the bottom app bar from view So the camera is full screen
                    fab.hide();
                    bottomAppBar.performHide();

                    break;
                }
                case R.id.loginFragment:
                case R.id.signUpFragment: {
                    // Want to remove the bottom app bar from view
                    bottomAppBar.performHide();
                    bottomAppBar.setVisibility(View.INVISIBLE);
                    fab.hide();
                    break;
                }
                default: {
                    bottomAppBar.setVisibility(View.VISIBLE);
                    bottomAppBar.performShow();
                    fab.show();

                    break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bottom_app_search: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.bookListFragment);
                break;
            }
            case R.id.app_settings: {
                BottomSheetSettingsMenu bottomSheetSettingsMenu = new BottomSheetSettingsMenu();
                bottomSheetSettingsMenu.show(getSupportFragmentManager(), null);
            }

        }
        return true;

    }
}