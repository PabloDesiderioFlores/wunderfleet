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
    private final CarUiMapper carUiMapper;
    private MutableLiveData<List<CarUi>> carLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();

    public CarMapViewModel(GetAllCarsUseCase getAllCarsUseCase,
                           CarUiMapper carUiMapper) {
        this.getAllCarsUseCase = getAllCarsUseCase;
        this.carUiMapper = carUiMapper;
    }

    public void loadCarInfo() {
        this.getAllCarsUseCase.execute(new GetAllCarsUseCaseSubscriber(this));
    }

    public LiveData<List<CarUi>> getCarLiveData() {
        return carLiveData;
    }

    public LiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
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
            CarMapViewModel viewModel = viewModelWeakReference.get();

            if (viewModel != null) {
                viewModel.errorLiveData.setValue(e);
            }
            Timber.e(e);
        }

        @Override
        public void onComplete() {
            //nothing to do here
        }
    }

    private void transformCar(List<Car> cars) {
        carLiveData.setValue(carUiMapper.transform(cars));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getAllCarsUseCase.unsubscribe();
    }
}
