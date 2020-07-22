package com.example.myapplication.model;

import java.util.List;

public class Quiz {
    private List<Question> mQuestions;
    private int mTimeOut;

    public Quiz(List<Question> mQuestions, int mTimeOut) {
        this.mQuestions = mQuestions;
        this.mTimeOut = mTimeOut;
    }

    public List<Question> getmQuestions() {
        return mQuestions;
    }

    public void setmQuestions(List<Question> mQuestions) {
        this.mQuestions = mQuestions;
    }

    public int getmTimeOut() {
        return mTimeOut;
    }

    public void setmTimeOut(int mTimeOut) {
        this.mTimeOut = mTimeOut;
    }
}
