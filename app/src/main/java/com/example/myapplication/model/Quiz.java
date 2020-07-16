package com.example.myapplication.model;

public class Quiz {
    private Question[] mQuestions;
    private int mTimeOut;

    public Quiz(Question[] mQuestions, int mTimeOut) {
        this.mQuestions = mQuestions;
        this.mTimeOut = mTimeOut;
    }

    public Question[] getmQuestions() {
        return mQuestions;
    }

    public void setmQuestions(Question[] mQuestions) {
        this.mQuestions = mQuestions;
    }

    public int getmTimeOut() {
        return mTimeOut;
    }

    public void setmTimeOut(int mTimeOut) {
        this.mTimeOut = mTimeOut;
    }
}
