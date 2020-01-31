package com.ar.pablo.data.entity.mapper;

import com.ar.pablo.data.entity.model.CarReservationEntity;
import com.ar.pablo.data.transfomer.Transformer;
import com.ar.pablo.domain.model.CarReservation;
import com.google.common.collect.FluentIterable;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class CarReservationMapper implements Transformer<CarReservation, CarReservationEntity> {

    @Inject
    CarReservationMapper() {
        //empty constructor
    }

    @Override
    public List<CarReservation> transform(Collection<CarReservationEntity> collection) {
        return FluentIterable.from(collection).transform(this::transform).toList();
    }

    @Override
    public CarReservation transform(CarReservationEntity entity) {
        CarReservation carReservation = new CarReservation();
        carReservation.setCarId(entity.getCarId());
        carReservation.setCost(entity.getCost());
        carReservation.setDamageDescription(entity.getDamageDescription());
        carReservation.setDrivenDistance(entity.getDrivenDistance());
        carReservation.setEndTime(entity.getEndTime());
        carReservation.setFuelCardPin(entity.getFuelCardPin());
        carReservation.setIsParkModeEnabled(entity.getIsParkModeEnabled());
        carReservation.setLicencePlate(entity.getLicencePlate());
        carReservation.setReservationId(entity.getReservationId());
        carReservation.setStartAddress(entity.getStartAddress());
        carReservation.setStartTime(entity.getStartTime());
        carReservation.setUserId(entity.getUserId());
        return carReservation;
    }
}
