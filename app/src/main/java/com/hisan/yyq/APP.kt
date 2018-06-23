package com.hisan.yyq

import android.app.Application
import android.util.Log
import com.hisan.base.network.HttpBuild
import com.hisan.base.network.KUtilLibs
import com.king.thread.nevercrash.NeverCrash

/**
 * Created by yyq on 2018/6/22.
 */
open class APP : Application() {

    override fun onCreate() {
        super.onCreate()
        KUtilLibs.init(this, HttpBuild.Build(null, 10, HttpBuild.CookieType.MemoryCookieStore))
        NeverCrash.init { _, e -> Log.v("xxx","系统异常：" + e.message) }
    }
}