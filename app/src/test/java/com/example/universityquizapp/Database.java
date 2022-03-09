package com.example.universityquizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Test;

import static org.junit.Assert.*;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Test
    public void insert() {
        String username = "zaky";
        String password = "password";
        Boolean expected = true;

        Database db = new Database();
        db.insert(username, password);
    }

    @Test
    public void checkUser() {
    }

    @Test
    public void login() {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table User(username text primary key, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists User");
    }
}