package com.hjw.fundplan.util

import android.app.Activity
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.view.Window
import android.widget.EditText


/**
 * @author hejiangwei
 * Created at 2021/1/4.
 * @Describe
 */
object AppUtils {
    fun setStatusBarColor(activity: Activity, colorId: Int) {
        val window: Window = activity.window
        window.setStatusBarColor(activity.resources.getColor(colorId))
    }
    fun setEditTextHintSize(
        editText: EditText,
        hintText: String?,
        size: Int
    ) {
        val ss = SpannableString(hintText) //定义hint的值
        val ass = AbsoluteSizeSpan(size, true) //设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        editText.hint = SpannedString(ss)
    }
}