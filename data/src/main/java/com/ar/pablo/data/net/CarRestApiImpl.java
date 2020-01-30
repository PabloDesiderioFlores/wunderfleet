package com.ar.pablo.data.net;

import com.ar.pablo.data.entity.model.CarEntity;
import com.ar.pablo.data.net.retrofit.CarAPI;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CarRestApiImpl implements CarRestApi {

    private final CarAPI carAPI;

    @Inject
    public CarRestApiImpl(CarAPI carApi) {
        this.carAPI = carApi;
    }

    @Override
    public Observable<List<CarEntity>> getAllCars() {
        return carAPI.getAllCars();
    }

    @Override
    public Observable<CarEntity> getCarById(String carId) {
        return carAPI.getCarById(carId);
    }
}
