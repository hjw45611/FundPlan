package com.hjw.fundplan.activity

import androidx.appcompat.widget.Toolbar
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundPlanAddPresenter
import com.hjw.fundplan.contract.IFundPlanAddView
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.presenter.FundPlanAddPresenter
import com.hjw.fundplan.util.AppUtils
import kotlinx.android.synthetic.main.activity_fund_plan_add.*

class FundPlanAddActivity : BaseActivity<IFundPlanAddPresenter>(), IFundPlanAddView {

    private var fundInfo: FundSearchRecordBean? = null

    companion object {
        val CODE = "CODE"
    }

    private var code: String? = null
    override fun initView() {
        code = intent.getStringExtra(CODE)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title =
            resources.getString(R.string.plan_dateMoney)
        mPresenter?.getFundInfo(code!!)
        AppUtils.setEditTextHintSize(et_money,"最低定投金额10元",15)
        ll_right.setOnClickListener {
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_plan_add
    }

    override fun initPresenter() {
        mPresenter = FundPlanAddPresenter()
        (mPresenter as FundPlanAddPresenter).attachView(this)
    }

    override fun setFundInfo(fundSearchRecordBean: FundSearchRecordBean) {
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title = fundSearchRecordBean.name
    }
}
