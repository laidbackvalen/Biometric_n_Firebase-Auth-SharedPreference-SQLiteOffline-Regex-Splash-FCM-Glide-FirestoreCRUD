package com.example.firestorepracticejobinterview.constants;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Constants {
    public static final FirebaseAuth auth = FirebaseAuth.getInstance();
    public static final String USER_FILE_NAME = "UserCredentials";
    public static final int MODE_PRIVATE = 0;
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public final static String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
}
