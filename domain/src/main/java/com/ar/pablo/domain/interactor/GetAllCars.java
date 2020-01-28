package com.ar.pablo.domain.interactor;

import com.ar.pablo.domain.ObservableUseCase;
import com.ar.pablo.domain.executor.PostExecutionThread;
import com.ar.pablo.domain.executor.ThreadExecutor;
import com.ar.pablo.domain.repository.CarRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetAllCars extends ObservableUseCase {

    private final CarRepository carRepository;

    @Inject
    public GetAllCars(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread,
                      CarRepository carRepository) {
        super(threadExecutor, postExecutionThread);
        this.carRepository = carRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.carRepository.getAllCars();
    }
}
