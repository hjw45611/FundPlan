package com.hjw.fundplan.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjw.fundplan.R
import com.hjw.fundplan.bean.DiffBean
import org.jetbrains.anko.textColor

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    private val mDatas = mutableListOf<DiffBean>()

    fun updateData(datas: List<DiffBean>) {
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mainshow, parent, false)
        )
    }

    override fun getItemCount(): Int = mDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mDatas[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtIndex = itemView.findViewById<TextView>(R.id.tv_index)
        val txtStatus = itemView.findViewById<TextView>(R.id.tv_index_status)
        fun bindData(appInfoBean: DiffBean) {
            txtIndex.text = appInfoBean.f14
            txtStatus.text =
                "${(appInfoBean.f2.toDouble() / 100).toString()}  ${(appInfoBean.f4.toDouble() / 100).toString()}  ${(appInfoBean.f3.toDouble() / 100).toString()}%"

            txtStatus.textColor = if (appInfoBean.f3 < 0) Color.parseColor("#009900") else {
                Color.RED
            }
        }
    }
}