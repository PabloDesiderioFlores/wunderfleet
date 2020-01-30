package com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.domain.interactor.GetCarByIdUseCase;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarDetailViewModel;

import javax.inject.Inject;

public class CarDetailViewModelFactory implements ViewModelProvider.Factory {

    private final GetCarByIdUseCase getCarByIdUseCase;

    @Inject
    public CarDetailViewModelFactory(GetCarByIdUseCase getCarByIdUseCase) {
        this.getCarByIdUseCase = getCarByIdUseCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CarDetailViewModel.class)) {
            return (T) new CarDetailViewModel(this.getCarByIdUseCase);
        }

        throw new IllegalArgumentException("UnKnow ViewModel class");
    }
}
