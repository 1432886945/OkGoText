package com.hisan.base.network.callback

import android.app.Activity
import android.widget.Toast
import com.hisan.base.network.NetworkUtils
import com.lzy.okgo.request.base.Request

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：对于网络请求是否需要弹出进度对话框
 * 修订历史：
 * ================================================
 */
open class DialogCallback<T>(private val activity: Activity) : JsonCallback<T>() {

    override fun onStart(request: Request<T, out Request<*, *>>?) {
        if (!NetworkUtils.isNetConnected(activity)) {
            Toast.makeText(activity, "亲，你断网了啊！", Toast.LENGTH_SHORT).show()
            // 一定要调用这个方法才会生效
            onFinish()
            return
        }
//        val token = SPUtils.getInstance().getString("token")
//        if (token != null && token != "") {
//            request!!.headers.put("token", token)
//        }

    }

    override fun onFinish() {}

}
