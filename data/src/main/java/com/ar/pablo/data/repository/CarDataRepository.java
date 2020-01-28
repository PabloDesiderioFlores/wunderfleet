package com.ar.pablo.data.repository;

import com.ar.pablo.data.datasource.CarDataStore;
import com.ar.pablo.data.entity.mapper.CarMapper;
import com.ar.pablo.domain.model.Car;
import com.ar.pablo.domain.repository.CarRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CarDataRepository implements CarRepository {

    private final CarDataStore carDataStore;
    private final CarMapper carMapper;

    @Inject
    public CarDataRepository(CarDataStore carDataStore,
                             CarMapper carMapper) {
        this.carDataStore = carDataStore;
        this.carMapper = carMapper;
    }

    @Override
    public Observable<List<Car>> getAllCars() {
        return carDataStore.getAllCars()
                .map(carMapper::transform);
    }
}
