package com.example.universityquizapp;


import android.os.Parcel;
import android.os.Parcelable;

public class Questions implements Parcelable {
    private String question;
    private String op1;
    private String op2;
    private String op3;
    private int ansNum;

    public Questions() {}

    public Questions(String question, String op1, String op2, String op3, int ansNum) {
        this.question = question;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.ansNum = ansNum;
    }

    protected Questions(Parcel in) {
        question = in.readString();
        op1 = in.readString();
        op2 = in.readString();
        op3 = in.readString();
        ansNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(op1);
        dest.writeString(op2);
        dest.writeString(op3);
        dest.writeInt(ansNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public int getAnsNum() {
        return ansNum;
    }

    public void setAnsNum(int ansNum) {
        this.ansNum = ansNum;
    }
}
