package com.cmput301f20t13.treatyourshelf.ui.signup_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Profile;
import com.cmput301f20t13.treatyourshelf.ui.UserProfile.ProfileRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * Sign up Fragment, allows the user to sign up
 * using there email address and a password.
 * Both Fields cannot be empty.
 * Uses firebase authentication.
 */
public class SignUpFragment extends Fragment {

    private FirebaseAuth mAuth;
    private final String TAG = "SIGNUP_FRAGMENT";


    /**
     * On Start is called prior to onCreateView.
     * Contains firebase initialization steps and
     * checks if the user is already logged in.
     */
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToNextScreen();
        }
    }


    /**
     * This creates the fragment view.
     *
     * @param inflater           the view inflater used to create the view
     * @param container          the viewGroup
     * @param savedInstanceState a bundle of the current state
     * @return returns the view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();

        Button signup_btn = (Button) view.findViewById(R.id.signup_btn);
        TextInputLayout email_layout = (TextInputLayout) view.findViewById(R.id.email_layout);
        TextInputLayout password_layout = (TextInputLayout) view.findViewById(R.id.password_layout);
        TextInputLayout phone_layout = (TextInputLayout) view.findViewById(R.id.phone_layout);
        TextView already_member_tv = (TextView) view.findViewById(R.id.already_member_tv);

        // Handles sign up button click
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fields are not empty
                String email, password, phone;
                email = email_layout.getEditText().getText().toString();
                password = password_layout.getEditText().getText().toString();
                phone = phone_layout.getEditText().getText().toString();
                if (email.isEmpty()) {
                    email_layout.setError("Email field can not be empty.");
                } else if (password.isEmpty()) {
                    password_layout.setError("Password field can not be empty.");
                } else if (password.length() < 6) {
                    // If password is less than 6 characters in length, set error
                    password_layout.setError("Password must be at least 6 characters long!");
                } else { // If non-empty password and email
                    // Remove whitespace from email
                    createNewUser(email.replaceAll("\\s+", ""), password, phone);
                }
            }
        });

        // Handles already a member text field clicked
        already_member_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Navigate to login", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }
        });

        return view;
    }


    /**
     * Creates a new user using firebase authentication.
     *
     * @param email    the email address provided.
     * @param password the password provided.
     */
    public void createNewUser(String email, String password, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Add the profile
                            Profile profile = new Profile();
                            profile.setEmail(email);
                            profile.setPassword(password);
                            profile.setPhoneNumber(phone);
                            ProfileRepository.addProfile(profile);

                            Toast.makeText(getContext(), "Sign up successful.", Toast.LENGTH_SHORT).show();
                            navigateToNextScreen();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Email address is already taken.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Navigates to the next screen
     */
    public void navigateToNextScreen() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.bookListFragment);
    }

    /**
     * Navigates to the login screen
     */
    public void navigateToLogin() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.loginFragment);
    }
}
