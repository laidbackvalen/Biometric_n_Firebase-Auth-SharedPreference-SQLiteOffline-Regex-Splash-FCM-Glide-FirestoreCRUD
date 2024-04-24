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
import com.example.firestorepracticejobinterview.databinding.ActivityUserLoginBinding;
import com.example.firestorepracticejobinterview.utils.firebase.FirebaseUtil;

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
                FirebaseUtil.userLogin(binding.txtInpEdTxtEmail.getText().toString().trim(), binding.txtInpEdTxtPassword.getText().toString().trim(), User_Login.this);
            }
        });
        binding.registrationUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_Login.this, User_Registration.class));
            }
        });
    }
}