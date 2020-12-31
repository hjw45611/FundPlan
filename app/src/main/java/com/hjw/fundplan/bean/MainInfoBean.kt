package com.hjw.fundplan.bean

import android.text.TextUtils
import com.hjw.fundplan.util.JsonUtils

/**
 * @author hejiangwei
 * Created at 2020/12/14.
 * @Describe
 * {"rc":0,"rt":11,"svr":182481211,"lt":1,"full":1,"data":
 *      {"total":6,"diff":[
 *          {"f1":2,"f2":340080,"f3":64,"f4":2176,"f12":"000001","f13":1,"f14":"上证指数","f107":2,"f152":2},
 *          {"f1":2,"f2":1416797,"f3":142,"f4":19776,"f12":"399001","f13":0,"f14":"深证成指","f107":2,"f152":2},
 *          {"f1":2,"f2":288450,"f3":254,"f4":7150,"f12":"399006","f13":0,"f14":"创业板指","f107":2,"f152":2},
 *          {"f1":2,"f2":2686564,"f3":112,"f4":29715,"f12":"HSI","f13":100,"f14":"恒生指数","f107":2,"f152":2},
 *          {"f1":2,"f2":3033567,"f3":-22,"f4":-6830,"f12":"DJIA","f13":100,"f14":"道琼斯","f107":5,"f152":2},
 *          {"f1":2,"f2":1285022,"f3":-38,"f4":-4920,"f12":"NDX","f13":100,"f14":"纳斯达克","f107":5,"f152":2}]}}
 */
data class MainInfoBean(
    var rc: Int = 0,
    var rt: Int = 0,
    var svr: Long = 0,
    var lt: Int = 0,
    var full: Int = 0,
    var data: DataInfo? = null
){
    override fun equals(other: Any?): Boolean {
        if (other!=null) {
            if (other is MainInfoBean) {
                if (TextUtils.equals(JsonUtils.toJson(other.data),JsonUtils.toJson(this.data))) {
                    return true
                }
            }

        }
        return false
    }
}

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
