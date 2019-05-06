package com.tony.utils.ui.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import com.tony.utils.R
import com.tony.utils.utils.LiveDataBus
import com.tony.utils.utils.UIUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkrequest_btn.setOnClickListener(this)
        rx_btn.setOnClickListener(this)
        pic_btn.setOnClickListener(this)
        radio_btn.setOnClickListener(this)
        battary_btn.setOnClickListener(this)
        dialog_btn.setOnClickListener(this)
        okhttp_btn.setOnClickListener(this)
        map_btn.setOnClickListener(this)
        livedatabus_btn.setOnClickListener(this)

    }

    //按钮点击事件
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.networkrequest_btn -> {//网络请求 框架封装
                val intent = Intent()
                intent.setClass(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.rx_btn -> {//rxjava测试
                val intent = Intent()
                intent.setClass(this, RxActivity::class.java)
                startActivity(intent)
            }
            R.id.pic_btn -> {//仿微信多张照片选择
                val intent = Intent()
                intent.setClass(this, PicActivity::class.java)
                startActivity(intent)
            }
            R.id.radio_btn -> {//动态添加radiobutton
                val intent = Intent()
                intent.setClass(this, RadioActivity::class.java)
                startActivity(intent)
            }
            R.id.battary_btn -> {//电池自定义view
                val intent = Intent()
                intent.setClass(this, BattaryActivity::class.java)
                startActivity(intent)
            }

            R.id.dialog_btn -> {//弹窗
               UIUtil.showProgressDialog(this,"正在加载")
                object : Thread() {
                    override fun run() {
                        super.run()
                        Thread.sleep(3000)//休眠3秒
                        UIUtil.cancelProgressDialog()
                    }
                }.start()
            }
            R.id.map_btn -> {//百度地图相关
                val intent = Intent()
                intent.setClass(this,BaiduMapActivity::class.java)
                startActivity(intent)
            }
            R.id.livedatabus_btn -> {//livedatabus测试
                val intent = Intent()
                intent.setClass(this,LiveDataBusActivity::class.java)
                startActivity(intent)
            }
        }
    }


}
