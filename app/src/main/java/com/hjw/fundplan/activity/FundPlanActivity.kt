package com.hjw.fundplan.activity

import androidx.appcompat.widget.Toolbar
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IMainPresenter
import com.hjw.fundplan.contract.IMainView
import com.hjw.fundplan.fragment.FundPlanFragment
import com.hjw.fundplan.util.Const

class FundPlanActivity : BaseActivity<IMainPresenter>(), IMainView {
    override fun initView() {
        val code = intent.getStringExtra(Const.CODE)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title = "我的定投"
        val beginTransaction = supportFragmentManager.beginTransaction()
        val newInstance = FundPlanFragment.newInstance(code)
        beginTransaction.add(R.id.root_content, newInstance)
        beginTransaction.commitAllowingStateLoss()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_plan
    }

    override fun initPresenter() {

    }
}
