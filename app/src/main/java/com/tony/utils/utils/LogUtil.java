package com.tony.utils.utils;

import android.util.Log;

/**
 * App Log管理类
 * @author Tony
 * @time 2019/4/4 11:39
 */
public class LogUtil {

    //是否是调试模式
    public static final boolean DEBUG = true;

    //log等级
    public static final int LECEL_INFO = 0;
    public static final int LECEL_DEBUG = 1;
    public static final int LECEL_ERROR = 2;

    public static void log(int level,String tag,String content){
        if(!DEBUG){
            return;
        }
        switch (level){
            case LECEL_INFO:
                Log.i(tag,content);
                break;
            case LECEL_DEBUG:
                Log.d(tag,content);
                break;
            case LECEL_ERROR:
                Log.e(tag,content);
                break;
        }

    }

}
