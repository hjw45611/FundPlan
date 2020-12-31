package com.hjw.fundplan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundRecordsPresenter
import com.hjw.fundplan.contract.IFundRecordsView
import com.hjw.fundplan.presenter.FundRecordsPresenter

class FundRecordsActivity : BaseActivity<IFundRecordsPresenter>(), IFundRecordsView {

    override fun initView() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_records
    }

    override fun initPresenter() {
        mPresenter = FundRecordsPresenter()
        (mPresenter as FundRecordsPresenter).attachView(this)
    }
}
