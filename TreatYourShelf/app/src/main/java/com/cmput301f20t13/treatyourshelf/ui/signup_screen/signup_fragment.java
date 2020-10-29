package com.cmput301f20t13.treatyourshelf.ui.signup_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.ui.login_screen.login_fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class signup_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        Button signup_btn = (Button) view.findViewById(R.id.signup_btn);
        TextInputLayout email_layout = (TextInputLayout) view.findViewById(R.id.email_layout);
        TextInputLayout password_layout = (TextInputLayout) view.findViewById(R.id.password_layout);
        TextView already_member_tv = (TextView) view.findViewById(R.id.already_member_tv);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fields are not empty
                String email, password;
                email = email_layout.getEditText().getText().toString();
                password = password_layout.getEditText().getText().toString();


                if(email.isEmpty()){
                    email_layout.setError("Email field can not be empty.");
                }else if(password.isEmpty()){
                    password_layout.setError("Password field can not be empty.");
                }else{
                    Toast.makeText(getContext(), "Valid sign up", Toast.LENGTH_SHORT).show();
                    email_layout.setError(null);
                    password_layout.setError(null);
                    // Navigate to next screen
                }
            }
        });

        already_member_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Navigate to login", Toast.LENGTH_SHORT).show();
                // Naviate to login
            }
        });

        return view;
    }


}
