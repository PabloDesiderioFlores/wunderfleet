package com.ar.pablo.domain;

import com.ar.pablo.domain.executor.PostExecutionThread;
import com.ar.pablo.domain.executor.ThreadExecutor;

import io.reactivex.Completable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableUseCase extends BaseUseCase {

    private final PostExecutionThread postExecutionThread;

    protected CompletableUseCase(ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor);
        this.postExecutionThread = postExecutionThread;
    }

    abstract Completable buildUseCaseObservable();

    public void execute(DisposableCompletableObserver useCaseSubscriber) {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }

        subscription = buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(this.postExecutionThread.getScheduler())
                .subscribeWith(useCaseSubscriber);
    }
}
