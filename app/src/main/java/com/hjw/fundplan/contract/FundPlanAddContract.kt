package com.hjw.fundplan.contract

import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.entity.FundSearchRecordBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundPlanAddPresenter : IContract.IPresenter<IFundPlanAddView> {
    fun getFundInfo(code: String)
}

interface IFundPlanAddView : IContract.IView {
    fun setFundInfo(fundSearchRecordBean: FundSearchRecordBean)

}