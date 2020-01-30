package com.ar.pablo.data.datasource.cloud;

import com.ar.pablo.data.datasource.CarDataStore;
import com.ar.pablo.data.entity.model.CarEntity;
import com.ar.pablo.data.net.CarRestApi;

import java.util.List;

import io.reactivex.Observable;

public class CarCloudDataStore implements CarDataStore {

    private final CarRestApi carRestApi;

    public CarCloudDataStore(CarRestApi carRestApi) {
        this.carRestApi = carRestApi;
    }

    @Override
    public Observable<List<CarEntity>> getAllCars() {
        return carRestApi.getAllCars();
    }

    @Override
    public Observable<CarEntity> getCarById(String carId) {
        return carRestApi.getCarById(carId);
    }
}
