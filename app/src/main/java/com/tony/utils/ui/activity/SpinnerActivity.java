package com.tony.utils.ui.activity;


import android.os.Build;
import android.os.Bundle;
import android.widget.Spinner;

import com.tony.utils.R;
import com.tony.utils.ui.adapter.MyStringSpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
 /**
  * 下拉控件Spinner
  * @author: Tony
  * @date: 2019/5/14 10:05
  */
public class SpinnerActivity extends BaseActivity {

    @BindView(R.id.spn_shiptype)
    Spinner spnShiptype;
    private MyStringSpinnerAdapter spntypeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spinner;
    }

    @Override
    protected void initView() {
        initSpinner();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private void initSpinner() {
        String[] typeStringArray =this.getResources().getStringArray(R.array.spinner);
        spntypeAdapter = new MyStringSpinnerAdapter(this, typeStringArray);
        spnShiptype.setAdapter(spntypeAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spnShiptype.setDropDownVerticalOffset(60);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
