package com.ar.pablo.domain;

import com.ar.pablo.domain.executor.ThreadExecutor;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public abstract class BaseUseCase {

    final ThreadExecutor threadExecutor;

    Disposable subscription = Disposables.empty();

    BaseUseCase(ThreadExecutor threadExecutor) {
        this.threadExecutor = threadExecutor;
    }

    /**
     * Disposes the current Subscription
     */
    public void unsubscribe() {
        if (!this.subscription.isDisposed()) {
            this.subscription.dispose();
        }
    }
}
