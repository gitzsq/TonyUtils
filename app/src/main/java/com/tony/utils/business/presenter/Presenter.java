package com.tony.utils.business.presenter;

import android.content.Intent;

import com.tony.utils.business.iview.View;


/**
 * 基础Presenter
 * @author Tony
 * @time 2019/4/3 23:05
 */
public interface Presenter {
    
    void onCreate();

    void onStart();//暂时没用到

    void onStop();

    void pause();//暂时没用到

    void attachView(View view);

    void attachIncomingIntent(Intent intent);//暂时没用到
}
