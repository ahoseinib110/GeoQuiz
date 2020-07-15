package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.model.Setting;


public class SettingActivity extends AppCompatActivity {
    private Button mButtonSave;
    private Button mButtonDiscard;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonLarge;
    private RadioButton mRadioButtonNormal;
    private RadioButton mRadioButtonSmall;

    private int mTextSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        Setting setting = (Setting) intent.getSerializableExtra(QuizActivity.KEY_SETTING);
        mTextSize = setting.getQuestionSize();
        //Log.d("loooog", String.valueOf(setting.getQuestionSize()));

        findAllViews();
        setClickListeners();

        restoreSetting();
    }

    private void restoreSetting() {
        switch (mTextSize){
            case 71 :
                mRadioButtonLarge.toggle(); break;
            case 49:
                mRadioButtonNormal.toggle(); break;
            case 38:
                mRadioButtonSmall.toggle(); break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() { }

    private void findAllViews() {
        mButtonSave = findViewById(R.id.button_save);
        mButtonDiscard = findViewById(R.id.button_discard);
        mRadioButtonLarge = findViewById(R.id.radio_button_large);
        mRadioButtonNormal = findViewById(R.id.radio_button_normal);
        mRadioButtonSmall = findViewById(R.id.radio_button_small);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
    }

    private void setClickListeners() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textSize = 0;
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                // Check which radio button was clicked
                switch (selectedId) {
                    case R.id.radio_button_large:
                        textSize = 26;
                        break;
                    case R.id.radio_button_normal:
                        textSize = 18;
                        break;
                    case R.id.radio_button_small:
                        textSize = 14;
                        break;
                }
                Intent intent = new Intent();
                Setting setting = new Setting(textSize);
                intent.putExtra(QuizActivity.KEY_SETTING, setting);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}