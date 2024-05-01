package com.example.firestorepracticejobinterview.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firestorepracticejobinterview.R;
import com.example.firestorepracticejobinterview.SQLiteLocalDatabase.DBhelper;
import com.example.firestorepracticejobinterview.databinding.ActivityUserNotesBinding;
import com.example.firestorepracticejobinterview.firebaseCRUD.Create_User_Details;
import com.example.firestorepracticejobinterview.model.Note_ModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_NotesActivity extends AppCompatActivity {
    private ActivityUserNotesBinding binding;
    String url1, url2, url3, url4;
    private DBhelper dBhelper;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserNotesBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dBhelper = new DBhelper(this);
        binding.addnotesButtonMoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userNotes(binding.titleTextAddMore.getText().toString().trim(),
                        binding.descriptiontextAddMore.getText().toString().trim(),
                        binding.pricetextAddMore.getText().toString().trim(),
                        binding.quantitytextAddMore.getText().toString().trim(),
                        binding.moreAddMore.getText().toString().trim(),
                        url1,
                        url2,
                        url3,
                        url4
                );

                String a = "v";
                Toast.makeText(getApplicationContext(), ""+a, Toast.LENGTH_SHORT).show();
                dBhelper.addUserDetailsToSQLiteDdatabase(binding.titleTextAddMore.getText().toString().trim(),
                        binding.descriptiontextAddMore.getText().toString().trim(),
                        binding.pricetextAddMore.getText().toString().trim(),
                        binding.quantitytextAddMore.getText().toString().trim(),
                        binding.moreAddMore.getText().toString().trim());
            }
        });


    }


    private void userNotes(String title, String description, String price, String quantity, String more,
                           String url1, String url2, String url3, String url4) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firestore.collection("User").document(user.getEmail());
        Note_ModelClass noteModelClass = new Note_ModelClass(title, description, price, quantity, more, url1, url2, url3, url4);

        reference.collection("Notes").document().set(noteModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(User_NotesActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_NotesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}