package com.example.universityquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.universityquizapp.QuizGameContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuestionsDBHelper extends SQLiteOpenHelper {

    private static final String db_name = "QuizApp.db";
    private static final int db_version = 1;
    private SQLiteDatabase db;

    public QuestionsDBHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String sql_create_questions_tb = "CREATE TABLE " + QuestionTable.tb_name + " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                QuestionTable.col_question + " TEXT, " + QuestionTable.col_op1 + " TEXT, " + QuestionTable.col_op2 + " TEXT, " +
                                                QuestionTable.col_op3 + " TEXT, " + QuestionTable.col_ans_num + " INTEGER" + ")";
        db.execSQL(sql_create_questions_tb);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.tb_name);
        onCreate(db);
    }

    private void fillQuestionTable() {
        Questions q1 = new Questions("When was the Engineering & Computing Building built", "2010", "2012", "2013", 2);
        addQuestion(q1);
        Questions q2 = new Questions("The Alan Terry building has been named after which star...", "Dame Ellen", "Alan Terry Jr", "Elliot Choy", 1);
        addQuestion(q2);
        Questions q3 = new Questions("In which year was the George Eliot building built?", "1970", "1990", "1960", 3);
        addQuestion(q3);
        Questions q4 = new Questions("In which year was the Graham Sutherland built?", "1959", "1956", "1963", 1);
        addQuestion(q4);
        Questions q5 = new Questions("In which year was the library completed?", "2001", "1999", "2005", 1);
        addQuestion(q5);
    }

    private void addQuestion(Questions question) {
        ContentValues content = new ContentValues();
        content.put(QuestionTable.col_question, question.getQuestion());
        content.put(QuestionTable.col_op1, question.getOp1());
        content.put(QuestionTable.col_op2, question.getOp2());
        content.put(QuestionTable.col_op3, question.getOp3());
        content.put(QuestionTable.col_ans_num, question.getAnsNum());
        db.insert(QuestionTable.tb_name, null, content);
    }

    public ArrayList<Questions> getAllQuestions() {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionTable.tb_name, null);

        if(cursor.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.col_question)));
                question.setOp1(cursor.getString(cursor.getColumnIndex(QuestionTable.col_op1)));
                question.setOp2(cursor.getString(cursor.getColumnIndex(QuestionTable.col_op2)));
                question.setOp3(cursor.getString(cursor.getColumnIndex(QuestionTable.col_op3)));
                question.setAnsNum(cursor.getInt(cursor.getColumnIndex(QuestionTable.col_ans_num)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}
