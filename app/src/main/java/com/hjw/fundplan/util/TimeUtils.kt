package com.hjw.fundplan.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author hejiangwei
 * Created at 2020/12/16.
 * @Describe
 */
class TimeUtils {
    companion object {
        /**
         * Formatted time string to the milliseconds.
         *
         * The pattern is `yyyy-MM-dd HH:mm:ss`.
         *
         * @param time The formatted time string.
         * @return the milliseconds
         */
        fun string2Millis(time: String?, format: DateFormat = defaultFormat): Long {
            return format.parse(time).time
        }

        fun millis2String(millis: Long, format: DateFormat = defaultFormat): String? {
            return format.format(Date(millis))
        }

        /**
         * 获取当天星期几
         * @return 周一：0，周日：6
         */
        fun getTodayWeekStatus(): Int {
            val instance = Calendar.getInstance()
            val day = instance[Calendar.DAY_OF_WEEK]
            return if (day == Calendar.SUNDAY) 6 else day - 2
        }
        fun getDayOfWeekByInt(status:Int): String {
            return "星期"+when(status){
                0->"一"
                1->"二"
                2->"三"
                3->"四"
                4->"五"
                5->"六"
                6->"日"
                else->""
            }
        }
        fun getDayOfMonthByInt(status:Int): String {
            return "${status+1}日"
        }

        private val SDF_THREAD_LOCAL = ThreadLocal<SimpleDateFormat>()
        private val defaultFormat: SimpleDateFormat
            private get() {
                var simpleDateFormat = SDF_THREAD_LOCAL.get()
                if (simpleDateFormat == null) {
                    simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    SDF_THREAD_LOCAL.set(simpleDateFormat)
                }
                return simpleDateFormat
            }
    }


}