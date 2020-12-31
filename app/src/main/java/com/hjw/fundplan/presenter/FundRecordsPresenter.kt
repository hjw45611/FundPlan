package com.hjw.fundplan.presenter

import com.github.mikephil.charting.data.Entry
import com.hjw.fundplan.base.BasePresenter
import com.hjw.fundplan.bean.FundLSJZListBean
import com.hjw.fundplan.bean.FundValueBean
import com.hjw.fundplan.contract.IFundAddPresenter
import com.hjw.fundplan.contract.IFundAddView
import com.hjw.fundplan.contract.IFundRecordsPresenter
import com.hjw.fundplan.contract.IFundRecordsView
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.event.FundAddEvent
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.net.api.ApiRepo
import com.hjw.fundplan.net.api.IBaseCallback
import com.hjw.fundplan.util.JsonUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
class FundRecordsPresenter : BasePresenter<IFundRecordsView>(), IFundRecordsPresenter {
    val TAG = FundRecordsPresenter::javaClass.name

}