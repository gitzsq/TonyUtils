package com.tony.utils.business.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.tony.utils.app.AppConstants;
import com.tony.utils.utils.LogUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retrofit的初始化
 * @author Tony
 * @time 2019/4/3 21:48
 */
public class RetrofitHelper {

    private Context mContext;


    GsonConverterFactory factory =GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance =null;
    private Retrofit mRetrofit = null;
    public static RetrofitHelper getInstance(Context context){
        if(instance==null){
             instance=new RetrofitHelper(context);
        }
        return instance;
    }

    public RetrofitHelper(Context context) {
        mContext=context;
        init();
    }

    private void init() {
        resetApp();
    }
    //retrofit的创建
    private void resetApp() {
        mRetrofit=new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

   //获取retrofit的实例
    public RetrofitService getServer(){

        return mRetrofit.create(RetrofitService.class);
    }
    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.log(LogUtil.LECEL_DEBUG,"Jonas","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

}
