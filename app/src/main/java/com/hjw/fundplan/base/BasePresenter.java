package com.hjw.fundplan.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;


/**
 * @author R&D
 */
public class BasePresenter<T extends IContract.IView> extends ViewModel implements IContract.IPresenter<T> {

    protected T mView;

    public BasePresenter() {
    }

    @Override
    public final void attachView(T view) {
        mView = view;
    }

    @Override
    public final void detachView() {
        mView = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (mView == null) {
            return;
        }
        onCtrlReady();
    }

    protected void onCtrlReady() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        detachView();
    }

}
