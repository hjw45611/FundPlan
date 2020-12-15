package com.hjw.fundplan.net.api

/**
 * @author hejiangwei
 * Created at 2020/12/14.
 * @Describe
 */
interface IBaseCallback {
    fun onSuccess(message: String)

    fun onFailure()
}