package com.example.firestorepracticejobinterview.firebaseCRUD;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.firestorepracticejobinterview.R;
import com.example.firestorepracticejobinterview.SQLiteLocalDatabase.DBhelper;
import com.example.firestorepracticejobinterview.activities.MainActivity;
import com.example.firestorepracticejobinterview.databinding.ActivityCreateUserDetailsBinding;
import com.example.firestorepracticejobinterview.model.ModelClass;
import com.example.firestorepracticejobinterview.utils.firebase.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.UploadTask;

import java.util.Map;
import java.util.Objects;

public class Create_User_Details extends AppCompatActivity {
    private ActivityCreateUserDetailsBinding binding;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUserDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });






        //if data exist like name is not null //use addValuEventListener //DataSnapshot
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        FirebaseFirestore.getInstance().collection("User").document(Objects.requireNonNull(firebaseUser.getUid())).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    Map<String, Object> map = value.getData();
                    String name = value.getData().get("name").toString();
                    if (name != null) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
            }
        });
        binding.createUserLayoutIncluded.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch("image/*");
            }
        });

        binding.createUserLayoutIncluded.addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ModelClass
                ModelClass modelClass = new ModelClass(binding.createUserLayoutIncluded.name.getText().toString().trim()
                        , firebaseUser.getUid(), binding.createUserLayoutIncluded.phone.getText().toString(),
                        url, String.valueOf(System.currentTimeMillis()));
                FirebaseUtil.firebaseFirestore(modelClass, Create_User_Details.this);




            }
        });

    }

    ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri_result) {
            FirebaseUtil.firebaseStorage().putFile(uri_result).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                url = task.getResult().toString();
                                binding.createUserLayoutIncluded.userImage.setImageURI(uri_result);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Create_User_Details.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Create_User_Details.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    });


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel Glide requests
        Glide.with(this).clear((ImageView)findViewById(R.id.userImage));
    }
}