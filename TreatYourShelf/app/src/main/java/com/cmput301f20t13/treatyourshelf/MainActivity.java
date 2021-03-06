package com.cmput301f20t13.treatyourshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cmput301f20t13.treatyourshelf.data.Notification;
import com.cmput301f20t13.treatyourshelf.ui.BookList.AllBooksFragmentDirections;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.BottomSheetNavigationMenu;
import com.cmput301f20t13.treatyourshelf.ui.settings.BottomSheetSettingsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * the main activity of the application
 */
public class MainActivity extends AppCompatActivity {
    /**
     * creates the activity view, initializes the fragments where all the functionality of
     * the application resides
     * @param savedInstanceState a bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this.getBaseContext());
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
                    if (Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId() == R.id.ownedBooksFragment) {
                        NavDirections action = NagivationGraphDirections.actionGlobalToAddBookfragment().setCategory(0);

                        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(action);
                    } else if (Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId() == R.id.bookDetailsFragment) {
                        NavDirections action = NagivationGraphDirections.actionGlobalToAddBookfragment().setCategory(1);
                        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(action);

                    }

                }
        );
        bottomAppBar.setNavigationOnClickListener(view -> {

            BottomSheetNavigationMenu bottomSheetNavigationMenu = new BottomSheetNavigationMenu();
            bottomSheetNavigationMenu.show(getSupportFragmentManager(), null);
        });


        Navigation.findNavController(this, R.id.nav_host_fragment).addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {

                case R.id.cameraXFragment: {
                    // Want to remove the bottom app bar from view So the camera is full screen
                    fab.hide();
                    bottomAppBar.setVisibility(View.INVISIBLE);
                    bottomAppBar.performHide();

                    break;
                }
                case R.id.loginFragment:
                case R.id.mapsFragmentBorrower:
                case R.id.mapsFragmentOwner:
                case R.id.signUpFragment:
                case R.id.addBookFragment: {
                    // Want to remove the bottom app bar from view So the camera is full screen
                    bottomAppBar.performHide();
                    bottomAppBar.setVisibility(View.INVISIBLE);
                    fab.hide();
                    break;
                }
                case R.id.bookDetailsFragment: {
                    if (arguments.getInt("category") == 1) {
                        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit_book));
                        bottomAppBar.setVisibility(View.VISIBLE);
                        bottomAppBar.performShow();
                        fab.show();
                    } else {
                        bottomAppBar.performHide();
                        bottomAppBar.setVisibility(View.INVISIBLE);
                        fab.hide();
                    }
                    break;
                }
                case R.id.ownedBooksFragment: {
                    fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_round_add_24));
                    bottomAppBar.setVisibility(View.VISIBLE);
                    bottomAppBar.performShow();
                    fab.show();
                    break;
                }
                case R.id.bookSearchFragment:
                case R.id.requestListFragment:
                case R.id.borrRequestedListFragment:
                case R.id.requestDetailsFragment:
                case R.id.bookListFragment: {
                    bottomAppBar.setVisibility(View.VISIBLE);
                    bottomAppBar.performShow();
                    fab.hide();
                    break;
                }
                default: {
                    fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
                    bottomAppBar.setVisibility(View.VISIBLE);
                    bottomAppBar.performShow();
                    fab.show();

                    break;
                }
            }
        });
    }

    /**
     * creates the bottom menu for navigation
     * @param menu a menu object
     * @return a boolean upon successful completion
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    /**
     * a listener for when a menu option is selected
     * @param item the menuItem that is selected
     * @return a boolean, returns true if the item that links to the bottom_app_search is selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bottom_app_search: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.bookSearchFragment);
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