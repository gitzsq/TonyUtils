package com.tony.utils.business.manager;

import android.content.Context;

import com.tony.utils.business.entity.LoginData;

import rx.Observable;


/**
 * 网络请求管理类
 * @author Tony
 * @time 2019/4/3 22:52
 */
public class DataManager {

     private RetrofitService mRetrofitService;
     //构造方法中得到RetrofitService 的实例
     public DataManager(Context context){
         this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
     }

     //登录请求
    public Observable<LoginData> login(String userName, String passWord, String videoIP, String token ){

         return mRetrofitService.login(userName,passWord,videoIP,token);

    }


}
