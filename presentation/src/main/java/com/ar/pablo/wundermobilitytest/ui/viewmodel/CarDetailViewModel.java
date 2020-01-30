package com.ar.pablo.wundermobilitytest.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.pablo.domain.interactor.GetCarByIdUseCase;
import com.ar.pablo.domain.model.Car;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class CarDetailViewModel extends ViewModel {

    private final GetCarByIdUseCase getCarByIdUseCase;
    private MutableLiveData<Car> carLiveData;

    public CarDetailViewModel(GetCarByIdUseCase getCarByIdUseCase) {
        this.getCarByIdUseCase = getCarByIdUseCase;
    }

    public LiveData<Car> getCarInfoById(String carId) {
        if (carLiveData == null) {
            carLiveData = new MutableLiveData<>();
            loadCarInfo(carId);
        }
        return carLiveData;
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

    @Override
    protected void onCleared() {
        super.onCleared();
        getCarByIdUseCase.unsubscribe();
    }
}
