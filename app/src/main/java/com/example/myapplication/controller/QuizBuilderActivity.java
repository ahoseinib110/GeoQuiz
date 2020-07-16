package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class QuizBuilderActivity extends AppCompatActivity {
    public static final String QUIZ_ATTRIBUTES = "com.example.myapplication.attributes";

    private TextView mEditTextQuizAttributes;
    private Button mButtonBuild;
    private Button mButtonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder);
        findAllViews();
        setClickListeners();
    }

    private void findAllViews() {
        mEditTextQuizAttributes = findViewById(R.id.meditTextQuizAttributes);
        mButtonBuild = findViewById(R.id.mbuttonBuild);
        mButtonDefault = findViewById(R.id.mButtonDefault);
    }

    private void setClickListeners() {
        mButtonBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizBuilderActivity.this, QuizActivity.class);
                String extra = String.valueOf(mEditTextQuizAttributes.getText());
                //Toast.makeText(QuizBuilderActivity.this, extra ,Toast.LENGTH_SHORT).show();
                intent.putExtra(QUIZ_ATTRIBUTES, extra);
                startActivity(intent);
            }
        });

        mButtonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String attributes = "{\n" +
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                        "} , \n" +
                        "{30}\n";
                mEditTextQuizAttributes.setText(attributes);
            }
        });
    }
}