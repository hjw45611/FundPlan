package com.hjw.fundplan.presenter

import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.contract.IFundShowPresenter
import com.hjw.fundplan.contract.IFundShowView
import com.hjw.fundplan.net.DbRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author hejiangwei
 * Created at 2020/12/16.
 * @Describe
 */
class FundShowPresenter : BasePresenter<IFundShowView>(), IFundShowPresenter {
    override fun getShowInfo() {
        GlobalScope.launch {
            mView.context?.let {
                val myFundBeanBeans = DbRepo(it).getMyFundBeanBeans()
                myFundBeanBeans
            }?.let {
                mView.showFundInfo(
                    it
                )
            }
        }
    }
}