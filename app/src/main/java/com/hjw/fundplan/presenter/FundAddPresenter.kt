package com.hjw.fundplan.presenter

import com.github.mikephil.charting.data.Entry
import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.bean.FundLSJZListBean
import com.hjw.fundplan.bean.FundValueBean
import com.hjw.fundplan.contract.IFundAddPresenter
import com.hjw.fundplan.contract.IFundAddView
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.event.FundAddEvent
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.net.api.ApiRepo
import com.hjw.fundplan.net.api.IBaseCallback
import com.hjw.fundplan.util.JsonUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class FundAddPresenter : BasePresenter<IFundAddView>(), IFundAddPresenter {
    val TAG = FundAddPresenter::javaClass.name
    val size = 0
    override fun getValues(fundCode: String, page: Int) {
        ApiRepo().getFundJingzhi(fundCode, page, object : IBaseCallback {
            override fun onSuccess(message: String) {
                val fromJson = JsonUtils.fromJson(message, FundValueBean::class.java)
                if (fromJson != null && fromJson.PageSize == 20) {
                    mView.setLineValues(
                        fromJson.Data?.LSJZList!!.reversed(),
                        fromJson.Data?.LSJZList?.let { getLineData(it.reversed()) })
                }
            }

            override fun onFailure() {

            }

        })
    }

    override fun addFundHave(fundHaveRecordBean: FundHaveRecordBean) {
        GlobalScope.launch {
            mView.context?.let { DbRepo(it).addFundHaveBean(fundHaveRecordBean) }
            EventBus.getDefault().post(FundAddEvent())
            mView.activity?.finish()
        }

    }

    private fun getLineData(beans: List<FundLSJZListBean>): ArrayList<Entry> {
        val values: ArrayList<Entry> = ArrayList()
        for ((index, bean) in beans.withIndex()) {
            var en: Entry = Entry((size + index).toFloat(), bean.DWJZ.toFloat())
            values.add(en)
        }
        return values
    }

}