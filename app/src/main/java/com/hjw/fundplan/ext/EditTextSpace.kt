package com.hjw.fundplan.ext

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText

fun EditText.removeSpace() {
    filters = arrayOf<InputFilter>(object : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {
            if (source != null) {
                return if (source == " ") ""
                else source
            }
            return ""
        }
    })
}

