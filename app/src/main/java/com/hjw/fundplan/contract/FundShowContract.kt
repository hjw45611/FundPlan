package com.hjw.fundplan.contract

import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.entity.MyFundBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundShowPresenter : IContract.IPresenter<IFundShowView> {
    fun getShowInfo()
    fun searchNew(beans: MutableList<MyFundBean>)
}

interface IFundShowView : IContract.IView {
    fun showFundInfo(beans: MutableList<MyFundBean>)

}