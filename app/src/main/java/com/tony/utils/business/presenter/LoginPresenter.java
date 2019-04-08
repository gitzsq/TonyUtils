package com.tony.utils.business.presenter;

import android.content.Context;
import android.content.Intent;

import com.tony.utils.business.entity.LoginData;
import com.tony.utils.business.iview.ILoginView;
import com.tony.utils.business.iview.View;
import com.tony.utils.business.manager.DataManager;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * 登录presenter
 * @author Tony
 * @time 2019/4/4 9:45
 */
public class  LoginPresenter implements Presenter {
    private DataManager dataManager;
    //CompositeSubscription是用来存放RxJava中的订阅关系的
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ILoginView mILoginView;
    private LoginData mLoginData;

    public LoginPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        dataManager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        //请求完数据要及时清掉rxjava订阅关系,防止发生内存泄漏
       if(mCompositeSubscription.hasSubscriptions()){
           mCompositeSubscription.unsubscribe();
       }
    }

    @Override
    public void pause() {

    }


    public void attachView(View view) {
        mILoginView = (ILoginView)view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void login(String userName,String passWord,String videoIP,String token){
        mCompositeSubscription.add(dataManager.login(userName,passWord,videoIP,token)
                .subscribeOn(Schedulers.io())//定义请求事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//定义事件在主线程
                .subscribe(new Observer<LoginData>() {//通过subscribe使观察者订阅
                    @Override
                    public void onCompleted() {
                         //请求结束后会调用onCompleted
                        if(mLoginData != null){
                            mILoginView.onSuccess(mLoginData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mILoginView.onError("登录失败！");
                    }

                    @Override
                    public void onNext(LoginData loginData) {
                        mLoginData = loginData;
                    }
                })
        );
    }
}
