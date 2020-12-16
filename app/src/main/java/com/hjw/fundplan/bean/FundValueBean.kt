package com.hjw.fundplan.bean

/**
 * @author hejiangwei
 * Created at 2020/12/16.
 * @Describe
 */
data class FundValueBean(
    var ErrCode:Int=0,
    var ErrMsg:String="",
    var TotalCount:Int=0,
    var Expansion:String="",
    var PageSize:Int=0,
    var PageIndex:Int=0,
    var Data:FundDataBean? = null
)
data class FundDataBean(
    var FundType:String="",
    var SYType:String="",
    var isNewType:Boolean=false,
    var Feature:String="",
    var LSJZList:List<FundLSJZListBean> = mutableListOf()
)
data class FundLSJZListBean(
    var FSRQ:String="",
    var DWJZ:String="",
    var LJJZ:String="",
    var SDATE:String="",
    var ACTUALSYI:String="",
    var NAVTYPE:String="",
    var JZZZL:String="",
    var SGZT:String="",
    var SHZT:String="",
    var FHFCZ:String="",
    var FHFCBZ:String="",
    var DTYPE:String="",
    var FHSP:String=""
)