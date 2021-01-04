package com.hjw.fundplan.presenter

import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.contract.IFundRecordsPresenter
import com.hjw.fundplan.contract.IFundRecordsView
import com.hjw.fundplan.net.DbRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class FundRecordsPresenter : BasePresenter<IFundRecordsView>(), IFundRecordsPresenter {
    val TAG = FundRecordsPresenter::javaClass.name
    override fun getRecords(code: String) {
        GlobalScope.launch {
            mView.context?.let {
                val fundHaveBeansByCode = DbRepo(it).getFundHaveBeansByCode(code)
                if (fundHaveBeansByCode.isNotEmpty()) {
                    mView.setData(fundHaveBeansByCode)
                }
            }
        }
    }

}