
package com.hisan.base.network.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import okhttp3.ResponseBody

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
abstract class JsonCallback<T> : AbsCallback<T> {
    var type: Type? = null
    var clazz: Class<kotlin.Any>? = null
    var mData: T? = null
    var mResponse: T? = null

    constructor(type: Type) {
        this.type = type
    }

    constructor() {
        this.clazz = clazz
    }


    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     * @param response 需要转换的对象
     * @return 转换后的结果
     * @throws Exception 转换过程发生的异常
     */
    @Throws(Throwable::class)
    override fun convertResponse(response: okhttp3.Response): T? {
        val body = response.body() ?: return null
        mData = null
        val gson = Gson()
        val jsonReader = JsonReader(body.charStream())
        if (type != null) {
            mData = gson.fromJson<T>(jsonReader, type)
        } else if (clazz != null) {
            mData = gson.fromJson(jsonReader, clazz)
        } else {
            val genType = javaClass.genericSuperclass
            val type = (genType as ParameterizedType).actualTypeArguments[0]
            mData = gson.fromJson<T>(jsonReader, type)
        }
        return mData
    }

    override fun onError(response: com.lzy.okgo.model.Response<T>) {
        super.onError(response)
        val exception = response.exception
        if (response.code() == 500) {
            try {
                val doc = Jsoup.parse(response.rawResponse.body()!!.string())
                val elements = doc.select("div.exception")
                Log.v("xxx",elements.text())
                Log.v("xxx",elements.select("h1").text())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        exception?.printStackTrace()
        mResponse = response.rawResponse as T
        if (exception is UnknownHostException || exception is ConnectException) {
            Log.d("xxx","网络连接失败，请检查网络")
        } else if (exception is SocketTimeoutException) {
            Log.d("xxx","网络请求超时")
        } else if (exception is HttpException) {
            Log.d("xxx","网络端响应码404或者505了，请联系服务器开发人员")
        } else if (exception is StorageException) {
            Log.d("xxx","SD卡不存在或者没有权限")
        } else if (exception is IllegalStateException) {
            val message = exception.message
            Log.d("xxx",message)
        }
    }

    //子类不需要去设置response.body()了，只需要拿到mResponse直接用就好了
    override fun onSuccess(response: com.lzy.okgo.model.Response<T>) {
        mResponse = response.body()
    }

}
