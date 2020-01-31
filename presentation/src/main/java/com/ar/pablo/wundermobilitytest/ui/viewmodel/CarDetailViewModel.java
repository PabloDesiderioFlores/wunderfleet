package com.ar.pablo.wundermobilitytest.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.pablo.domain.interactor.GetCarByIdUseCase;
import com.ar.pablo.domain.interactor.SetCarReservationUseCase;
import com.ar.pablo.domain.model.Car;
import com.ar.pablo.domain.model.CarReservation;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class CarDetailViewModel extends ViewModel {

    private final GetCarByIdUseCase getCarByIdUseCase;
    private final SetCarReservationUseCase setCarReservationUseCase;
    private MutableLiveData<Car> carLiveData;
    private MutableLiveData<CarReservation> carReservationLiveData;

    public CarDetailViewModel(GetCarByIdUseCase getCarByIdUseCase,
                              SetCarReservationUseCase setCarReservationUseCase) {
        this.getCarByIdUseCase = getCarByIdUseCase;
        this.setCarReservationUseCase = setCarReservationUseCase;
    }

    public LiveData<Car> getCarInfoById(String carId) {
        if (carLiveData == null) {
            carLiveData = new MutableLiveData<>();
            loadCarInfo(carId);
        }
        return carLiveData;
    }

    public LiveData<CarReservation> setCarReservation(String carId) {
        if (carReservationLiveData == null) {
            carReservationLiveData = new MutableLiveData<>();
            setReservationCar(carId);
        }
        return carReservationLiveData;
    }


    private void loadCarInfo(String carId) {
        this.getCarByIdUseCase.setCarId(carId);
        this.getCarByIdUseCase.execute(new GetCarByIdUseCaseSubscriber(this));
    }

    static class GetCarByIdUseCaseSubscriber extends DisposableObserver<Car> {

        final WeakReference<CarDetailViewModel> viewModelWeakReference;

        GetCarByIdUseCaseSubscriber(CarDetailViewModel viewModel) {
            this.viewModelWeakReference = new WeakReference<>(viewModel);
        }

        @Override
        public void onNext(Car car) {
            CarDetailViewModel viewModel = viewModelWeakReference.get();
            if (viewModel != null) {
                viewModel.carLiveData.setValue(car);
            }
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e);
        }

        @Override
        public void onComplete() {
            //Nothing to do here
        }
    }

    private void setReservationCar(String carId) {
        this.setCarReservationUseCase.setCarId(carId);
        this.setCarReservationUseCase.execute(new SetCarReservationUseCaseSubscriber(this));
    }

    static class SetCarReservationUseCaseSubscriber extends DisposableObserver<CarReservation> {

        final WeakReference<CarDetailViewModel> viewModelWeakReference;

        SetCarReservationUseCaseSubscriber(CarDetailViewModel viewModel) {
            viewModelWeakReference = new WeakReference<>(viewModel);
        }

        @Override
        public void onNext(CarReservation carReservation) {
            CarDetailViewModel viewModel = viewModelWeakReference.get();
            if (viewModel != null) {
                viewModel.carReservationLiveData.setValue(carReservation);
            }
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e);
        }

        @Override
        public void onComplete() {
            //nothing to do here
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getCarByIdUseCase.unsubscribe();
    }
}
