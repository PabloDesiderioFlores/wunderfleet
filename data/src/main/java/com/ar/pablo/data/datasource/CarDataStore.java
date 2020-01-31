package com.ar.pablo.data.datasource;

import com.ar.pablo.data.entity.model.CarEntity;
import com.ar.pablo.data.entity.model.CarReservationEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CarDataStore {
    Observable<List<CarEntity>> getAllCars();

    Observable<CarEntity> getCarById(String carId);

    Observable<CarReservationEntity> setCarReservation(String carId);
}
