package com.hjw.fundplan.activity

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IFundPlanAddPresenter
import com.hjw.fundplan.contract.IFundPlanAddView
import com.hjw.fundplan.entity.FundPlanBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.presenter.FundPlanAddPresenter
import com.hjw.fundplan.util.AppUtils
import com.hjw.fundplan.util.JsonUtils
import com.hjw.fundplan.util.TimeUtils
import kotlinx.android.synthetic.main.activity_fund_plan_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast


class FundPlanAddActivity : BaseActivity<IFundPlanAddPresenter>(), IFundPlanAddView {

    companion object {
        val CODE = "CODE"
    }

    private var code: String? = null
    private var options1Items: ArrayList<String> = ArrayList()
    private val options2Items: ArrayList<ArrayList<String>> = ArrayList()
    private var cycleType: Int = 0
    private var cycleValue: Int = 0
    override fun initView() {
        code = intent.getStringExtra(CODE)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title =
            resources.getString(R.string.plan_dateMoney)
        mPresenter?.getFundInfo(code!!)
        AppUtils.setEditTextHintSize(et_money, "最低定投金额10元", 15)
        ll_right.setOnClickListener {
            mContext?.let {
                AppUtils.hideSoftInputFromWindow(it, et_money)
            }
            showPickerView()
        }
        btn_add.setOnClickListener {
            val money = et_money.text.toString()
            if (money.isNullOrEmpty()) {
                toast("请输入金额")
                return@setOnClickListener
            }
            GlobalScope.launch {
                mContext?.let { it1 ->
                    DbRepo(it1).addFundPlanBean(FundPlanBean(code!!, cycleType, cycleValue))
                    finish()
                }
            }

        }
        GlobalScope.launch {
            getPlanCycle()
        }
    }

    private fun showPickerView() { // 弹出选择器
        val pvOptions: OptionsPickerView<*> =
            OptionsPickerBuilder(this,
                OnOptionsSelectListener { options1, options2, _, v -> //返回的分别是三个级别的选中位置
                    cycleType = options1
                    cycleValue = options2
                    tv_date.text = options1Items[options1] + options2Items[options1][options2]
                })
                .setTitleText("定投周期")
                .setDividerColor(getColor(R.color.col_eee))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .setTitleSize(16)
                .build<Any>()

        pvOptions.setPicker(
            options1Items as List<Nothing>?,
            options2Items as List<Nothing>?
        )
        pvOptions.setSelectOptions(cycleType, cycleValue)
        pvOptions.show()
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

    private fun getPlanCycle() {
        val assetsString = mContext?.let { AppUtils.getAssetsString(it, "plantime.json") }
        if (!assetsString.isNullOrEmpty()) {
            val parseJson = JsonUtils.parseJson(assetsString, Array<CycleBean>::class.java)
            if (parseJson != null) {
                for (i in parseJson.indices) {
                    options1Items.add(parseJson[i].name)
                    val cityList: ArrayList<String> = ArrayList()
                    for (c in parseJson[i].value.indices) {
                        val cityName: String = parseJson[i].value[c]
                        cityList.add(cityName)
                    }
                    options2Items.add(cityList)

                }
            }

        }
        runOnUiThread {
            val todayWeekStatus = TimeUtils.getTodayWeekStatus()
            cycleValue = if (todayWeekStatus > 3) 0 else {
                todayWeekStatus + 1
            }
            tv_date.text = "${options1Items[cycleType]}${options2Items[cycleType][cycleValue]}"

        }
    }

    data class CycleBean(var name: String = "", var value: List<String> = mutableListOf())
}
