package com.example.firestorepracticejobinterview.utils.JAVAFunctions;

import com.example.firestorepracticejobinterview.constants.Constants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Java_Regex {
    public static boolean emailValidator(String email) {
        Pattern pattern = Pattern.compile(Constants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean passwordValidator(String password) {
        Pattern pattern = Pattern.compile(Constants.PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
