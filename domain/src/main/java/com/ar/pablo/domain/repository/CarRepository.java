package com.ar.pablo.domain.repository;

import com.ar.pablo.domain.model.Car;
import com.ar.pablo.domain.model.CarReservation;

import java.util.List;

import io.reactivex.Observable;

public interface CarRepository {
    Observable<List<Car>> getAllCars();

    Observable<Car> getCarById(String carId);

    Observable<CarReservation> setCarReservation(String carId);
}
