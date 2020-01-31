package com.ar.pablo.data.net;

import com.ar.pablo.data.entity.model.CarEntity;
import com.ar.pablo.data.entity.model.CarReservationEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CarRestApi {
    Observable<List<CarEntity>> getAllCars();

    Observable<CarEntity> getCarById(String carId);

    Observable<CarReservationEntity> setCarReservation(String carId);
}
