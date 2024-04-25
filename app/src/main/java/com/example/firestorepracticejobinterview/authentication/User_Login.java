package com.example.firestorepracticejobinterview.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firestorepracticejobinterview.R;
import com.example.firestorepracticejobinterview.constants.Constants;
import com.example.firestorepracticejobinterview.databinding.ActivityUserLoginBinding;
import com.example.firestorepracticejobinterview.preferences.UserSharedPreferences;
import com.example.firestorepracticejobinterview.utils.firebase.FirebaseUtil;
import com.example.firestorepracticejobinterview.utils.JAVAFunctions.Java_Regex;

import java.util.HashMap;

public class User_Login extends AppCompatActivity {
    private ActivityUserLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.txtInpEdTxtEmail.getText().toString().trim();
                String password = binding.txtInpEdTxtPassword.getText().toString().trim();
                if (!Java_Regex.emailValidator(email)) {
                    binding.txtInpEdTxtEmail.setError("Email Doesn't Match Requirement");
                    binding.txtInpEdTxtEmail.setError("Wrongly Added");
                } else if (Java_Regex.emailValidator(email)) {
                    if (!Java_Regex.passwordValidator(password)) {
                        binding.txtInpEdTxtPassword.setError("Password must:\n" +
                                "- Be at least 8 characters long\n" +
                                "- Contain at least one uppercase letter\n" +
                                "- Contain at least one lowercase letter\n" +
                                "- Contain at least one digit\n" +
                                "- Contain at least one special character (@$!%*?&)");
                    } else {
                        FirebaseUtil.userLogin(email, password, User_Login.this);
                    }
                }

            }
        });
        binding.registrationUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_Login.this, User_Registration.class));
            }
        });

        UserSharedPreferences preferences = new UserSharedPreferences(getApplicationContext());
        HashMap<String, String> info = preferences.getUserInfoPreferences();
        binding.txtInpEdTxtEmail.setText(info.get(Constants.EMAIL));
        binding.txtInpEdTxtPassword.setText(info.get(Constants.PASSWORD));
    }
}