package com.ar.pablo.data.entity.mapper;

import com.ar.pablo.data.entity.model.CarEntity;
import com.ar.pablo.data.transfomer.Transformer;
import com.ar.pablo.domain.model.Car;
import com.google.common.collect.FluentIterable;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class CarMapper implements Transformer<Car, CarEntity> {

    @Inject
    public CarMapper() {
        //empty constructor
    }

    @Override
    public List<Car> transform(Collection<CarEntity> collection) {
        return FluentIterable.from(collection).transform(this::transform).toList();
    }

    @Override
    public Car transform(CarEntity entity) {
        Car car = new Car();
        car.setCarId(entity.getCarId());
        car.setAddress(entity.getAddress());
        car.setCity(entity.getCity());
        car.setDistance(entity.getDistance());
        car.setFuelLevel(entity.getFuelLevel());
        car.setIsClean(entity.getIsClean());
        car.setIsDamaged(entity.getIsDamaged());
        car.setLat(entity.getLat());
        car.setLon(entity.getLon());
        car.setLicencePlate(entity.getLicencePlate());
        car.setLocationId(entity.getLocationId());
        car.setPricingParking(entity.getPricingParking());
        car.setPricingTime(entity.getPricingTime());
        car.setReservationState(entity.getReservationState());
        car.setTitle(entity.getTitle());
        car.setVehicleStateId(entity.getVehicleStateId());
        car.setVehicleTypeId(entity.getVehicleTypeId());
        car.setZipCode(entity.getZipCode());
        return car;
    }
}
