package com.ar.pablo.data.net.retrofit;

import com.ar.pablo.data.entity.model.CarEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarAPI {
    @GET("cars.json")
    Observable<List<CarEntity>> getAllCars();

    @GET("cars/{carId}")
    Observable<CarEntity> getCarById(@Path("carId") String carId);
}
