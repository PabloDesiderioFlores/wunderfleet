package com.ar.pablo.domain;

import com.ar.pablo.domain.executor.PostExecutionThread;
import com.ar.pablo.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class ObservableUseCase extends BaseUseCase {

    private final PostExecutionThread postExecutionThread;

    protected ObservableUseCase(ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(threadExecutor);
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Observable buildUseCaseObservable();

    public void execute(DisposableObserver useCaseSubscriber) {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }

        subscription = (DisposableObserver) buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(this.postExecutionThread.getScheduler())
                .subscribeWith(useCaseSubscriber);
    }
}
