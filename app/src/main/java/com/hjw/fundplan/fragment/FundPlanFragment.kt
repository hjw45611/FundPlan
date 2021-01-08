package com.hjw.fundplan.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjw.fundplan.R
import com.hjw.fundplan.activity.FundPlanAddActivity
import com.hjw.fundplan.activity.FundSearchActivity
import com.hjw.fundplan.adapter.FundPlanAdapter
import com.hjw.fundplan.base.BaseFragment
import com.hjw.fundplan.bean.PlanRecordShowBean
import com.hjw.fundplan.contract.IFundPlanPresenter
import com.hjw.fundplan.contract.IFundPlanView
import com.hjw.fundplan.entity.FundPlanBean
import com.hjw.fundplan.entity.FundPlanRecordBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.event.FundPlanAddEvent
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.presenter.FundPlanPresenter
import com.hjw.fundplan.util.Const
import kotlinx.android.synthetic.main.fragment_fund_plan.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast

private const val ARG_PARAM1 = "Code"

/**
 * A simple [Fragment] subclass.
 * Use the [FundPlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FundPlanFragment : BaseFragment<IFundPlanPresenter>(), IFundPlanView {
    private var adapter: FundPlanAdapter? = null
    private var fundInfo: MutableList<FundSearchRecordBean> = mutableListOf()
    private var planShowList: ArrayList<PlanRecordShowBean> = arrayListOf()
    private var fundPlanBeans: MutableList<FundPlanBean> = mutableListOf()
    private var code: String? = null
    private var neesPlanInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fund_plan, container, false)
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_fundPlan.layoutManager = LinearLayoutManager(mContext)
        adapter = FundPlanAdapter()
        rv_fundPlan.adapter = adapter
        tv_noData.visibility = View.GONE
        btn_add.setOnClickListener {

            var intent: Intent? = null
            if (code.isNullOrEmpty()) {
                intent = Intent(mContext, FundSearchActivity::class.java)
                intent.putExtra(FundSearchActivity.TOPLAN, true)
            } else {
                intent = Intent(mContext, FundPlanAddActivity::class.java)
                intent.putExtra(Const.CODE, code)
            }

            startActivity(intent)
        }
        mPresenter?.getFundInfo(code!!)

    }

    companion object {

        @JvmStatic
        fun newInstance(code: String) =
            FundPlanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, code)
                }
            }
    }

    override fun initPresenter() {
        mPresenter = FundPlanPresenter()
        mPresenter.attachView(this)
    }

    override fun setFundInfo(fundSearchRecordBeans: MutableList<FundSearchRecordBean>) {
        fundInfo.clear()
        fundInfo.addAll(fundSearchRecordBeans)
        if (fundInfo.isNotEmpty()) {
            mPresenter?.getFundPlans(code!!)
        }

    }

    override fun onResume() {
        super.onResume()
        planShowList.clear()
        if (neesPlanInfo) {
            neesPlanInfo = false
            mPresenter.getFundInfo(code!!)
        } else {
            if (fundInfo.isNotEmpty()) {
                mPresenter?.getFundPlans(code!!)
            }
        }
    }

    override fun setFundPlans(plans: MutableList<FundPlanBean>) {
        fundPlanBeans.clear()
        fundPlanBeans.addAll(plans)
        getRecordShows()
    }

    private fun getRecordShows() {
        GlobalScope.launch {
            if (code?.isNotEmpty() == true && fundInfo == null) return@launch
            var loadFundPlanRecordByCode =
                getAllRecords()
            if (loadFundPlanRecordByCode.isNullOrEmpty()) {
                if (!fundPlanBeans.isNullOrEmpty()) {
                    loadFundPlanRecordByCode = mutableListOf()
                    fundPlanBeans?.forEach {
                        loadFundPlanRecordByCode.add(
                            FundPlanRecordBean(it.code, it.cycle_type, it.cycle_value)
                        )
                    }

                }
            }
            if (!loadFundPlanRecordByCode.isNullOrEmpty()) {
                var planRecordShowBean: PlanRecordShowBean? = null
                loadFundPlanRecordByCode.indices.forEach {
                    val money = loadFundPlanRecordByCode[it].money
                    val cycle_type = loadFundPlanRecordByCode[it].cycle_type
                    val cycle_value = loadFundPlanRecordByCode[it].cycle_value
                    val code1 = loadFundPlanRecordByCode[it].code
                    if (planRecordShowBean == null) {
                        planRecordShowBean = PlanRecordShowBean(
                            code1,
                            getPlanName(code1),
                            money,
                            if (money.toInt() == 0) 0 else 1,
                            cycle_type,
                            cycle_value,
                            getPlanBean(cycle_type, cycle_value).money
                        )
                    } else {
                        //不一定是连续的，也有可能是早已加入过的
                        if (code1 == planRecordShowBean!!.code && cycle_type == planRecordShowBean!!.cycle_type &&
                            cycle_value == planRecordShowBean!!.cycle_value) {
                            planRecordShowBean!!.money += money
                            planRecordShowBean!!.nums += if (money.toInt() == 0) 0 else 1
                            return@forEach
                        } else {
                            val hadInputIndex = getHadInputIndex(code1, cycle_type, cycle_value)
                            if (hadInputIndex>-1){
                                planShowList[hadInputIndex].money += money
                                planShowList[hadInputIndex].nums += if (money.toInt() == 0) 0 else 1
                                return@forEach
                            }
                            planShowList.add(planRecordShowBean!!)
                            planRecordShowBean = PlanRecordShowBean(
                                code1,
                                getPlanName(code1),
                                money,
                                if (money.toInt() == 0) 0 else 1,
                                cycle_type,
                                cycle_value,
                                getPlanBean(cycle_type, cycle_value).money
                            )
                        }
                    }
                    if (it == loadFundPlanRecordByCode.size - 1) {
                        planShowList.add(planRecordShowBean!!)
                    }
                }
            }
            runOnUiThread {
                adapter?.updateData(planShowList)
                if (planShowList.isNullOrEmpty()) {
                    toast("暂无定投，快去设置吧")
                }
            }


        }
    }

    private fun getHadInputIndex(code: String,type: Int,value: Int):Int {
        return planShowList.forEachIndexed { index, bean ->
            if(bean.code == code && bean.cycle_type == type && bean.cycle_value == value){
            index
        }}?.let { -1 }
    }
    private fun getAllRecords(): MutableList<FundPlanRecordBean>? {
        var loadFundPlanRecordByCode =
            mContext?.let {
                if (code.isNullOrEmpty()) DbRepo(it).loadAllFundPlanRecord() else DbRepo(it).loadFundPlanRecordByCode(
                    code!!
                )
            }
        return loadFundPlanRecordByCode
    }

    private fun getPlanBean(type: Int, value: Int): FundPlanBean {
        val indexOfFirst =
            fundPlanBeans?.indexOfFirst { it.cycle_type == type && it.cycle_value == value }
        return fundPlanBeans!!.get(indexOfFirst!!)
    }

    private fun getPlanName(code: String): String {
        val indexOfFirst =
            fundInfo?.indexOfFirst { it.code == code }
        return fundInfo!!.get(indexOfFirst!!).name
    }

    /**
     * 基金定投添加成功
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onFundPlanAddSuccess(e: FundPlanAddEvent?) {
        neesPlanInfo = true
    }
}
