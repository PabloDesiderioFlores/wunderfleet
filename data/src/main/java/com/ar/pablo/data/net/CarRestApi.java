package com.ar.pablo.data.net;

import com.ar.pablo.data.entity.model.CarEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CarRestApi {
    Observable<List<CarEntity>> getAllCars();

    Observable<CarEntity> getCarById(String carId);
}
