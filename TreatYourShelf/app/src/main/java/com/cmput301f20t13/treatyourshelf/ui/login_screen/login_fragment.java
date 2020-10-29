package com.cmput301f20t13.treatyourshelf.ui.login_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class login_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button login_btn = (Button) view.findViewById(R.id.login_btn);
        TextInputLayout email_layout = (TextInputLayout) view.findViewById(R.id.email_layout);
        TextInputLayout password_layout = (TextInputLayout) view.findViewById(R.id.password_layout);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fields are not empty
                String email, password;
                email = email_layout.getEditText().getText().toString();
                password = password_layout.getEditText().getText().toString();

                if(checkValid(email, password)){
                    // Navigate elswhere
                    Toast.makeText(getContext(), "Valid Login", Toast.LENGTH_SHORT).show();
                    email_layout.setError(null);
                    password_layout.setError(null);
                }else{
                    email_layout.setError("Email and Password does not match preexisting user.");
                    password_layout.setError("Email and Password does not match preexisting user.");
                }

            }
        });

        return view;
    }

    public boolean checkValid(String email, String password){
        //Call external check to see if credientials are valid
        return false; // Default
    }

}
