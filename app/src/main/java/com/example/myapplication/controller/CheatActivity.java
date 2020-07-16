package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class CheatActivity extends AppCompatActivity {

    private TextView mTextViewAnswer;
    private TextView mTextViewQuestion;
    private Button mButtonShowCheat;
    private boolean mAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Log.d(QuizActivity.LOG,"salam");
        Intent intent = getIntent();
        mAnswer = intent.getBooleanExtra(QuizActivity.KEY_CHEAT_ANSWER,true);
        String question = intent.getStringExtra(QuizActivity.KEY_CHEAT_Question);

        findAllViews();
        setClickListeners();

        mTextViewQuestion.setText(question);
    }


    private void findAllViews() {
        mTextViewAnswer  =findViewById(R.id.text_view_cheat_answer);
        mTextViewQuestion=findViewById(R.id.text_view_cheat_question);
        mButtonShowCheat =findViewById(R.id.button_cheat_show);
    }

    private void setClickListeners() {
        mButtonShowCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextViewAnswer.setText(String.valueOf(mAnswer));
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
            }
        });
    }
}