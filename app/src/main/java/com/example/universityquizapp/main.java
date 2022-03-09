package com.example.universityquizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class main extends AppCompatActivity {
    private static final int req_code_quiz = 1;

    public static final String shared_pref = "sharedPref";
    public static final String key_highscore = "keyHighScore";

    private TextView text_highscore;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign exit button
        Button exit_btn = (Button) findViewById(R.id.exit_btn);
        // Assign start quiz button
        Button startQuizButton = (Button) findViewById(R.id.play_game_btn);
        // assign high score button
        text_highscore = findViewById(R.id.highScoreTxt);
        loadHighScore();

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(main.this, quiz_game.class);
        startActivityForResult(intent, req_code_quiz);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == req_code_quiz) {
            if(resultCode == RESULT_OK) {
                int score = data.getIntExtra(quiz_game.ext_score, 0);
                if (score > highscore) {
                    updateScore(score);
                }
            }
        }
    }

    private void loadHighScore() {
        SharedPreferences pref = getSharedPreferences(shared_pref, MODE_PRIVATE);
        highscore = pref.getInt(key_highscore, 0);
        text_highscore.setText("Highscore: " + highscore);
    }

    private void updateScore(int newHighScore) {
        highscore = newHighScore;
        text_highscore.setText("Highscore: " + highscore);

        SharedPreferences pref = getSharedPreferences(shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key_highscore, highscore);
        editor.apply();
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
