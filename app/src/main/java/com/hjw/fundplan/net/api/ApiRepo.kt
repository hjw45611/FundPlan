package com.hjw.fundplan.net.api

import com.hjw.fundplan.App
import com.hjw.fundplan.bean.FundInfoBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.net.DbRepo
import com.hjw.fundplan.util.JsonUtils
import com.hjw.fundplan.util.TimeUtils
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

/**
 * @author hejiangwei
 * Created at 2020/12/14.
 * @Describe
 */
class ApiRepo {
    private var mdmLoginApiStore: ApiStore? = null

    fun getMdmLoginApiStore(): ApiStore? {
        if (mdmLoginApiStore == null) {

            val httpClient: OkHttpClient = OkHttpClient.Builder().build()
            // 接口
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://xueqiu.com")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            mdmLoginApiStore = retrofit.create(ApiStore::class.java)
        }
        return mdmLoginApiStore
    }

    fun searchCode(code: String, back: IBaseCallback) {
        getMdmLoginApiStore()?.let {
            val imeiLogin = it.searchByCode(code)
            imeiLogin!!.enqueue(object : Callback<ResponseBody?> {
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    back.onFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    var result = "";
                    val string = response.body()?.string()
                    if (!string.isNullOrEmpty()) {
                        result = string.substring(string.indexOf("(") + 1, string.indexOf(")"))
                        val info = JsonUtils.fromJson(result, FundInfoBean::class.java)
                        GlobalScope.launch {
                            if (info != null) {
                                App.APP_CONTEXT?.let { it1 ->
                                    DbRepo(it1).addFundSearchBean(
                                        FundSearchRecordBean(
                                            info.fundcode,
                                            info.name,
                                            info.dwjz.toDouble(),
                                            info.gsz.toDouble(),
                                            info.gszzl.toDouble(),
                                            TimeUtils.string2Millis(
                                                info.gztime,
                                                SimpleDateFormat("yyyy-MM-dd HH:mm")
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                    back.onSuccess(result)
                }

            })
        }
    }


    fun getMainInfo(back: IBaseCallback) {
        getMdmLoginApiStore()?.let {
            //1.000001 上证指数
            //1.000300 沪深300
            //0.399001 深证成指
            //0.399006 创业板指
            //0.399005 中小板指
            //8.040120 IF当月连续
            //104.CN00Y A50期指当月连续
            //100.FTSE 英国富时100
            //100.N225 日经225
            //100.GDAXI 德国DAX30
            //NYMEX原油 102.CL00Y
            //COMEX黄金 101.GC00Y
            //美元指数 100.UDI
            //美元离岸人民币 133.USDCNH
            //美元人民币中间价 120.USDCNYC
            //原油主力 142.scm
            //恒生指数100.HSI
            //道琼斯 100.DJIA
            //纳斯达克 100.NDX
            val imeiLogin = it.getMainInfo(
                "1.000001,0.399001,0.399006,100.HSI,100.DJIA,100.NDX",
                "f1,f2,f3,f4,f12,f13,f14,f107,f152"
            )
            imeiLogin!!.enqueue(object : Callback<ResponseBody?> {
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    back.onFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    val string = response.body()?.string()
                    if (!string.isNullOrEmpty()) {
                        back.onSuccess(string)
                    }
                }

            })
        }
    }


    fun getFundJingzhi(code: String, page: Int, back: IBaseCallback) {
        getMdmLoginApiStore()?.let {
            val imeiLogin = it.getFundJingzhi(
                code, page, 20
            )
            imeiLogin!!.enqueue(object : Callback<ResponseBody?> {
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    back.onFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    val string = response.body()?.string()
                    if (!string.isNullOrEmpty()) {
                        back.onSuccess(string)
                    }
                }

            })
        }
    }
}