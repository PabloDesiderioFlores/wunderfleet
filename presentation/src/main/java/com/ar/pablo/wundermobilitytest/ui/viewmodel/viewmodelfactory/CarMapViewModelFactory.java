package com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.domain.interactor.GetAllCarsUseCase;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarMapViewModel;

import javax.inject.Inject;

public class CarMapViewModelFactory implements ViewModelProvider.Factory {

    private final GetAllCarsUseCase getAllCarsUseCase;

    @Inject
    public CarMapViewModelFactory(GetAllCarsUseCase getAllCarsUseCase) {
        this.getAllCarsUseCase = getAllCarsUseCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CarMapViewModel.class)) {
            return (T) new CarMapViewModel(this.getAllCarsUseCase);
        }

        throw new IllegalArgumentException("UnKnow ViewModel class");
    }
}
