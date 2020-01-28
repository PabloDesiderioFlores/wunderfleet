package com.ar.pablo.wundermobilitytest.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.pablo.domain.interactor.GetAllCarsUseCase;
import com.ar.pablo.domain.model.Car;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class CarMapViewModel extends ViewModel {

    private final GetAllCarsUseCase getAllCarsUseCase;
    private MutableLiveData<List<Car>> carLiveData;

    public CarMapViewModel(GetAllCarsUseCase getAllCarsUseCase) {
        this.getAllCarsUseCase = getAllCarsUseCase;
    }

    public LiveData<List<Car>> getCarInfo() {
        if (carLiveData == null) {
            carLiveData = new MutableLiveData<>();
            loadCarInfo();
        }
        return carLiveData;
    }

    private void loadCarInfo() {
        this.getAllCarsUseCase.execute(new GetAllCarsUseCaseSubscriber(this));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getAllCarsUseCase.unsubscribe();
    }

    static class GetAllCarsUseCaseSubscriber extends DisposableObserver<List<Car>> {

        final WeakReference<CarMapViewModel> viewModelWeakReference;

        GetAllCarsUseCaseSubscriber(CarMapViewModel viewModel) {
            this.viewModelWeakReference = new WeakReference<>(viewModel);
        }

        @Override
        public void onNext(List<Car> cars) {
            CarMapViewModel viewModel = viewModelWeakReference.get();

            if (viewModel != null) {
                viewModel.carLiveData.setValue(cars);
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
}
