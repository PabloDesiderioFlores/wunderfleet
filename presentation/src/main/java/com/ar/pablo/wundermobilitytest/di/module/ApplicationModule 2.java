package com.ar.pablo.wundermobilitytest.di.module;

import com.ar.pablo.data.datasource.CarDataStore;
import com.ar.pablo.data.datasource.cloud.CarCloudDataStore;
import com.ar.pablo.data.executor.JobExecutor;
import com.ar.pablo.data.net.CarRestApi;
import com.ar.pablo.data.net.CarRestApiImpl;
import com.ar.pablo.data.net.retrofit.CarAPI;
import com.ar.pablo.data.net.retrofit.CarReservationAPI;
import com.ar.pablo.data.repository.CarDataRepository;
import com.ar.pablo.domain.executor.PostExecutionThread;
import com.ar.pablo.domain.executor.ThreadExecutor;
import com.ar.pablo.domain.repository.CarRepository;
import com.ar.pablo.wundermobilitytest.BuildConfig;
import com.ar.pablo.wundermobilitytest.ui.UIThread;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {AndroidInjectionModule.class})
public class ApplicationModule {

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    ThreadExecutor providesThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providesPostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    //Region rest api
    @Provides
    @Singleton
    CarAPI providesCarAPI(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(CarAPI.class);
    }

    @Provides
    @Singleton
    CarReservationAPI providesCarReservationApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL_POST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(CarReservationAPI.class);
    }

    @Provides
    @Singleton
    CarRestApi providesCarRestApi(CarRestApiImpl carRestApiImpl) {
        return carRestApiImpl;
    }

    //Region Repository
    @Provides
    @Singleton
    CarRepository providesCarRepository(CarDataRepository carDataRepository) {
        return carDataRepository;
    }

    //Region data store
    @Provides
    @Singleton
    CarDataStore providesCarDataStore(CarRestApi carRestApi) {
        return new CarCloudDataStore(carRestApi);
    }
}
