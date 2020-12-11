package com.hjw.fundplan.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
@Entity
class MyFundBean {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var code:String =""
    var name:String =""
    var money:Double= 0.0
    var earnings:Double= 0.0
    var rate_return:Double= 0.0


}