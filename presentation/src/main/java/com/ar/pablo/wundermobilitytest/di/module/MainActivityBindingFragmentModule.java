package com.ar.pablo.wundermobilitytest.di.module;

import com.ar.pablo.wundermobilitytest.di.qualifier.PerFragment;
import com.ar.pablo.wundermobilitytest.ui.fragment.CarMapFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainActivityBindingFragmentModule {
    @ContributesAndroidInjector(modules = {CarMapFragmentModule.class})
    @PerFragment
    abstract CarMapFragment carMapFragment();
}
