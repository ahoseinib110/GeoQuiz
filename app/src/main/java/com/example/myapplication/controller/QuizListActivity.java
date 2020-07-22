package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.myapplication.R;

public class QuizListActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new QuizListFragment();
    }
}