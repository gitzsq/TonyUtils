package com.tony.utils.ui.activity;



import com.tony.utils.R;
import com.tony.utils.customview.BattaryView;

import butterknife.BindView;

public class BattaryActivity extends BaseActivity {

    @BindView(R.id.battar_pro)
    BattaryView battarPro;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_battary;
    }

    @Override
    protected void initView() {
        battarPro.setBattaryPercent(80);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

}
