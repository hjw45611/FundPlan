package com.hjw.fundplan.activity

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.bean.FundLSJZListBean
import com.hjw.fundplan.contract.IFundAddPresenter
import com.hjw.fundplan.contract.IFundAddView
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.presenter.FundAddPresenter
import kotlinx.android.synthetic.main.activity_fund_add.*
import org.jetbrains.anko.toast


class FundAddActivity : BaseActivity<IFundAddPresenter>(), IFundAddView {
    companion object {
        const val Name: String = "FundAddName"
        const val Code: String = "FundAddCode"
    }

    private var code: String? = null
    private var beans: ArrayList<FundLSJZListBean> = arrayListOf()

    override fun initView() {

        var stringExtra = intent.getStringExtra(Name)
        code = intent.getStringExtra(Code)
        findViewById<Toolbar>(R.id.id_drawer_layout_toolbar).title = stringExtra

        initChart()
        btn_addFirst.setOnClickListener {
        }
        btn_addHave.setOnClickListener {
            val price = et_havePrice.text.toString()
            val num = et_haveNum.text.toString()
            if (price.isNullOrEmpty()) {
                toast("请输入持有成本")
                return@setOnClickListener
            }
            if (num.isNullOrEmpty()) {
                toast("请输入持有份额")
                return@setOnClickListener
            }

            mPresenter?.addFundHave(FundHaveRecordBean(code!!,price.toDouble(),num.toDouble()))
        }
    }

    private fun initChart() {
        char_line.description.isEnabled = false
        char_line.setTouchEnabled(true)

        // set listeners
//        char_line.setOnChartValueSelectedListener(this)
        char_line.setDrawGridBackground(false)
        char_line.setDragEnabled(true)
        char_line.setScaleEnabled(true)

        // force pinch zoom along both axis
        char_line.setPinchZoom(true)
        var xAxis: XAxis
        run {   // // X-Axis Style // //
            xAxis = char_line.getXAxis()
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM;
            xAxis.granularity = 1f;
            // vertical grid lines
//            xAxis.enableGridDashedLine(10f, 10f, 0f)
            xAxis.valueFormatter =
                IAxisValueFormatter { value, axis ->
                    beans.get(value.toInt()).FSRQ.substring(2)
                }
        }

        var yAxis: YAxis
        {   // // Y-Axis Style // //
            yAxis = char_line.getAxisLeft()
            yAxis.setDrawGridLines(false)

            // disable dual axis (only use LEFT axis)
            char_line.getAxisRight().setEnabled(false)

            // horizontal grid lines

            // axis range
            yAxis.axisMaximum = 10f
            yAxis.axisMinimum = 0f
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_fund_add
    }

    override fun initPresenter() {
        mPresenter = FundAddPresenter()
        (mPresenter as FundAddPresenter).attachView(this)
        mPresenter?.getValues(code!!, 1)
    }

    override fun setLineValues(list: List<FundLSJZListBean>, entrys: List<Entry>?) {
        beans.addAll(list)
        var set1: LineDataSet? = null
        if (char_line.getData() != null &&
            char_line.getData().getDataSetCount() > 0
        ) {
            set1 = char_line.getData().getDataSetByIndex(0) as LineDataSet
            if (entrys != null) {
                set1.entries.addAll(entrys)
            }
            set1.notifyDataSetChanged()
            char_line.getData().notifyDataChanged()
            char_line.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(entrys, "基金净值")
            set1.color = getColor(R.color.light_menu_header)
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set1.setFillColor(Color.BLACK)
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            char_line.setData(data)
            char_line.invalidate()
        }
    }


}
