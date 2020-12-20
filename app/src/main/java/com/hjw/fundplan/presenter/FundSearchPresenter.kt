package com.hjw.fundplan.presenter

import android.util.Log
import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.bean.FundInfoBean
import com.hjw.fundplan.contract.IFundSearchPresenter
import com.hjw.fundplan.contract.IFundSearchView
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.net.api.ApiRepo
import com.hjw.fundplan.net.api.IBaseCallback
import com.hjw.fundplan.util.JsonUtils
import com.hjw.fundplan.util.TimeUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class FundSearchPresenter : BasePresenter<IFundSearchView>(), IFundSearchPresenter {
    val TAG = FundSearchPresenter::javaClass.name
    override fun search(word: String) {

//查询数据库
        ApiRepo().searchCode(word, object : IBaseCallback {
            override fun onSuccess(message: String) {
                Log.d(TAG, "onSearchSuccess=$message")

                val info = JsonUtils.fromJson(message, FundInfoBean::class.java)
                GlobalScope.launch {
                    mView.context?.let {
                        if (info != null) {
                            DbRepo(it).addFundSearchBean(
                                FundSearchRecordBean(
                                    info.fundcode,
                                    info.name,
                                    info.dwjz.toDouble(),
                                    info.gsz.toDouble(),
                                    info.gszzl.toDouble(),
                                    TimeUtils.string2Millis(
                                        info.gztime,
                                        SimpleDateFormat("yyyy-MM-dd HH:mm")
                                    )
                                )
                            )
                        }
                    }
                }
                mView.setFundInfo(info)
            }

            override fun onFailure() {
                Log.d(TAG, "onSearchFailure")
                mView.context?.toast("查询失败")
            }

        })
    }
}