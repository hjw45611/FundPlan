package com.hjw.fundplan.net.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author hejiangwei
 * Created at 2020/12/14.
 * @Describe
 */
interface ApiStore {
    /**
     * 根据代码号搜索
     */
    @GET("http://fundgz.1234567.com.cn/js/{code}.js")
    fun searchByCode(@Path("code") code: String?): Call<ResponseBody?>?

    /**
     * 首页信息
     */
    @GET("https://push2.eastmoney.com/api/qt/ulist.np/get")
    fun getMainInfo(
        @Query("secids") secids: String?,
        @Query("fields") fields: String?
    ): Call<ResponseBody?>?
}