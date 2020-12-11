package com.hjw.fundplan.base

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IContract {

    interface IPresenter<T : IView?> : LifecycleObserver {
        fun attachView(view: T)
        fun detachView()
    }

    interface IView {
        val context: Context?
        val activity: Activity?
        val lifecycleOwner: LifecycleOwner?


        fun showMessage(message: String?)
        fun showMessage(messageResId: Int)
        fun finish()
    }
}
