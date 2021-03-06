package com.hjw.fundplan.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe 基金保存记录
 */
@Entity
class FundHaveRecordBean {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code: String = ""

    //成本
    var price: Double = 0.0

    //份额
    var num: Double = 0.0

    //时间
    var time: Long = 0

    constructor(
        code: String = "",
        price: Double = 0.0,
        num: Double = 0.0,
        time: Long = System.currentTimeMillis()
    ) {
        this.code = code
        this.price = price
        this.num = num
        this.time = time
    }

}