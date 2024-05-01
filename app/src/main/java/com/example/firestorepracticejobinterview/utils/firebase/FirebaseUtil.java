package com.example.firestorepracticejobinterview.utils.firebase;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.firestorepracticejobinterview.R;
import com.example.firestorepracticejobinterview.SQLiteLocalDatabase.DBhelper;
import com.example.firestorepracticejobinterview.activities.MainActivity;
import com.example.firestorepracticejobinterview.firebaseCRUD.Create_User_Details;
import com.example.firestorepracticejobinterview.model.ModelClass;
import com.example.firestorepracticejobinterview.preferences.UserSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;
import java.util.Objects;

public class FirebaseUtil {
    public static final FirebaseAuth auth = FirebaseAuth.getInstance();
    public static FirebaseUser user;

    //Firestore Database
    //DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(user.getEmail());
    public static Task<Void> firebaseFirestore(ModelClass modelClass, Context context) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        return firestore.collection("User").document(user.getUid()).set(modelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Data Added Successfully!", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Login with credentials
    public static void userLogin(String email, String password, Context context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = task.getResult().getUser(); //if the user created is email verified
                    assert user != null;
                    if (user.isEmailVerified()) {
                        UserSharedPreferences preferences = new UserSharedPreferences(context);
                        preferences.setUserInfoPreferences(email, password);//shared preferences
                        context.startActivity(new Intent(context, Create_User_Details.class));
                    } else {
                        Toast.makeText(context, "Email is not verified!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Create User with credentials
    public static void registerUser(String email, String password, Context context) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Please Verify!, Check Email.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(context, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //   StorageReference
    public static StorageReference firebaseStorage() {
        return FirebaseStorage.getInstance().getReference().child("Images").child(Objects.requireNonNull(user.getUid())).child("IMG_" + System.currentTimeMillis());
    }

    public static void getUserInformation(TextView nameTextView, TextView emailTextView, TextView phoneTextView, ImageView userImageView, Context context) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        FirebaseFirestore.getInstance().collection("User").document(Objects.requireNonNull(user.getUid())).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {

                        Map<String, Object> map = value.getData();
                    String name = value.getData().get("name").toString();
                    String email = "";
                    String phone = value.getData().get("phone").toString();
                    String image = value.getData().get("url").toString();
                    String time = value.getData().get("timeStamp").toString();

                    //setting it to required views from firebase
                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    phoneTextView.setText(phone);
                    Glide.with(context).load(image).centerCrop().placeholder(R.drawable.baseline_person_24).into(userImageView);
//                            String s = value.getData().toString();
                }
            }
        });
    }

}