package com.example.firestorepracticejobinterview.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.firestorepracticejobinterview.constants.Constants;

import java.util.HashMap;

public class UserSharedPreferences {
    Context context;
    SharedPreferences preferences; //storing small size values in key value pairs
    SharedPreferences.Editor editor;


    public UserSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(Constants.USER_FILE_NAME, context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setUserInfoPreferences(String email, String password) {
        editor.putString(Constants.EMAIL, email);
        editor.putString(Constants.PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getUserInfoPreferences() {
        HashMap<String, String> info = new HashMap<>();
        info.put(Constants.EMAIL, preferences.getString(Constants.EMAIL, null));
        info.put(Constants.PASSWORD, preferences.getString(Constants.PASSWORD, null));
        return info;
    }

}
