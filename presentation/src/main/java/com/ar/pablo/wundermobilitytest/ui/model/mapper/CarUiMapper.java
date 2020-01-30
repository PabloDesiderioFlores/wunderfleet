package com.ar.pablo.wundermobilitytest.ui.model.mapper;

import com.ar.pablo.data.transfomer.Transformer;
import com.ar.pablo.domain.model.Car;
import com.ar.pablo.wundermobilitytest.ui.model.CarUi;
import com.google.common.collect.FluentIterable;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class CarUiMapper implements Transformer<CarUi, Car> {

    @Inject
    public CarUiMapper() {
        //Empty constructor
    }

    @Override
    public List<CarUi> transform(Collection<Car> collection) {
        return FluentIterable.from(collection).transform(this::transform).toList();
    }

    @Override
    public CarUi transform(Car car) {
        CarUi carUi = new CarUi();
        carUi.setCarId(car.getCarId());
        carUi.setLat(car.getLat());
        carUi.setLon(car.getLon());
        carUi.setTitle(car.getTitle());
        return carUi;
    }
}
