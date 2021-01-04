package com.hjw.fundplan.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.hjw.fundplan.R
import com.hjw.fundplan.base.IContract.IPresenter
import com.hjw.fundplan.base.IContract.IView
import com.hjw.fundplan.util.AppUtils
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity<T : IPresenter<out IView?>?> : AppCompatActivity(),
    IView {
    // 加载进度条
    private val mLoadingDialog: Dialog? = null
    protected var mPresenter: T? = null
    protected var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.setStatusBarColor(this,R.color.light_menu_header)
        setContentView(getLayoutId())
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
        mContext = this
        if (findViewById<Toolbar>(R.id.id_drawer_layout_toolbar) != null) {
            setSupportActionBar(findViewById<Toolbar>(R.id.id_drawer_layout_toolbar))
            if (setNavigation()) {
                findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).setNavigationIcon(R.drawable.back)
                findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).setNavigationOnClickListener { finish() }
            }
        }
        initPresenter()
        initView()
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter!!)
        }
    }


    protected abstract fun initView()
    protected abstract fun getLayoutId(): Int
    open fun setNavigation(): Boolean = true
    open fun isRegisterEventBus(): Boolean = false
    protected abstract fun initPresenter()
    override fun onDestroy() {
        super.onDestroy()
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        if (mPresenter != null) {
            lifecycle.removeObserver(mPresenter!!)
        }
    }

    override val context: Context
        get() = this

    override val lifecycleOwner: LifecycleOwner
        get() = this

    override fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(messageResId: Int) {
        showMessage(getString(messageResId))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
    }

    override val activity: Activity
        get() = this

    fun onBack(view: View?) {
        finish()
    }

    companion object {
        private const val TAG = "My_Test"
    }
}