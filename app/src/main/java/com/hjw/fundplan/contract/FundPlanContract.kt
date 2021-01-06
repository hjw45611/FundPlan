package com.hjw.fundplan.contract

import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.entity.FundPlanBean
import com.hjw.fundplan.entity.FundSearchRecordBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundPlanPresenter : IContract.IPresenter<IFundPlanView> {
    fun getFundInfo(code: String)
    fun getFundPlans(code: String)
}

interface IFundPlanView : IContract.IView {
    fun setFundInfo(fundSearchRecordBean: MutableList<FundSearchRecordBean>)
    fun setFundPlans(plans:MutableList<FundPlanBean>)

}