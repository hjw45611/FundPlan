package com.hjw.fundplan.contract

import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.entity.FundHaveRecordBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundRecordsPresenter : IContract.IPresenter<IFundRecordsView> {
    fun getRecords(code: String)
}

interface IFundRecordsView : IContract.IView {
    fun setData(data:MutableList<FundHaveRecordBean>)
}