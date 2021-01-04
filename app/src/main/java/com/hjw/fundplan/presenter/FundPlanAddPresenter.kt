package com.hjw.fundplan.presenter

import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.contract.IFundPlanAddPresenter
import com.hjw.fundplan.contract.IFundPlanAddView
import com.hjw.fundplan.net.DbRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class FundPlanAddPresenter : BasePresenter<IFundPlanAddView>(), IFundPlanAddPresenter {
    val TAG = FundPlanAddPresenter::javaClass.name
    override fun getFundInfo(code: String) {
        GlobalScope.launch {
            mView.context?.let {
                mView.setFundInfo(DbRepo(it).getFundSearchBean(code))
            }
        }
    }


}