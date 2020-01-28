package com.ar.pablo.wundermobilitytest.di.module;

import com.ar.pablo.wundermobilitytest.di.qualifier.PerActivity;
import com.ar.pablo.wundermobilitytest.ui.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = {MainActivityBindingFragmentModule.class})
    @PerActivity
    abstract MainActivity mainActivity();
}
