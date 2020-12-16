package com.hjw.fundplan.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjw.fundplan.R
import com.hjw.fundplan.activity.FundSearchActivity
import com.hjw.fundplan.base.BaseFragment
import com.hjw.fundplan.contract.IFundShowPresenter
import com.hjw.fundplan.contract.IFundShowView
import com.hjw.fundplan.presenter.FundShowPresenter
import kotlinx.android.synthetic.main.fragment_fund.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FundFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FundFragment : BaseFragment<IFundShowPresenter>(), IFundShowView {
//    private var param1: String? = null
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fund, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_fund.layoutManager = LinearLayoutManager(context)
        btn_add.setOnClickListener {
            startActivity(Intent(context, FundSearchActivity::class.java))
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FundFragment.
         */
        @JvmStatic
        fun newInstance() =
            FundFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun initPresenter() {
        mPresenter = FundShowPresenter()
        mPresenter.attachView(this)
    }
}
