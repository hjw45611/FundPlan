package com.hjw.fundplan.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hjw.fundplan.R
import kotlinx.android.synthetic.main.fragment_count_tools.*
import org.jetbrains.anko.support.v4.toast


class CountToolsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_count_tools, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_come.setOnClickListener {
            val have =
                if (et_have.text.toString().isEmpty()) 0.0 else et_have.text.toString().toDouble()
            val flag =
                if (et_flag.text.toString().isEmpty()) 0.0  else et_flag.text.toString().toDouble()
            val put =
                if (et_put.text.toString().isEmpty()) 0.0  else et_put.text.toString().toDouble()
            val rate =
                if (et_rate.text.toString().isEmpty()) 1.0  else et_rate.text.toString().toDouble()/100+1
            getResult(have,put,rate,flag)
            toast(if (year>100) "请重新配置" else "$year 年达成目标")
            year = 0
        }
    }

    var year = 0

    private fun getResult(money: Double, add: Double, rate: Double, flag: Double) {
        val result = (money + add) * rate
        year += 1
        if (year > 100) {
            return
        }
        if (result < flag) {
            getResult(result, add, rate, flag)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CountToolsFragment()
    }
}
