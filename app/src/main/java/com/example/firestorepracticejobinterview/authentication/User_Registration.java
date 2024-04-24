package com.example.firestorepracticejobinterview.authentication;

import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.firestorepracticejobinterview.R;
import com.example.firestorepracticejobinterview.databinding.ActivityUserRegistrationBinding;
import com.example.firestorepracticejobinterview.utils.firebase.FirebaseUtil;

public class User_Registration extends AppCompatActivity {
    private ActivityUserRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegistrationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.registerUser(binding.txtInpEdTxtEmailRegistration.getText().toString().trim(),
                        binding.txtInpEdTxtPasswordRegistration.getText().toString().trim(), User_Registration.this);
                finish();
            }
        });
    }
}