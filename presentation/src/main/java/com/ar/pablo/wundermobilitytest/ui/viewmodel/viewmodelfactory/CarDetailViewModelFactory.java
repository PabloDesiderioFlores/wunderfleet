package com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.domain.interactor.GetCarByIdUseCase;
import com.ar.pablo.domain.interactor.SetCarReservationUseCase;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarDetailViewModel;

import javax.inject.Inject;

public class CarDetailViewModelFactory implements ViewModelProvider.Factory {

    private final GetCarByIdUseCase getCarByIdUseCase;
    private final SetCarReservationUseCase setCarReservationUseCase;

    @Inject
    public CarDetailViewModelFactory(GetCarByIdUseCase getCarByIdUseCase,
                                     SetCarReservationUseCase setCarReservationUseCase) {
        this.getCarByIdUseCase = getCarByIdUseCase;
        this.setCarReservationUseCase = setCarReservationUseCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CarDetailViewModel.class)) {
            return (T) new CarDetailViewModel(this.getCarByIdUseCase, this.setCarReservationUseCase);
        }

        throw new IllegalArgumentException("UnKnow ViewModel class");
    }
}
