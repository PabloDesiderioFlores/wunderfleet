package com.ar.pablo.wundermobilitytest.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ar.pablo.wundermobilitytest.R;

import dagger.android.DaggerActivity;

public class MainActivity extends DaggerActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
