package com.tony.utils.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.tony.utils.utils.LogUtil;

/**
 * 
 * @author Tony
 * @time 2019/4/12 17:09
 */
public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = ActivityLifecycleListener.class.getSimpleName();
    private static ActivityLifecycleListener sActivityLifecycleListener;
    private int refCount = 0;

    public static boolean mIsForgroud = false;

    private ActivityLifecycleListener() {

    }

    public static ActivityLifecycleListener getInstance() {
        if (null == sActivityLifecycleListener) {
            sActivityLifecycleListener = new ActivityLifecycleListener();
        }
        return sActivityLifecycleListener;
    }

    public boolean isInApp() {
        return refCount > 0;
    }


    public static Activity currentActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        refCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        mIsForgroud = true;
        LogUtil.log(LogUtil.LECEL_DEBUG,TAG,"onActivityResumed() : " + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        
        mIsForgroud = false;

    }


    @Override
    public void onActivityStopped(Activity activity) {
        refCount--;
        LogUtil.log(LogUtil.LECEL_DEBUG,TAG,"onActivityStopped() : " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // 把actvity移除Stack
        AppManager.getAppManager().removeActivity(activity);
    }
}
