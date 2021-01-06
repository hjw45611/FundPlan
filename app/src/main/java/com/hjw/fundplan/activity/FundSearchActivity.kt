package com.hjw.fundplan.activity

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.bean.FundInfoBean
import com.hjw.fundplan.contract.IFundSearchPresenter
import com.hjw.fundplan.contract.IFundSearchView
import com.hjw.fundplan.event.FundAddEvent
import com.hjw.fundplan.event.FundPlanAddEvent
import com.hjw.fundplan.ext.removeSpace
import com.hjw.fundplan.presenter.FundSearchPresenter
import com.hjw.fundplan.util.AppUtils
import com.hjw.fundplan.util.Const
import kotlinx.android.synthetic.main.activity_fund_search.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FundSearchActivity : BaseActivity<IFundSearchPresenter>(), IFundSearchView {
    companion object{
        val TOPLAN ="TOPLAN"
    }
    private var infoBean: FundInfoBean? = null
    private var toPlan = false
    override fun initView() {
        toPlan = intent.getBooleanExtra(TOPLAN,false)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).setTitle(R.string.fund_search)
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                //搜索 类型，内容。
                mPresenter?.search(getEtInfo())
                mContext?.let {
                    AppUtils.hideSoftInputFromWindow(it, et_search)
                }
                true
            } else {
                false
            }
        }

        //移除空格
        et_search.removeSpace()
        et_search.setText("")
        btn_add.setOnClickListener {
            var intent: Intent? = null
            if (toPlan) {
                intent = Intent(mContext, FundPlanAddActivity::class.java)
                intent.putExtra(Const.CODE, infoBean?.fundcode)
            } else {
                intent = Intent(context, FundAddActivity::class.java)
                intent.putExtra(FundAddActivity.Name, infoBean?.name)
                intent.putExtra(FundAddActivity.Code, infoBean?.fundcode)
            }

            startActivity(intent)
        }
    }

    private fun getEtInfo(): String = et_search.text.toString().trim()

    override fun initPresenter() {
        mPresenter = FundSearchPresenter()
        (mPresenter as FundSearchPresenter).attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_search
    }

    override fun setFundInfo(info: FundInfoBean?) {
        infoBean = info
        card_fund.visibility = if (infoBean == null) View.GONE else {
            tv_fundCode.text = infoBean!!.fundcode
            tv_fundName.text = infoBean!!.name
            tv_netValue.text = "净值\n${infoBean!!.dwjz}"
            tv_assessValue.text = "估算\n${infoBean!!.gsz}"
            tv_gszzl.text = "估算涨幅\n${infoBean!!.gszzl}%"
            View.VISIBLE
        }
        root_nodata.visibility = if (infoBean == null) View.VISIBLE else {
            View.GONE
        }


    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    /**
     * 基金记录成功
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onFundAddSuccess(e: FundAddEvent?) {
        finish()
    }
    /**
     * 基金定投添加成功
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onFundPlanAddSuccess(e: FundPlanAddEvent?) {
        finish()
    }

}
