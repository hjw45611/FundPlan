package com.hjw.fundplan.presenter

import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.contract.IFundShowPresenter
import com.hjw.fundplan.contract.IFundShowView
import com.hjw.fundplan.entity.MyFundBean
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.net.api.ApiRepo
import com.hjw.fundplan.net.api.IBaseCallback
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

    override fun searchNew(beans: MutableList<MyFundBean>) {
        beans.forEach {
            ApiRepo().searchCode(it.code,object : IBaseCallback{
                override fun onSuccess(message: String) {
                    getShowInfo()
                }

                override fun onFailure() {
                }

            })

        }
    }
}