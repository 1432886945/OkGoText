package com.hisan.yyq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.hisan.base.network.Interface.GetDataInteface
import com.hisan.yyq.network.OkGoUtlis
import com.lzy.okgo.model.Response

class MainActivity : AppCompatActivity(), GetDataInteface{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var text=findViewById<TextView>(R.id.text)
        text.setOnClickListener({
            OkGoUtlis.getInstance()!!.getmData(this, 0, 0, "https://api.hk.hisan-web.com/v1/juhe/test", null, this)
        })

    }
    override fun <T : Any> OnSuccessData(did: Int, data: T?) {
        Log.v("yyq",data.toString())
    }

    override fun <T : Any> OnErrorData(did: Int, data: Response<T>) {
        Log.v("yyq",data.toString())
    }

}
