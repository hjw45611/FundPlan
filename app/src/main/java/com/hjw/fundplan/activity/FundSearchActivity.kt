package com.hjw.fundplan.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.bean.FundInfoBean
import com.hjw.fundplan.contract.IFundSearchPresenter
import com.hjw.fundplan.contract.IFundSearchView
import com.hjw.fundplan.ext.removeSpace
import com.hjw.fundplan.presenter.FundSearchPresenter
import kotlinx.android.synthetic.main.activity_fund_search.*

class FundSearchActivity : BaseActivity<IFundSearchPresenter>(), IFundSearchView {
    private var infoBean: FundInfoBean? = null
    override fun initView() {
        id_drawer_layout_toolbar.setTitle(R.string.fund_search)
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                //搜索 类型，内容。
                mPresenter?.search(getEtInfo())
                // 隐藏输入法
                hideSoftInputFromWindow(et_search)
                true
            } else {
                false
            }
        }

        //移除空格
        et_search.removeSpace()
        et_search.setText("")
        btn_add.setOnClickListener {
            val intent = Intent(context, FundAddActivity::class.java)
            intent.putExtra(FundAddActivity.Name, infoBean?.name)
            intent.putExtra(FundAddActivity.Code, infoBean?.fundcode)
            startActivity(intent)
        }
    }

    private fun getEtInfo(): String = et_search.text.toString().trim()

    override fun initPresenter() {
        mPresenter = FundSearchPresenter()
        (mPresenter as FundSearchPresenter).attachView(this)
    }

    protected fun hideSoftInputFromWindow(editText: EditText) {
        // 隐藏输入法
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager?.hideSoftInputFromWindow(editText.windowToken, 0)
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
}
