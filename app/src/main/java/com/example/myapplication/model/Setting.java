package com.example.myapplication.model;

import java.io.Serializable;

public class Setting implements Serializable {
private int questionSize;

    public Setting(int questionSize) {
        this.questionSize = questionSize;
    }

    public int getQuestionSize() {
        return questionSize;
    }

    public void setQuestionSize(int questionSize) {
        this.questionSize = questionSize;
    }
}
