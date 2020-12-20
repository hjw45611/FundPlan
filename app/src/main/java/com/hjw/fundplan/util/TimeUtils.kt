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