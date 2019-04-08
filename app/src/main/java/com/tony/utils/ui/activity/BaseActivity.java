package com.tony.utils.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 封装BaseActivity 便于统一管理事件的绑定与解绑
 * @author Tony
 * @time 2019/4/8 16:45
 */
public abstract class BaseActivity extends AppCompatActivity{

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder= ButterKnife.bind(this);//绑定ButterKnife
    }

    protected abstract int getLayoutId();
}
