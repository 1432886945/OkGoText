package com.hisan.base.network.Interface

import com.lzy.okgo.model.Response

/**
 * 创建时间 : 2018/3/21
 * 创建人：yangyingqi
 * 公司：嘉善和盛网络有限公司
 * 备注：网络请求返回数据接口
 */
interface GetDataInteface {
    fun <T : Any> OnSuccessData(did: Int, data: T?)
    fun <T : Any> OnErrorData(did: Int, data: Response<T>)
}
