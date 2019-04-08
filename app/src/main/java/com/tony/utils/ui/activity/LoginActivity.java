package com.tony.utils.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.polites.android.GestureImageView;
import com.tony.utils.R;
import com.tony.utils.app.AppConstants;
import com.tony.utils.business.entity.LoginData;
import com.tony.utils.business.iview.ILoginView;
import com.tony.utils.business.presenter.LoginPresenter;
import com.tony.utils.utils.GlideCircleTransform;
import com.tony.utils.utils.GlideRoundTransform;
import com.tony.utils.utils.ToasUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 登录页
 *
 * @author Tony
 * @time 2019/4/3 21:37
 */
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.img_view)
    GestureImageView imgView;
    private LoginPresenter mLoginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.onCreate();
        mLoginPresenter.attachView(mILoginView);
    }

    @OnClick(R.id.login_btn)
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //网络请求
                mLoginPresenter.login("mobile", "123456", "222.66.82.2", AppConstants.TOKEN);
                //加载图片
                Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideCircleTransform(this)).into(imgView);
                break;
        }
    }

    private ILoginView mILoginView = new ILoginView() {
        //登录成功回调
        @Override
        public void onSuccess(LoginData mLoginData) {
            loginBtn.setText(mLoginData.getMsg());
        }

        //登录失败回调
        @Override
        public void onError(String result) {
            ToasUtil.show(result, LoginActivity.this);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onStop();
    }
}
