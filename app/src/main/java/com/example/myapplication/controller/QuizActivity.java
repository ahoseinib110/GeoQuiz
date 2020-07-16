package com.example.myapplication.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Colors;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Quiz;
import com.example.myapplication.model.Setting;
import com.example.myapplication.utils.StringUtils;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    public static final String LOG = "geoLog";
    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonCheat;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrevious;
    private ImageButton mImageButtonFirst;
    private ImageButton mImageButtonLast;
    private ImageButton mImageAgain;
    private ImageButton mImageButtonSetting;
    private TextView mTextViewQuestion;
    private TextView mTextViewScore;
    private TextView mTextViewScore2;
    private TextView mTextViewScoreText;
    private TextView mTextViewScoreText2;
    private TextView mTextViewTimer;
    private LinearLayout mLayoutQuestion;
    private LinearLayout mLayoutScore;
    private LinearLayout mLayoutScore2;
    private int mCurrentIndex = 0;
    private int mAnsweredNumber = 0;
    private int mScore = 0;

    public static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_ANSWERED_NUMBER = "answeredNumber";
    public static final String KEY_SCORE = "score";
    public static final String KEY_ANSWERED_ARRAY = "answeredArray";
    public static final String KEY_SETTING = "setting";
    public static final String KEY_CHEAT_ANSWER = "cheatAnswer";
    public static final String KEY_CHEAT_Question = "cheatQuestion";
    private static final String KEY_TIME_OUT = "timeOut" ;

    public static final int REQUEST_CODE_SETTING = 0;
    public static final int REQUEST_CODE_CHEAT = 1;

    private Question[] mQuestionBank;
    private Quiz mQuiz;
    private Setting mSetting;
    private int mTimeOut;
    private boolean mIsFinished;

    /*= {
    new Question(R.string.question_australia, false),
    new Question(R.string.question_oceans, true),
    new Question(R.string.question_mideast, false),
    new Question(R.string.question_africa, true),
    new Question(R.string.question_americas, false),
    new Question(R.string.question_asia, false)
};
*/
    boolean[] answeredArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String attributes = intent.getStringExtra(QuizBuilderActivity.QUIZ_ATTRIBUTES);
        Log.d(LOG, attributes);
        mQuiz = StringUtils.parseQuestions(attributes);
        mQuestionBank = mQuiz.getmQuestions();
        mTimeOut = mQuiz.getmTimeOut();

        answeredArray = new boolean[mQuestionBank.length];
        //inflate: convert layout xml to actual java objects to be displayed
        setContentView(R.layout.activity_quiz);


        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            mAnsweredNumber = savedInstanceState.getInt(KEY_ANSWERED_NUMBER);
            mScore = savedInstanceState.getInt(KEY_SCORE);
            answeredArray = savedInstanceState.getBooleanArray(KEY_ANSWERED_ARRAY);
            for (int i = 0; i < answeredArray.length; i++) {
                mQuestionBank[i].setmAnswered(answeredArray[i]);
            }
            mIsFinished = savedInstanceState.getBoolean(KEY_TIME_OUT);
        }
        //it must be the first task we do after inflate
        findAllViews();
        setClickListeners();
        String scoreText = "/" + mQuestionBank.length + " : score";
        mTextViewScoreText.setText(scoreText);
        mTextViewScoreText2.setText(scoreText);
        updateQuestion();
        updateScore();
        updateEnable();
        mIsFinished = false;
        updateVisibility();
        startTimer(mTimeOut);
    }

    private void startTimer(int interval) {
        new CountDownTimer(interval * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(!mIsFinished){
                        mTextViewTimer.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            public void onFinish() {
                //mTextField.setText("done!");
                if(!mIsFinished) {
                    mIsFinished = true;
                    updateVisibility();
                    Toast.makeText(QuizActivity.this, "Your time is up", Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }


    /*
        public static void main(String[] args) {
            String attributes = "{\n" +
                    "[{\"Tehran is in iran\"}, {true}, {false}, {green}],\n" +
                    "[{\"Iran language is english\"}, {false} {true}, {red}], \n" +
                    "[{\"England is in usa\"}, {false}, {false}, {black}]\n" +
                    "} , \n" +
                    "{30}\n";
            parseQuestions(attributes);
        }

    {
    [{"Tehran is in iran"}, {true}, {false}, {green}],
    [{"Iran language is english"}, {false} {true}, {red}],
    [{"England is in usa"}, {false}, {false}, {black}]
    } ,
    {30}
    */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mIsFinished = true;
        super.onSaveInstanceState(outState);
        for (int i = 0; i < answeredArray.length; i++) {
            answeredArray[i] = mQuestionBank[i].ismAnswered();
        }
        outState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(KEY_ANSWERED_NUMBER, mAnsweredNumber);
        outState.putInt(KEY_SCORE, mScore);
        outState.putBooleanArray(KEY_ANSWERED_ARRAY, answeredArray);
        outState.putBoolean(KEY_TIME_OUT,mIsFinished);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_SETTING:
                mSetting = (Setting) data.getSerializableExtra(KEY_SETTING);
                updateSetting();
                //Log.d(LOG, String.valueOf(mTextViewQuestion.getTextSize()));
                break;
            case REQUEST_CODE_CHEAT:
                mAnsweredNumber++;
                answeredArray[mCurrentIndex] = true;
                mQuestionBank[mCurrentIndex].setmAnswered(answeredArray[mCurrentIndex]);
                updateEnable();
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
    }

    //always used mandatory
    private void findAllViews() {
        mButtonTrue = findViewById(R.id.button_true);
        mButtonFalse = findViewById(R.id.button_false);
        mButtonCheat = findViewById(R.id.button_cheat);
        mImageButtonNext = findViewById(R.id.button_next);
        mImageButtonPrevious = findViewById(R.id.button_previous);
        mImageButtonFirst = findViewById(R.id.button_first);
        mImageButtonLast = findViewById(R.id.button_last);
        mImageAgain = findViewById(R.id.button_again);
        mImageButtonSetting = findViewById(R.id.button_setting);
        mTextViewQuestion = findViewById(R.id.text_view_question);
        mTextViewScore = findViewById(R.id.text_view_score);
        mTextViewScore2 = findViewById(R.id.text_view_score2);
        mTextViewScoreText = findViewById(R.id.text_view_score_text);
        mTextViewScoreText2 = findViewById(R.id.text_view_score_text2);
        mTextViewTimer = findViewById(R.id.text_view_timer);
        mLayoutQuestion = findViewById(R.id.layout_question);
        mLayoutScore = findViewById(R.id.layout_score);
        mLayoutScore2 = findViewById(R.id.layout_score2);
    }

    //change question in textview using current question in bank
    private void updateQuestion() {
        Question currentQuestion = mQuestionBank[mCurrentIndex];
        mTextViewQuestion.setText(currentQuestion.getmText());
        switch (currentQuestion.getmColor()) {
            case BLACK:
                mTextViewQuestion.setTextColor(Color.BLACK);
                break;
            case RED:
                mTextViewQuestion.setTextColor(Color.RED);
                break;
            case BLUE:
                mTextViewQuestion.setTextColor(Color.BLUE);
                break;
            case GREEN:
                mTextViewQuestion.setTextColor(Color.GREEN);
                break;
            default:
                break;
        }
        if(mAnsweredNumber == mQuestionBank.length){
            mIsFinished = true;
        }
    }

    private void updateEnable() {
        if (mQuestionBank[mCurrentIndex].ismAnswered()) {
            mButtonTrue.setEnabled(false);
            mButtonFalse.setEnabled(false);
        } else {
            mButtonTrue.setEnabled(true);
            mButtonFalse.setEnabled(true);
        }
    }

    private void updateVisibility() {
        if (mIsFinished) {
            mLayoutQuestion.setVisibility(View.GONE);
            mImageButtonLast.setVisibility(View.GONE);
            mImageButtonFirst.setVisibility(View.GONE);
            mImageButtonNext.setVisibility(View.GONE);
            mImageButtonPrevious.setVisibility(View.GONE);
            mLayoutScore.setVisibility(View.GONE);
            mLayoutScore2.setVisibility(View.VISIBLE);
            mImageAgain.setVisibility(View.VISIBLE);
            mTextViewScore2.setText(String.valueOf(mScore));
        } else {
            mLayoutQuestion.setVisibility(View.VISIBLE);
            mImageButtonLast.setVisibility(View.VISIBLE);
            mImageButtonFirst.setVisibility(View.VISIBLE);
            mImageButtonNext.setVisibility(View.VISIBLE);
            mImageButtonPrevious.setVisibility(View.VISIBLE);
            mLayoutScore2.setVisibility(View.GONE);
            mLayoutScore.setVisibility(View.VISIBLE);
            mImageAgain.setVisibility(View.GONE);
        }
    }

    private void goToNext() {
        mCurrentIndex = (++mCurrentIndex) % mQuestionBank.length;
        updateQuestion();
        updateEnable();
    }

    private void goToPrevious() {
        mCurrentIndex = (--mCurrentIndex + mQuestionBank.length) % mQuestionBank.length;
        updateQuestion();
        updateEnable();
    }

    private void updateScore() {
        mTextViewScore.setText(String.valueOf(mScore));
    }

    private void updateSetting() {
        mTextViewQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, mSetting.getQuestionSize());
    }


    private void setClickListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mQuestionBank[mCurrentIndex].setmAnswered(true);
                mAnsweredNumber++;
                updateEnable();
                goToNext();
                updateVisibility();
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setmAnswered(true);
                mAnsweredNumber++;
                updateEnable();
                goToNext();
                updateVisibility();
            }
        });

        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });

        mImageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrevious();
            }
        });

        mImageButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = 0;
                updateQuestion();
                updateEnable();
            }
        });

        mImageButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
                updateEnable();
            }
        });

        mImageAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFinished=false;
                mAnsweredNumber = 0;
                mCurrentIndex = 0;
                mScore = 0;
                for (int i = 0; i < mQuestionBank.length; i++) {
                    mQuestionBank[i].setmAnswered(false);
                }
                mTimeOut = mQuiz.getmTimeOut();
                updateVisibility();
                updateEnable();
                updateQuestion();
                updateScore();
                startTimer(mTimeOut);
            }
        });

        mImageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
                mSetting = new Setting((int) mTextViewQuestion.getTextSize());//
                intent.putExtra(KEY_SETTING, mSetting);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });

        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuestionBank[mCurrentIndex].ismIsCheat()) {
                    Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                    intent.putExtra(KEY_CHEAT_Question, mQuestionBank[mCurrentIndex].getmText());
                    intent.putExtra(KEY_CHEAT_ANSWER, mQuestionBank[mCurrentIndex].ismAnswerTrue());
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                } else {
                    Toast.makeText(QuizActivity.this, R.string.toast_disable_cheating, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //check current question answer and show toast.
    private void checkAnswer(boolean userPressed) {
        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        if (userPressed == isAnswerTrue) {
            mScore++;
            updateScore();
            Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_SHORT).show();

        }
    }
}