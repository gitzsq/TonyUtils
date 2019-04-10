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

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder= ButterKnife.bind(this);//绑定ButterKnife
        initView();
        initData();
        initListener();
    }
    protected abstract int getLayoutId();
    protected abstract  void initView();
    protected abstract  void initData();
    protected abstract  void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
