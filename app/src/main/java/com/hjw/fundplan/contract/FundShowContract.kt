package com.hjw.fundplan.contract

import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.bean.MainInfoBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundShowPresenter : IContract.IPresenter<IFundShowView> {
    fun getShowInfo()
}

interface IFundShowView : IContract.IView {

}