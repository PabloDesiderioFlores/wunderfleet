package com.ar.pablo.wundermobilitytest.di.component;

import android.app.Application;

import com.ar.pablo.wundermobilitytest.AndroidApplication;
import com.ar.pablo.wundermobilitytest.di.module.ActivityBindingModule;
import com.ar.pablo.wundermobilitytest.di.module.ApplicationModule;
import com.ar.pablo.wundermobilitytest.di.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class,
        ActivityBindingModule.class, ContextModule.class})
public interface ApplicationComponent extends AndroidInjector<AndroidApplication> {
    @dagger.Component.Builder
    abstract class Builder extends AndroidInjector.Builder<AndroidApplication> {
        @BindsInstance
        public abstract Builder application(Application application);
    }
}
