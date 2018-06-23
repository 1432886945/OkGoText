package com.hisan.base.network

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.util.Log

import java.io.IOException

/**
 * 创建时间 : 2017/6/8
 * 创建人：yangyingqi
 * 公司：嘉善和盛网络有限公司
 * 备注： 网络相关工具类
 */
object NetworkUtils {
    @SuppressLint("MissingPermission")
    fun isNetConnected(context: Context): Boolean {

        val isNetConnected: Boolean
        // 获得网络连接服务
        val connManager = context.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connManager.activeNetworkInfo
        if (info != null && info.isConnected) {
            // 判断当前网络是否已经连接
            return info.state == NetworkInfo.State.CONNECTED
        } else {
            isNetConnected = false
        }
        return isNetConnected

    }

    // Gps是否可用
    fun isGpsEnable(context: Context): Boolean {
        val locationManager = context
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 打开GPS设置页面
    fun openGPSSetting(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context.startActivity(intent)

        } catch (ex: ActivityNotFoundException) {

            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.

            // General settings activity
            intent.action = Settings.ACTION_SETTINGS
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
            }

        }

    }

    /**
     * @return
     * @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     */
    fun ping(): Boolean {

        var result: String? = null
        try {
            val ip = "www.baidu.com"// ping 的地址，可以换成任何一种可靠的外网
            val p = Runtime.getRuntime().exec("ping $ip")// ping网址3次 -c 3
            // -w 100
            // ping的状态
            val status = p.waitFor()
            if (status == 0) {
                result = "success"
                return true
            } else {
                result = "failed"
            }
        } catch (e: IOException) {
            result = "IOException"
        } catch (e: InterruptedException) {
            result = "InterruptedException"
        } finally {
            Log.d("----result---", "result = " + result!!)
        }
        return false
    }

    @SuppressLint("MissingPermission")
            /**
     * 验证wifi是否连接
     *
     * @param context
     * @return true-连接；false-未连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected
    }

    @SuppressLint("MissingPermission")
            /**
     * 判断是否为WIFI网络
     *
     * @param context
     * @return
     */
    fun isWifi(context: Context): Boolean {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkINfo = cm.activeNetworkInfo
        return networkINfo != null && networkINfo.type == ConnectivityManager.TYPE_WIFI
    }
}
