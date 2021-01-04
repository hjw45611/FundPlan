package com.hjw.fundplan.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjw.fundplan.R
import com.hjw.fundplan.activity.FundRecordsActivity
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.util.StringUtils
import com.hjw.fundplan.util.TimeUtils

class FundRecordAdapter : RecyclerView.Adapter<FundRecordAdapter.ViewHolder>() {

    private val mDatas = mutableListOf<FundHaveRecordBean>()

    fun updateData(datas: List<FundHaveRecordBean>) {
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fundrecord, parent, false)
        )
    }

    override fun getItemCount(): Int = mDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mDatas[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMoney = itemView.findViewById<TextView>(R.id.tv_money)
        val txtNums = itemView.findViewById<TextView>(R.id.tv_nums)
        val txtTime = itemView.findViewById<TextView>(R.id.tv_time)
        fun bindData(appInfoBean: FundHaveRecordBean) {
            txtMoney.text = StringUtils.getDoubleString(appInfoBean.price)
            txtNums.text = StringUtils.getDoubleString(appInfoBean.num)
            txtTime.text = TimeUtils.millis2String(appInfoBean.time)?.replace(" ","\n") ?: ""
        }
    }
}