package com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.domain.interactor.GetAllCarsUseCase;
import com.ar.pablo.wundermobilitytest.ui.model.mapper.CarUiMapper;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarMapViewModel;

import javax.inject.Inject;

public class CarMapViewModelFactory implements ViewModelProvider.Factory {

    private final GetAllCarsUseCase getAllCarsUseCase;
    private final CarUiMapper carUiMapper;

    @Inject
    public CarMapViewModelFactory(GetAllCarsUseCase getAllCarsUseCase,
                                  CarUiMapper carUiMapper) {
        this.getAllCarsUseCase = getAllCarsUseCase;
        this.carUiMapper = carUiMapper;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CarMapViewModel.class)) {
            return (T) new CarMapViewModel(this.getAllCarsUseCase, this.carUiMapper);
        }

        throw new IllegalArgumentException("UnKnow ViewModel class");
    }
}
