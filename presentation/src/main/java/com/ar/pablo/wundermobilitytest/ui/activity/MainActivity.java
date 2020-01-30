package com.ar.pablo.wundermobilitytest.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ar.pablo.wundermobilitytest.R;
import com.ar.pablo.wundermobilitytest.ui.fragment.CarMapFragment;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentFragment(CarMapFragment.newInstance());
    }

    public void setContentFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        ft.commit();
    }
}
