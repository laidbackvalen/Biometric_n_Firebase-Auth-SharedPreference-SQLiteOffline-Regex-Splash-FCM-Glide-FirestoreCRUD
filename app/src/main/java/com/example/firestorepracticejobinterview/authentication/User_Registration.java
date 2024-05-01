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
import com.example.firestorepracticejobinterview.utils.JAVAFunctions.Java_Regex;
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

                String email = binding.txtInpEdTxtEmailRegistration.getText().toString().trim();
                String password = binding.txtInpEdTxtPasswordRegistration.getText().toString().trim();
                if (!Java_Regex.emailValidator(email)) {
                    binding.txtInpEdTxtEmailRegistration.setError("Email Doesn't Match Requirement");
                    binding.txtInpEdTxtEmailRegistration.setError("Wrongly Added");
                } else if (Java_Regex.emailValidator(email)) {
                    if (!Java_Regex.passwordValidator(password)) {
                        binding.txtInpEdTxtPasswordRegistration.setError("Password must:\n" +
                                "- Be at least 8 characters long\n" +
                                "- Contain at least one uppercase letter\n" +
                                "- Contain at least one lowercase letter\n" +
                                "- Contain at least one digit\n" +
                                "- Contain at least one special character (@$!%*?&)");
                    } else {
                        FirebaseUtil.registerUser(email, password, User_Registration.this);
                        finish();
                    }
                }
            }


        });
    }
}