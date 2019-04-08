package com.tony.utils.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tony.utils.R
import com.tony.utils.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG= AppCompatActivity::class.java!!.getSimpleName()+" Jonas :"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkrequest_btn.setOnClickListener(this)

    }

    //按钮点击事件
    override fun onClick(v: View?) {
        when(v?.id){
             R.id.networkrequest_btn ->{
                 val intent = Intent()
                 intent.setClass(this, LoginActivity::class.java)
                 startActivity(intent)
              }

        }
    }
}
