package com.hjw.fundplan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundSearchPresenter

class FundSearchActivity : BaseActivity<IFundSearchPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fund_search)
    }

    override fun initView() {

    }

    override fun initPresenter() {

    }
}
