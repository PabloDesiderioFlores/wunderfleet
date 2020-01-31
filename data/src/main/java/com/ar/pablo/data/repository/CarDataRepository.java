package com.ar.pablo.data.repository;

import com.ar.pablo.data.datasource.CarDataStore;
import com.ar.pablo.data.entity.mapper.CarMapper;
import com.ar.pablo.data.entity.mapper.CarReservationMapper;
import com.ar.pablo.domain.model.Car;
import com.ar.pablo.domain.model.CarReservation;
import com.ar.pablo.domain.repository.CarRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CarDataRepository implements CarRepository {

    private final CarDataStore carDataStore;
    private final CarMapper carMapper;
    private final CarReservationMapper carReservationMapper;

    @Inject
    public CarDataRepository(CarDataStore carDataStore,
                             CarMapper carMapper,
                             CarReservationMapper carReservationMapper) {
        this.carDataStore = carDataStore;
        this.carMapper = carMapper;
        this.carReservationMapper = carReservationMapper;
    }

    @Override
    public Observable<List<Car>> getAllCars() {
        return carDataStore.getAllCars()
                .map(carMapper::transform);
    }

    @Override
    public Observable<Car> getCarById(String carId) {
        return carDataStore.getCarById(carId)
                .map(carMapper::transform);
    }

    @Override
    public Observable<CarReservation> setCarReservation(String carId) {
        return carDataStore.setCarReservation(carId)
                .map(carReservationMapper::transform);
    }
}
