package com.tony.utils.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.tony.utils.R;
import com.tony.utils.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.functions.Action1;


/**
 * RxJava 测试界面
 *
 * @author Tony
 * @time 2019/4/8 15:19
 */
public class RxActivity extends BaseActivity {
    @BindView(R.id.rx_btn)
    Button rxBtn;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx;
    }

    @Override
    protected void initView() {
        mContext = this;
        //防止按钮在单位时间内被重复点击
        RxView.clicks(rxBtn).throttleFirst(1,TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                startTimerTask();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

//    @OnClick({R.id.rx_btn})
//    public void click(View v) {
//        switch (v.getId()) {
//            case R.id.rx_btn:
//                startTimerTask();
//                break;
//        }
//    }



    //rxjava启动一个定时任务
    private void startTimerTask() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                ToastUtil.show("定时任务启动成功！", mContext);
            }
        });
    }
}
