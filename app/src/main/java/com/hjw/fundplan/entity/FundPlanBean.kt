package com.hjw.fundplan.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe 基金定投计划
 */
@Entity(indices = [Index(value = ["code","cycle_type","cycle_value"], unique = true)])
class FundPlanBean {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code: String = ""
    var cycle_type: Int = 0
    var cycle_value: Int = 0
    //添加时间
    var time: Long = 0

    constructor(code: String, cycle_type: Int , cycle_value: Int , time: Long = System.currentTimeMillis()) {
        this.code = code
        this.cycle_type = cycle_type
        this.cycle_value = cycle_value
        this.time = time
    }
}