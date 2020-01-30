package com.ar.pablo.wundermobilitytest.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.pablo.domain.interactor.GetAllCarsUseCase;
import com.ar.pablo.domain.model.Car;
import com.ar.pablo.wundermobilitytest.ui.model.CarUi;
import com.ar.pablo.wundermobilitytest.ui.model.mapper.CarUiMapper;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class CarMapViewModel extends ViewModel {

    private final GetAllCarsUseCase getAllCarsUseCase;
    private MutableLiveData<List<CarUi>> carLiveData;
    private final CarUiMapper carUiMapper;

    public CarMapViewModel(GetAllCarsUseCase getAllCarsUseCase,
                           CarUiMapper carUiMapper) {
        this.getAllCarsUseCase = getAllCarsUseCase;
        this.carUiMapper = carUiMapper;
    }

    public LiveData<List<CarUi>> getCarInfo() {
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

    private void transformCar(List<Car> cars) {
        carLiveData.setValue(carUiMapper.transform(cars));
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
                viewModel.transformCar(cars);
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
