package com.hisan.yyq.network;

import android.app.Activity;


import com.hisan.base.network.Interface.GetDataInteface;
import com.hisan.base.network.callback.DialogCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;


/**
 * 创建时间 : 2017/12/7
 * 创建人：yangyingqi
 * 公司：嘉善和盛网络有限公司
 * 备注：网络请求封装工具类
 */
public class OkGoUtlis{
    private static OkGoUtlis okGoUtlis;

    public static OkGoUtlis getInstance() {
        if (okGoUtlis == null) {
            synchronized (OkGoUtlis.class) {
                if (okGoUtlis == null) {
                    okGoUtlis = new OkGoUtlis();
                }
            }
        }
        return okGoUtlis;
    }

    /**
     *
     * @param getDataInteface 当前数据返回接口
     * @param did 接口返回参数id
     * @param type 请求方式标识
     * @param url 链接
     * @param httpParams 请求参数
     * @param activity 当前上下文
     */
    public void getmData(final GetDataInteface getDataInteface, final int did, int type, String url, HttpParams httpParams, final Activity activity){
        switch (type){
            case 0:
                OkGo.get(url)
                        .tag(activity)
                        .params(httpParams)
                        .execute(new DialogCallback<Object>(activity){
                            @Override
                            public void onSuccess(Response<Object> response) {
                                super.onSuccess(response);
                                if (isOk(response)){
                                    getDataInteface.OnSuccessData(did,response.body());
                                }
                                else getDataInteface.OnErrorData(did,response);
                            }
                            @Override
                            public void onError(Response<Object> response) {
                                super.onError(response);
                                getDataInteface.OnErrorData(did,response);
                            }
                        });
                return;
            //上传多张图片:  httpParams.putFileParams();
            //上传多个参数:   httpParams.putUrlParams();
            case 1:
                OkGo.post(url)
                        .tag(activity)
                        .params(httpParams)
                        .execute(new DialogCallback<Object>(activity){
                            @Override
                            public void onSuccess(Response<Object> response) {
                                super.onSuccess(response);
                                if (isOk(response)){
                                    getDataInteface.OnSuccessData(did,response.body());
                                }
                                else getDataInteface.OnErrorData(did,response);
                            }
                            @Override
                            public void onError(Response<Object> response) {
                                super.onError(response);
                                getDataInteface.OnErrorData(did,response);
                            }
                        });
                return;
            case 2:
                OkGo.delete(url)
                        .tag(activity)
                        .params(httpParams)
                        .execute(new DialogCallback<Object>(activity){
                            @Override
                            public void onSuccess(Response<Object> response) {
                                super.onSuccess(response);
                                if (isOk(response)){
                                    getDataInteface.OnSuccessData(did,response.body());
                                }
                                else getDataInteface.OnErrorData(did,response);
                            }
                            @Override
                            public void onError(Response<Object> response) {
                                super.onError(response);
                                getDataInteface.OnErrorData(did,response);
                            }
                        });
                return;
            case 3:
                OkGo.put(url)
                        .tag(activity)
                        .params(httpParams)
                        .execute(new DialogCallback<Object>(activity){
                            @Override
                            public void onSuccess(Response<Object> response) {
                                super.onSuccess(response);
                                if (isOk(response)){
                                    getDataInteface.OnSuccessData(did,response.body());
                                }
                                else getDataInteface.OnErrorData(did,response);
                            }
                            @Override
                            public void onError(Response<Object> response) {
                                super.onError(response);
                                getDataInteface.OnErrorData(did,response);
                            }
                        });
                return;
        }
    }
    public boolean isOk(Response response) {
        boolean isok = true;
        return isok ? response.code() >= 200 && response.code() < 300 : response.code() > 300;
    }
}
