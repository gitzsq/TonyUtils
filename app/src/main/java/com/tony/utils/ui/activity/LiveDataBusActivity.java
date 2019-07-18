package com.tony.utils.ui.activity;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tony.utils.R;
import com.tony.utils.utils.LiveDataBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LiveDataBusActivity extends BaseActivity implements LifecycleOwner {


    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.message_txt)
    TextView messageTxt;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_data_bus;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        LiveDataBus.get()
                .with("key_test", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        messageTxt.setText("接收到的消息："+s);
                    }
                });
    }

    @OnClick({R.id.send_btn})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                goMain();
              //发送消息
//                LiveDataBus.get().with("key_test").setValue("这是一条测试消息");

                break;
        }
    }


    public void goMain(){
        ComponentName componentName = new ComponentName("com.diting.police","com.lbs.lbspos.MainMap");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("phonenumber", "15769225572");
        intent.putExtras(bundle);
        intent.setComponent(componentName);
        startActivity(intent);
    }

}
