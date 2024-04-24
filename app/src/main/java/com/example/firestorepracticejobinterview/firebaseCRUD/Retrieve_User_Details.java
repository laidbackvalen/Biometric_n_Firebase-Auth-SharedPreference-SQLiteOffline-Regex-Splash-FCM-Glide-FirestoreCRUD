package com.example.firestorepracticejobinterview.firebaseCRUD;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firestorepracticejobinterview.R;
import com.example.firestorepracticejobinterview.databinding.ActivityCreateUserDetailsBinding;
import com.example.firestorepracticejobinterview.databinding.ActivityRetrieveUserDetailsBinding;
import com.example.firestorepracticejobinterview.utils.firebase.FirebaseUtil;

public class Retrieve_User_Details extends AppCompatActivity {
    private ActivityRetrieveUserDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetrieveUserDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      FirebaseUtil.getUserInformation(binding.usernameRetrieved,binding.userEmailRetrieved,binding.userPhoneRetrieved,null,this);

    }
}