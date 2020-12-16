package com.hjw.fundplan.presenter

import android.util.Log
import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.bean.MainInfoBean
import com.hjw.fundplan.contract.IMainShowPresenter
import com.hjw.fundplan.contract.IMainShowView
import com.hjw.fundplan.net.api.ApiRepo
import com.hjw.fundplan.net.api.IBaseCallback
import com.hjw.fundplan.util.JsonUtils
import org.jetbrains.anko.toast

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class MainShowPresenter : BasePresenter<IMainShowView>(), IMainShowPresenter {
    val TAG = MainShowPresenter::javaClass.name
    override fun getMainInfo() {

        ApiRepo().getMainInfo(object : IBaseCallback {
            override fun onSuccess(message: String) {
                Log.d(TAG, "onSearchSuccess=$message")
                mView.let {
                    it.setMainInfo(JsonUtils.fromJson(message, MainInfoBean::class.java))
                }
            }

            override fun onFailure() {
                Log.d(TAG, "onSearchFailure")
                mView.context?.toast("查询失败")
            }

        })
    }

}