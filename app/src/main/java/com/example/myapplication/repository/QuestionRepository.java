package com.example.myapplication.repository;

import com.example.myapplication.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private static QuestionRepository sCrimeRepository;
    //private static final int NUMBER_OF_Questions = 100;

    public static QuestionRepository getInstance() {
        if (sCrimeRepository == null)
            sCrimeRepository = new QuestionRepository();

        return sCrimeRepository;
    }

    private List<Question> mQuestions;

    //Read all
    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> questions) {
        mQuestions = questions;
    }

    //Create
    private QuestionRepository() {
    }
}
