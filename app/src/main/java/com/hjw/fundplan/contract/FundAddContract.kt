package com.hjw.fundplan.contract

import com.github.mikephil.charting.data.Entry
import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.bean.FundLSJZListBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundAddPresenter : IContract.IPresenter<IFundAddView> {
    fun getValues(fundCode: String, page: Int)
}

interface IFundAddView : IContract.IView {
    fun setLineValues(beans: List<FundLSJZListBean>, entrys: List<Entry>?)
}