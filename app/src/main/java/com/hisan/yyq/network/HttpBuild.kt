package com.hisan.base.network

import okhttp3.Interceptor

/**
 * Created by yyq on 2018/6/19.
 */

object HttpBuild {
     var httpInterceptor: Interceptor?=null//日志拦截器 为空时使用默认的拦截器
    var timeOut = 20//超时时间  单位 s
    lateinit var cookieStore: CookieType//cookie机制


    class Build
    /**
     * @param httpInterceptor    日志拦截器 为空时使用默认的拦截器
     * @param timeOut            超时时间  单位 s
     * @param cookieStore        cookie机制
     */
    (httpInterceptor: Interceptor?, timeOut: Int, cookieStore: CookieType) {
        init {
            HttpBuild.httpInterceptor = httpInterceptor
            HttpBuild.timeOut = timeOut
            HttpBuild.cookieStore = cookieStore
        }
    }

    enum class CookieType {
        SPCookieStore, DBCookieStore, MemoryCookieStore
    }

    enum class HttpUploadDataType {
        JSON, XML, DEFAULT
    }
}
