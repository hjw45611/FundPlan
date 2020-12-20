package com.hjw.fundplan.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe 基金搜索记录表
 */
@Entity
class FundSearchRecordBean {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code: String = ""
    var name: String = ""

    //确定净值
    var value: Double = 0.0

    //估值
    var gzValue: Double = 0.0

    //估值涨幅
    var gzRate: Double = 0.0

    //记录时间
    var time: Long = 0

    constructor(
        code: String = "",
        name: String = "",
        value: Double = 0.0,
        gzValue: Double = 0.0,
        gzRate: Double = 0.0,
        time: Long = 0
    ){
        this.code = code
        this.name = name
        this.value = value
        this.gzValue = gzValue
        this.gzRate = gzRate
        this.time = time
    }

}