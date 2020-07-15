package com.example.myapplication.model;

public class Question {
    private String mText;
    private boolean mAnswerTrue;
    private boolean mIsCheat;
    private Colors mColor;
    private boolean mAnswered = false;

    public Question(String mText, boolean mAnswerTrue, boolean mIsCheat, Colors mColor) {
        this.mText = mText;
        this.mAnswerTrue = mAnswerTrue;
        this.mIsCheat = mIsCheat;
        this.mColor = mColor;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public boolean ismAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public boolean ismIsCheat() {
        return mIsCheat;
    }

    public void setmIsCheat(boolean mIsCheat) {
        this.mIsCheat = mIsCheat;
    }

    public Colors getmColor() {
        return mColor;
    }

    public void setmColor(Colors mColor) {
        this.mColor = mColor;
    }

    public boolean ismAnswered() {
        return mAnswered;
    }

    public void setmAnswered(boolean mAnswered) {
        this.mAnswered = mAnswered;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mText='" + mText + '\'' +
                ", mAnswerTrue=" + mAnswerTrue +
                ", mIsCheat=" + mIsCheat +
                ", mColor=" + mColor +
                '}';
    }
}
