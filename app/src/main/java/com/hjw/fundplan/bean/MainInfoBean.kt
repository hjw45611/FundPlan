package com.hjw.fundplan.bean

/**
 * @author hejiangwei
 * Created at 2020/12/14.
 * @Describe
 */
data class MainInfoBean(
    var rc: Int = 0,
    var rt: Int = 0,
    var svr: Long = 0,
    var lt: Int = 0,
    var full: Int = 0,
    var data: DataInfo? = null
)

data class DataInfo(var total: Int = 0, var diff: List<DiffBean> = mutableListOf())
data class DiffBean(
    var f1: Int = 0,
    var f2: Int = 0,
    var f3: Int = 0,
    var f4: Int = 0,
    var f12: String = "",
    var f13: Int = 0,
    var f14: String = "",
    var f107: Int = 0,
    var f152: Int = 0
)
