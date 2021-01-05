package com.hjw.fundplan.util

import android.app.Activity
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader


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

    /**
     * 获取Assets下文件内容
     */
    fun getAssetsString(context: Context, fileName: String): String {
        try {
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var next: String? = null
            val sb = StringBuilder()
            while ({ next = bufferedReader.readLine();next }() != null) {
                sb.append(next)
            }
            val mdmJson = sb.toString()

            return mdmJson
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
    fun hideSoftInputFromWindow(context: Context,editText: EditText) {
        // 隐藏输入法
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager?.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}