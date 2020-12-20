package com.hjw.fundplan.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjw.fundplan.R
import com.hjw.fundplan.bean.DiffBean
import com.hjw.fundplan.entity.MyFundBean
import com.hjw.fundplan.util.StringUtils
import org.jetbrains.anko.textColor

class FundShowAdapter : RecyclerView.Adapter<FundShowAdapter.ViewHolder>() {

    private val mDatas = mutableListOf<MyFundBean>()

    fun updateData(datas: List<MyFundBean>) {
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fundshow, parent, false)
        )
    }

    override fun getItemCount(): Int = mDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mDatas[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.tv_fundName)
        val txtMoney = itemView.findViewById<TextView>(R.id.tv_money)
        val txtEarnings = itemView.findViewById<TextView>(R.id.tv_earnings)
        val txtEarningsRate = itemView.findViewById<TextView>(R.id.tv_earningsRate)
        fun bindData(appInfoBean: MyFundBean) {
            txtName.text = appInfoBean.name
            txtMoney.text = StringUtils.getDoubleString(appInfoBean.money)
            txtEarnings.text = StringUtils.getDoubleString(appInfoBean.earnings)
            txtEarningsRate.text = "${StringUtils.getDoubleString(appInfoBean.rate_return)}%"
        }
    }
}