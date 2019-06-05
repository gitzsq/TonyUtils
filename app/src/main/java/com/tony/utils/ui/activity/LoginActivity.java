package com.tony.utils.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.polites.android.GestureImageView;
import com.tony.utils.R;
import com.tony.utils.app.AppConstants;
import com.tony.utils.business.entity.LoginData;
import com.tony.utils.business.iview.ILoginView;
import com.tony.utils.business.presenter.LoginPresenter;
import com.tony.utils.customview.GlideCircleTransform;
import com.tony.utils.service.LogObserverService;
import com.tony.utils.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页
 *
 * @author Tony
 * @time 2019/4/3 21:37
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.img_view)
    GestureImageView imgView;
    private LoginPresenter mLoginPresenter = new LoginPresenter(this);
    private Context mContext;
    private String TAG = "LogObserverActivity";
    public static String LOG_ACTION = "com.example.admin.logobserver.LOG_ACTION";
    private TextView logContent = null;
    private Button start = null;
    private Intent logObserverIntent = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mContext = this;
        mLoginPresenter.onCreate();
        mLoginPresenter.attachView(mILoginView);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.login_btn})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //启动Service处理任务
//                startLogObserverService();
//                UserInfo userInfo =new UserInfo("tony","男",1);
//                userInfo.save();
//                //网络请求
                mLoginPresenter.login("mobile", "123456", "222.66.82.2", AppConstants.TOKEN);
                break;
        }
    }


    private ILoginView mILoginView = new ILoginView() {
        //登录成功回调
        @Override
        public void onSuccess(LoginData mLoginData) {
            loginBtn.setText(mLoginData.getMsg());
            //加载图片
            Glide.with(mContext).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideCircleTransform(mContext)).into(imgView);
        }

        //登录失败回调
        @Override
        public void onError(String result) {
            ToastUtil.show(result, LoginActivity.this);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onStop();
    }

    private void startLogObserverService() {
        logObserverIntent = new Intent(this, LogObserverService.class);
        startService(logObserverIntent); }
      //写数据
     public void writeToSdCard(String string){
            //1、判断sd卡是否可用
         if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
             //sd卡可用 //2、获取sd卡路径
             File sdFile=Environment.getExternalStorageDirectory();
             File path=new File(sdFile,"360/a.txt");
             //sd卡下面的a.txt文件  参数 前面 是目录 后面是文件
             try { FileOutputStream fileOutputStream=new FileOutputStream(path,true);
                 fileOutputStream.write(string.getBytes());
             } catch (Exception e) { e.printStackTrace();
             }
         }
        }
//        protected void onDestroy() {
//            super.onDestroy();
//            if (logObserverIntent!=null){
//                stopService(logObserverIntent);
//            }
//            if (mLogBroadcastReceiver!=null){
//                unregisterReceiver(mLogBroadcastReceiver);
//            }
//        }

}
