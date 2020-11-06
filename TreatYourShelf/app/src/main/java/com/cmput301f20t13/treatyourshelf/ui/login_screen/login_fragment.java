package com.cmput301f20t13.treatyourshelf.ui.login_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Login Fragment, allows the user to log in
 * using there email address and a password.
 * Uses firebase authentication
 */
public class login_fragment extends Fragment {

    private FirebaseAuth mAuth;
    private final String TAG = "LOGIN_FRAGMENT";

    private TextInputLayout email_layout;
    private TextInputLayout password_layout;

    /**
     * On Start is called prior to onCreateView.
     * Contains firebase initialization steps and
     * checks if the user is already logged in.
     */
    @Override
    public void onStart() {
        super.onStart();

        FirebaseApp.initializeApp(getContext());

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            navigateToNextScreen();
        }
    }

    /**
     * This creates the fragment view.
     * @param inflater the view inflater used to create the view
     * @param container the viewGroup
     * @param savedInstanceState a bundle of the current state
     * @return      returns the view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        Button login_btn = (Button) view.findViewById(R.id.login_btn);
        email_layout = (TextInputLayout) view.findViewById(R.id.email_layout);
        password_layout = (TextInputLayout) view.findViewById(R.id.password_layout);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fields are not empty
                String email, password;
                email = email_layout.getEditText().getText().toString();
                password = password_layout.getEditText().getText().toString();
                checkValid(email, password);
            }
        });

        return view;
    }

    /**
     * Sets the error option on the material textviews
     * @param errorExists If there is an error
     */
    public void setError(boolean errorExists){
        if(!errorExists){
            email_layout.setError(null);
            password_layout.setError(null);
        }else{
            email_layout.setError("Email and Password does not match preexisting user.");
            password_layout.setError("Email and Password does not match preexisting user.");
        }
    }

    /**
     * Checks if the user email and password match a user on firebase.
     * @param email the email provided.
     * @param password the password provided.
     */
    public void checkValid(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Login in successful.", Toast.LENGTH_SHORT).show();
                            setError(false);
                            navigateToNextScreen();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Login in failed.", Toast.LENGTH_SHORT).show();
                            setError(true);
                        }
                    }
                });
    }

    /**
     * Navigates to the next screen
     */
    public void navigateToNextScreen(){

    }

}
