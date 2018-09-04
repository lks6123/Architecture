package com.leelu.library.http.subsciber;

import android.content.Context;

import com.leelu.library.http.exception.ApiException;
import com.leelu.library.utils.LogUtils;
import com.leelu.library.utils.NetworkUtils;
import com.leelu.library.utils.ToastUtils;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


/**
 * @author : leelu
 * CreateTime : 2018/8/3 14:45
 * Description :
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {
    public WeakReference<Context> contextWeakReference;

    @Override
    protected void onStart() {
        LogUtils.e("-->http is onStart");
        if (contextWeakReference != null && contextWeakReference.get() != null && !NetworkUtils.isConnected()) {
            ToastUtils.showShort("网络未连接，请检查网络设置");
            onComplete();
        }
    }


    public BaseObserver(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public final void onError(java.lang.Throwable e) {
        LogUtils.e("-->http is onError");
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {
        LogUtils.e("-->http is onComplete");
    }


    public abstract void onError(ApiException e);
}
