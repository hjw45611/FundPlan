package com.hjw.fundplan.activity

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjw.fundplan.R
import com.hjw.fundplan.adapter.FundPlanAdapter
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundPlanPresenter
import com.hjw.fundplan.contract.IFundPlanView
import com.hjw.fundplan.entity.FundPlanBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.presenter.FundPlanPresenter
import kotlinx.android.synthetic.main.activity_fund_plan.*

class FundPlanActivity : BaseActivity<IFundPlanPresenter>(), IFundPlanView {
    companion object {
        val CODE = "CODE"
    }

    private var adapter: FundPlanAdapter? = null
    private var fundInfo: FundSearchRecordBean? = null
    override fun initView() {
        val code = intent.getStringExtra(FundRecordsActivity.CODE)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title = "我的定投"
        rv_fundPlan.layoutManager = LinearLayoutManager(mContext)
        adapter = FundPlanAdapter()
        rv_fundPlan.adapter = adapter
        tv_noData.visibility = View.GONE
        btn_add.setOnClickListener {
            val intent = Intent(mContext, FundPlanAddActivity::class.java)
            intent.putExtra(FundPlanAddActivity.CODE, code)
            startActivity(intent)
        }
        mPresenter?.getFundInfo(code!!)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_plan
    }

    override fun initPresenter() {

        mPresenter = FundPlanPresenter()
        (mPresenter as FundPlanPresenter).attachView(this)

    }

    override fun setFundInfo(fundSearchRecordBean: FundSearchRecordBean) {
        fundInfo = fundSearchRecordBean

    }

    override fun setFundPlans(plans: MutableList<FundPlanBean>) {


    }
}
