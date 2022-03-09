package com.example.universityquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class quiz_game extends AppCompatActivity {

    public static final String ext_score = "Extra_Score";
    private static final long countdown_in_millis = 30000;

    private static final String key_score = "key_score";
    private static final String key_question_count = "key_question_count";
    private static final String key_mili_left = "key_mili_left";
    private static final String key_answered = "key_answered";
    private static final String key_qeuestion_list = "key_question_list";

    private TextView txt_question, view_score, question_count, count_down;
    private RadioGroup optionGroup;
    private RadioButton op1, op2, op3;
    private Button buttonNext;

    private ColorStateList textColourRb;
    private ColorStateList textColourDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;


    private ArrayList<Questions> questionsList;
    private int questionCount;
    private int questionCountTotal;
    private Questions currentQuestion;

    private int score;
    private boolean answerGiven;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        txt_question = findViewById(R.id.text_view_question);
        view_score = findViewById(R.id.text_view_score);
        question_count = findViewById(R.id.text_question_count);
        count_down = findViewById(R.id.text_view_countdown);

        optionGroup = findViewById(R.id.radio_group);
        op1 = findViewById(R.id.radio_button1);
        op2 = findViewById(R.id.radio_button2);
        op3 = findViewById(R.id.radio_button3);
        buttonNext = findViewById(R.id.button_confirm_next);

        textColourRb = op1.getTextColors();
        textColourDefaultCd = count_down.getTextColors();

        if(savedInstanceState == null) {
            QuestionsDBHelper dbHelper = new QuestionsDBHelper(this);
            questionsList = dbHelper.getAllQuestions();
            questionCountTotal = questionsList.size();
            Collections.shuffle(questionsList);
        } else {
            questionsList = savedInstanceState.getParcelableArrayList(key_qeuestion_list);
            questionCountTotal = questionsList.size();
            questionCount = savedInstanceState.getInt(key_question_count);
            currentQuestion = questionsList.get(questionCount - 1);
            score = savedInstanceState.getInt(key_score);
            timeLeftInMillis = savedInstanceState.getLong(key_mili_left);
            answerGiven = savedInstanceState.getBoolean(key_answered);

            if(!answerGiven) {
                startCountDown();
            } else {
                updateCountDownText();
                showCorrectAns();
            }
        }

        showNextQuestion();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answerGiven) {
                    if(!op1.isChecked() || !op2.isChecked() || !op3.isChecked()) {
                        checkAns();
                    } else {
                        Toast.makeText(quiz_game.this, "Please select one of the options", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        op1.setTextColor(textColourRb);
        op2.setTextColor(textColourRb);
        op3.setTextColor(textColourRb);
        optionGroup.clearCheck();

        if(questionCount < questionCountTotal) {
            currentQuestion = questionsList.get(questionCount);

            txt_question.setText(currentQuestion.getQuestion());
            op1.setText(currentQuestion.getOp1());
            op2.setText(currentQuestion.getOp2());
            op3.setText(currentQuestion.getOp3());

            questionCount++;
            question_count.setText("Question: " + questionCount + "/" + questionCountTotal);
            answerGiven = false;
            buttonNext.setText("Confirm");

            timeLeftInMillis = countdown_in_millis;
            startCountDown();
        } else {
            finishGame();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAns();
            }
        }.start();
    }

    private void updateCountDownText() {
        int mins = (int) (timeLeftInMillis / 1000) / 60;
        int secs = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d%02d", mins, secs);

        count_down.setText(timeFormatted);

        if(timeLeftInMillis < 10000) {
            count_down.setTextColor(Color.RED);
        } else {
            count_down.setTextColor(textColourDefaultCd);
        }
    }

    private void checkAns() {
        answerGiven = true;

        countDownTimer.cancel();

        RadioButton selectedRb = findViewById(optionGroup.getCheckedRadioButtonId());
        int ansNum = optionGroup.indexOfChild(selectedRb) + 1;

        if(ansNum == currentQuestion.getAnsNum()) {
            score++;
            view_score.setText("Score: " + score);
        }

        showCorrectAns();
    }

    private void showCorrectAns() {
        op1.setTextColor(Color.RED);
        op2.setTextColor(Color.RED);
        op3.setTextColor(Color.RED);

        switch (currentQuestion.getAnsNum()) {
            case 1:
                op1.setTextColor(Color.GREEN);
                txt_question.setText("Answer 1 is correct");
                break;
            case 2:
                op2.setTextColor(Color.GREEN);
                txt_question.setText("Answer 2 is correct");
                break;
            case 3:
                op3.setTextColor(Color.GREEN);
                txt_question.setText("Answer 3 is correct");
                break;
        }

        if(questionCount < questionCountTotal) {
            buttonNext.setText("Next");
        } else {
            buttonNext.setText("Finish");
        }
    }

    private void finishGame() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ext_score, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            finishGame();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(key_score, score);
        outState.putInt(key_question_count, questionCount);
        outState.putLong(key_mili_left, timeLeftInMillis);
        outState.putBoolean(key_answered, answerGiven);
        outState.putParcelableArrayList(key_qeuestion_list, questionsList);


    }
}
