package com.example.universityquizapp;

import android.provider.BaseColumns;

public final class QuizGameContract {

    private QuizGameContract() {}

    public static class QuestionTable implements BaseColumns {
        public static final String tb_name = "quiz_questions";
        public static final String col_question = "question";
        public static final String col_op1 = "option1";
        public static final String col_op2 = "option2";
        public static final String col_op3 = "option3";
        public static final String col_ans_num = "answer_num";
    }
}
