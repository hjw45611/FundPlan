package com.hjw.fundplan

import android.app.Application
import android.content.Context

/**
 * @author hejiangwei
 * Created at 2020/12/20.
 * @Describe
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        APP_CONTEXT = this
    }

    companion object {
        private val TAG = App::class.java.name
        var APP_CONTEXT: Context? = null
    }
}