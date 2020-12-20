package com.hjw.fundplan.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjw.fundplan.R
import com.hjw.fundplan.activity.MainActivity
import com.hjw.fundplan.adapter.MainListAdapter
import com.hjw.fundplan.base.BaseFragment
import com.hjw.fundplan.bean.MainInfoBean
import com.hjw.fundplan.contract.IMainShowPresenter
import com.hjw.fundplan.contract.IMainShowView
import com.hjw.fundplan.inter.SwitchFragmentListener
import com.hjw.fundplan.presenter.MainShowPresenter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : BaseFragment<IMainShowPresenter>(), IMainShowView {
    var callBack: SwitchFragmentListener? = null
    var adapter: MainListAdapter? = null
    var handle: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            getMainInfo()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun bindCallBack(callBack: SwitchFragmentListener) {
        this.callBack = callBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_marketUpdate.layoutManager = LinearLayoutManager(context)
        adapter = MainListAdapter()
        rv_marketUpdate.adapter = adapter
        btn_toFund.setOnClickListener {
            callBack?.switchTag(MainActivity.FUNF_TAG)
        }
    }


    companion object {
        const val TAG = "MainFragment"

        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        getMainInfo()
    }

    override fun initPresenter() {
        mPresenter = MainShowPresenter()
        mPresenter.attachView(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "hidden=$hidden")
        if (!hidden) {
            getMainInfo()
        } else {
            handle.removeMessages(0)
        }
    }

    override fun onStop() {
        super.onStop()
        handle.removeMessages(0)
    }

    override fun setMainInfo(mainInfoBean: MainInfoBean?) {
        if (mainInfoBean != null) {
            if (mainInfoBean.data?.total!! > 0) {
                mainInfoBean?.data?.diff?.let { adapter?.updateData(it) }

            }
        }
    }

    fun getMainInfo() {
        mPresenter.getMainInfo()
        handle.sendEmptyMessageDelayed(0, 5000)

    }
}
