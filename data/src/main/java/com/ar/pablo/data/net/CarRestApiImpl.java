package com.ar.pablo.data.net;

import com.ar.pablo.data.dto.CarReservationDto;
import com.ar.pablo.data.entity.model.CarEntity;
import com.ar.pablo.data.entity.model.CarReservationEntity;
import com.ar.pablo.data.net.retrofit.CarAPI;
import com.ar.pablo.data.net.retrofit.CarReservationAPI;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CarRestApiImpl implements CarRestApi {

    private final CarAPI carAPI;
    private final CarReservationAPI carReservationAPI;

    @Inject
    public CarRestApiImpl(CarAPI carApi,
                          CarReservationAPI carReservationAPI) {
        this.carAPI = carApi;
        this.carReservationAPI = carReservationAPI;
    }

    @Override
    public Observable<List<CarEntity>> getAllCars() {
        return carAPI.getAllCars();
    }

    @Override
    public Observable<CarEntity> getCarById(String carId) {
        return carAPI.getCarById(carId);
    }

    @Override
    public Observable<CarReservationEntity> setCarReservation(String carId) {
        CarReservationDto dto = new CarReservationDto();
        dto.setCarId(carId);
        return carReservationAPI.setCarReservation("Bearer df7c313b47b7ef87c64c0f5f5cebd6086bbb0fa", dto);
    }
}
