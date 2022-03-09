package com.example.universityquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table User(username text primary key, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists User");
    }
    //inserting data into database
    public boolean insert(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password",password);
        long insert = db.insert("User", null, contentValues);

        if(insert==-1) return false;
        else return true;
    }
    // Check if the username exists
    public Boolean checkUser(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{user});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    // Check username and password for login
    public Boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where username=? and password=?", new String[]{username, password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
}
