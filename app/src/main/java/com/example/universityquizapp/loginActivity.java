package com.example.universityquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText username,password;
    Button loginBtn, google;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new Database(this);
        username = (EditText) findViewById(R.id.usernameLoginTxt);
        password = (EditText) findViewById(R.id.passwordLoginTxt);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        google = (Button) findViewById(R.id.googleSignIn);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Boolean checkLogin = db.login(user,pass);
                if(checkLogin==true)
                    //Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_SHORT).show();
                    mainAct();
                else
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginActivity.this, googleLogin.class);
                startActivity(i);
            }
        });

    }

    /** The main page once logged in **/
    public void mainAct() {
        Intent intent = new Intent(this, main.class);
        startActivity(intent);
    }
}
