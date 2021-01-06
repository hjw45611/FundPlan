package com.hjw.fundplan.util

import java.text.DecimalFormat

/**
 * @author hejiangwei
 * Created at 2020/12/18.
 * @Describe
 */
object StringUtils {
    fun getDoubleString(d: Double): String {
        return DecimalFormat("#0.00").format(d)
    }
}