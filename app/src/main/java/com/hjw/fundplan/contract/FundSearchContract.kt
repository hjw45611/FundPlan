package com.hjw.fundplan.contract

import com.hjw.fundplan.base.IContract
import com.hjw.fundplan.bean.FundInfoBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
interface IFundSearchPresenter : IContract.IPresenter<IFundSearchView> {
    fun search(word: String)
}

interface IFundSearchView : IContract.IView {
    fun setFundInfo(info: FundInfoBean?)
}