package com.hjw.fundplan.activity

import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundAddPresenter
import kotlinx.android.synthetic.main.activity_fund_add.*

class FundAddActivity : BaseActivity<IFundAddPresenter>() {
    companion object {
        const val Name: String = "FundAddName"
    }

    override fun initView() {
        setSupportActionBar(id_drawer_layout_toolbar)

        var stringExtra = intent.getStringExtra(Name)
        id_drawer_layout_toolbar.title = stringExtra
        et_havePrice
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_add
    }

    override fun initPresenter() {

    }


}
