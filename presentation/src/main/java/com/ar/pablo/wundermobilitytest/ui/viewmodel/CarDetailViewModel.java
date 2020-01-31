package com.ar.pablo.wundermobilitytest.ui.viewmodel;

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
    private MutableLiveData<Car> carLiveData = new MutableLiveData<>();
    private MutableLiveData<CarReservation> carReservationLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> carReservationLiveDataError = new MutableLiveData<>();
    private MutableLiveData<Throwable> carLiveDataError = new MutableLiveData<>();


    public CarDetailViewModel(GetCarByIdUseCase getCarByIdUseCase,
                              SetCarReservationUseCase setCarReservationUseCase) {
        this.getCarByIdUseCase = getCarByIdUseCase;
        this.setCarReservationUseCase = setCarReservationUseCase;
    }

    public void getCarInfoById(String carId) {
        this.getCarByIdUseCase.setCarId(carId);
        this.getCarByIdUseCase.execute(new GetCarByIdUseCaseSubscriber(this));
    }

    public void setCarReservation(String carId) {
        this.setCarReservationUseCase.setCarId(carId);
        this.setCarReservationUseCase.execute(new SetCarReservationUseCaseSubscriber(this));
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
            CarDetailViewModel viewModel = viewModelWeakReference.get();
            if (viewModel != null) {
                viewModel.carLiveDataError.setValue(e);
            }
        }

        @Override
        public void onComplete() {
            //Nothing to do here
        }
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
            CarDetailViewModel viewModel = viewModelWeakReference.get();
            if (viewModel != null) {
                viewModel.carReservationLiveDataError.setValue(e);
            }
        }

        @Override
        public void onComplete() {
            //nothing to do here
        }
    }

    public MutableLiveData<Throwable> getCarLiveDataError() {
        return carLiveDataError;
    }

    public MutableLiveData<Throwable> getCarReservationLiveDataError() {
        return carReservationLiveDataError;
    }

    public MutableLiveData<Car> getCarLiveData() {
        return carLiveData;
    }

    public MutableLiveData<CarReservation> getCarReservationLiveData() {
        return carReservationLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getCarByIdUseCase.unsubscribe();
        setCarReservationUseCase.unsubscribe();
    }
}
