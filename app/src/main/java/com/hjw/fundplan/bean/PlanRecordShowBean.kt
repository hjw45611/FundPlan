package com.hjw.fundplan.bean

/**
 * @author hejiangwei
 * Created at 2021/1/6.
 * @Describe
 */
data class PlanRecordShowBean(
    var code: String,
    var name: String,
    var money: Double,
    var nums: Int,
    var cycle_type: Int = 0,
    var cycle_value: Int = 0,
    var planMoney: Double
)