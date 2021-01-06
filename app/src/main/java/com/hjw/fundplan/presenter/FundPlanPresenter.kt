package com.hjw.fundplan.presenter

import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.contract.IFundPlanPresenter
import com.hjw.fundplan.contract.IFundPlanView
import com.hjw.fundplan.net.DbRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class FundPlanPresenter : BasePresenter<IFundPlanView>(), IFundPlanPresenter {
    val TAG = FundPlanPresenter::javaClass.name
    override fun getFundInfo(code: String) {
        GlobalScope.launch {
            mView.context?.let {

                mView.setFundInfo(if (code.isNullOrEmpty()) DbRepo(it).getAllFundSearchBeans() else mutableListOf(DbRepo(it).getFundSearchBean(code)))
            }
        }
    }

    override fun getFundPlans(code: String) {
        GlobalScope.launch {
            mView.context?.let {
                mView.setFundPlans(DbRepo(it).getFundPlanBeans(code))
            }
        }
    }


}