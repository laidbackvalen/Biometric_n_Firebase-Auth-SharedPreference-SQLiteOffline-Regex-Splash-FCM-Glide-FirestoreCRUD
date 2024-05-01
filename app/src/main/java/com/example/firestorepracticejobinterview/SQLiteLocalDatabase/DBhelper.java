package com.example.firestorepracticejobinterview.SQLiteLocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserDetails.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "userdetails";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String EMAIL_COLUMN = "email";
    public static final String PHONE_COLUMN = "phone";
    public static final String TIME_COLUMN = "time";
    public static final String URL_COLUMN = "url"; // Changed from IMAGE_COLUMN to URL_COLUMN
    Context context;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COLUMN + " TEXT,"
                + EMAIL_COLUMN + " TEXT,"
                + PHONE_COLUMN + " TEXT,"
                + TIME_COLUMN + " TEXT,"
                + URL_COLUMN + " TEXT)"; // Added a space before TEXT here

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUserDetailsToSQLiteDdatabase(String name, String email, String phone, String url, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, name);
        values.put(EMAIL_COLUMN, email);
        values.put(PHONE_COLUMN, phone);
        values.put(URL_COLUMN, url); // Changed from IMAGE_COLUMN to URL_COLUMN
        values.put(TIME_COLUMN, time);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show(); // Corrected the toast message
        }
        db.close();
    }

}
