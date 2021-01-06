package com.hjw.fundplan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjw.fundplan.R
import com.hjw.fundplan.bean.PlanRecordShowBean
import com.hjw.fundplan.util.StringUtils
import com.hjw.fundplan.util.TimeUtils

class FundPlanAdapter : RecyclerView.Adapter<FundPlanAdapter.ViewHolder>() {

    private val mDatas = mutableListOf<PlanRecordShowBean>()

    fun updateData(datas: List<PlanRecordShowBean>) {
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fundplan, parent, false)
        )
    }

    override fun getItemCount(): Int = mDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mDatas[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMoney = itemView.findViewById<TextView>(R.id.tv_input_money)
        val txtNums = itemView.findViewById<TextView>(R.id.tv_input_num)
        val txtName = itemView.findViewById<TextView>(R.id.tv_fundName)
        val txtInfo = itemView.findViewById<TextView>(R.id.tv_info)
        fun bindData(appInfoBean: PlanRecordShowBean) {
            txtName.text = appInfoBean.name
            txtMoney.text = StringUtils.getDoubleString(appInfoBean.money)
            txtNums.text = appInfoBean.nums.toString()
            txtInfo.text = when (appInfoBean.cycle_type) {
                0 -> "每周"
                1 -> "每月"
                else -> ""
            } + when (appInfoBean.cycle_type) {
                0 -> TimeUtils.getDayOfWeekByInt(appInfoBean.cycle_value)
                1 -> TimeUtils.getDayOfMonthByInt(appInfoBean.cycle_value)
                else -> ""
            } + "投入${appInfoBean.planMoney}元"

        }
    }
}