package com.example.universityquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class mainScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        Button exit_btn = (Button) findViewById(R.id.exit_btn);



        exit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });
    }

    /** Called when the user clicks the Instruction button */
    public void instructionAct(View view) {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the leaderboard button */
    public void leaderboardAct(View view) {
        Intent intent = new Intent(this, leaderboardActivity.class);
        startActivity(intent);
    }
}