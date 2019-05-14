package com.tony.utils.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.tony.utils.utils.CrashHandler;
import com.tony.utils.utils.LogUtil;
import com.tony.utils.utils.pic.pictools.ImageItem;

import java.util.List;

public class TonyApplication extends Application {

    static TonyApplication app;
    private Context appContext;
    private ActivityLifecycleListener mActivityLifecycleListener;

    //选择照片的list
    private static List<ImageItem> imageList = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log(LogUtil.LECEL_DEBUG,"Tony","TonyApplication onCreate()");
        appContext=getApplicationContext();
        app=this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(appContext);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        mActivityLifecycleListener=ActivityLifecycleListener.getInstance();
        registerActivityLifecycleCallbacks(mActivityLifecycleListener);
        //app异常捕捉
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    public static TonyApplication getApp(){
        return app;
    }

    public static List<ImageItem> getImageList() {
        return imageList;
    }

    public static void setImageList(List<ImageItem> imageList) {
        TonyApplication.imageList = imageList;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(mActivityLifecycleListener);
    }
}
