package com.ly.rxlibrary.net.api;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public abstract class ApiDisposableObserver<T> implements Observer<T>, Disposable {
    final AtomicReference<Disposable> s = new AtomicReference<Disposable>();

    @Override
    public void onSubscribe(@NonNull Disposable s) {
        if (EndConsumerHelper.setOnce(this.s, s, getClass())) {
            onStart();
        }
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected void onStart() {
    }

    @Override
    public final boolean isDisposed() {
        return s.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        DisposableHelper.dispose(s);
    }
}
