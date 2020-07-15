package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class QuizBuilderActivity extends AppCompatActivity {
    public static final String QUIZ_ATTRIBUTES = "com.example.myapplication.attributes";

    private TextView meditTextQuizAttributes;
private Button mbuttonBuild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder);
        findAllViews();
        setClickListeners();
    }

    private void findAllViews() {
        meditTextQuizAttributes = findViewById(R.id.meditTextQuizAttributes);
        mbuttonBuild = findViewById(R.id.mbuttonBuild);
    }

    private void setClickListeners() {
        mbuttonBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizBuilderActivity.this,QuizActivity.class);
                String extra = String.valueOf( meditTextQuizAttributes.getText());
                //Toast.makeText(QuizBuilderActivity.this, extra ,Toast.LENGTH_SHORT).show();
                intent.putExtra(QUIZ_ATTRIBUTES,extra);
                startActivity(intent);
            }
        });
    }
}