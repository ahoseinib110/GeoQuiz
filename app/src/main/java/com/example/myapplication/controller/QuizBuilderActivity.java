package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Quiz;
import com.example.myapplication.repository.QuestionRepository;
import com.example.myapplication.utils.StringUtils;

import java.util.List;

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
                //Intent intent = new Intent(QuizBuilderActivity.this, QuizActivity.class);
                Intent intent = new Intent(QuizBuilderActivity.this, QuizListActivity.class);
                String extra = String.valueOf(mEditTextQuizAttributes.getText());
                //Toast.makeText(QuizBuilderActivity.this, extra ,Toast.LENGTH_SHORT).show();
                Quiz quiz = StringUtils.parseQuestions(extra);
                List<Question> questionBank = quiz.getmQuestions();
                saveToRepository(questionBank);
                //intent.putExtra(QUIZ_ATTRIBUTES, extra);
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
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                        "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                        "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                        "} , \n" +
                        "{30}\n";
                mEditTextQuizAttributes.setText(attributes);
            }
        });
    }

    private void saveToRepository(List<Question> questionBank) {
        QuestionRepository questionRepository = QuestionRepository.getInstance();
        questionRepository.setQuestions(questionBank);
    }

}