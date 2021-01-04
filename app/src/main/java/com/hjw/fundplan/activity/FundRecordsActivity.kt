package com.hjw.fundplan.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MainThread
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjw.fundplan.R
import com.hjw.fundplan.adapter.FundRecordAdapter
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundRecordsPresenter
import com.hjw.fundplan.contract.IFundRecordsView
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.presenter.FundRecordsPresenter
import kotlinx.android.synthetic.main.activity_fund_records.*


class FundRecordsActivity : BaseActivity<IFundRecordsPresenter>(), IFundRecordsView {


    companion object {
        val CODE = "CODE"
    }
    var code:String? = null
    var adapter: FundRecordAdapter? = null
    override fun initView() {
        code = intent.getStringExtra(CODE)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title = "交易记录"
        rv_fund.layoutManager = LinearLayoutManager(mContext)
        adapter = FundRecordAdapter()
        rv_fund.adapter = adapter

        mPresenter?.getRecords(code!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_record_plan, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(mContext, FundPlanActivity::class.java)
        intent.putExtra(FundPlanActivity.CODE,code)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_records
    }

    override fun initPresenter() {
        mPresenter = FundRecordsPresenter()
        (mPresenter as FundRecordsPresenter).attachView(this)
    }

    @MainThread
    override fun setData(data: MutableList<FundHaveRecordBean>) {
        adapter?.updateData(data)
    }
}
