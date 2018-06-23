package com.hisan.base.network

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import okhttp3.OkHttpClient
import com.lzy.okgo.cookie.store.MemoryCookieStore
import com.lzy.okgo.cookie.CookieJarImpl
import com.lzy.okgo.cookie.store.DBCookieStore
import com.lzy.okgo.cookie.store.SPCookieStore
import okhttp3.CookieJar





/**
 * 创建时间 : 2017/12/7
 * 创建人：yangyingqi
 * 公司：嘉善和盛网络有限公司
 * 备注：OkGO初始化工具
 */
object KUtilLibs {
    /**
     * 初始化库
     *
     * @param context application
     */
    fun init(context: Application, mbuild: HttpBuild.Build) {
        val builder = OkHttpClient.Builder()
        if (HttpBuild.httpInterceptor != null) {
            builder.addInterceptor(HttpBuild.httpInterceptor)
        } else {
            //log相关
            val loggingInterceptor = HttpLoggingInterceptor("okgo")
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)        //log打印级别，决定了log显示的详细程度
            loggingInterceptor.setColorLevel(Level.INFO)                               //log颜色级别，决定了log在控制台显示的颜色
            builder.addInterceptor(loggingInterceptor)                                 //添加OkGo默认debug日志
        }
        //超时时间设置，默认60秒
        builder.connectTimeout(HttpBuild.timeOut.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(HttpBuild.timeOut.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(HttpBuild.timeOut.toLong(), TimeUnit.SECONDS)
        var cookieJar: CookieJar? = null
        when (HttpBuild.cookieStore) {
            HttpBuild.CookieType.SPCookieStore -> cookieJar = CookieJarImpl(SPCookieStore(context))
            HttpBuild.CookieType.DBCookieStore -> cookieJar = CookieJarImpl(DBCookieStore(context))
            HttpBuild.CookieType.MemoryCookieStore -> cookieJar = CookieJarImpl(MemoryCookieStore())
        }
        if (cookieJar != null)
            builder.cookieJar(cookieJar)
//        //https相关设置
        val sslParams = HttpsUtils.getSslSocketFactory()
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        OkGo.getInstance().init(context)//必须调用初始化
                .setOkHttpClient(builder.build()).retryCount = 0//全局统一超时重连次数
    }
}


