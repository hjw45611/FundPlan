package com.hjw.fundplan.base;


import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

public abstract class BaseFragment<T extends IContract.IPresenter<? extends IContract.IView>> extends Fragment implements IContract.IView {

    protected T mPresenter;

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
    }

    protected abstract void initPresenter();


    @Override
    public void showMessage(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMessage(message);
        }
    }

    @Override
    public void showMessage(int messageResId) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMessage(messageResId);
        }
    }

    @Override
    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
        }
    }

    protected void hideSoftInputFromWindow(EditText editText) {
        // 隐藏输入法
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }

    }
}
