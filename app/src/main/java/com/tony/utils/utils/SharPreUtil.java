package com.tony.utils.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tony.utils.app.TonyApplication;


/**
 * SharedPreferences 管理类
 * @author Tony
 * @time 2019/4/4 13:39
 */
public class SharPreUtil {
    Context mContext;
    SharedPreferences sharedPreferences;
    private static volatile SharPreUtil mSharPreUtil;
    private static final Object mLock = new Object();
    SharedPreferences.Editor editor;

    private SharPreUtil(String fileName){
        mContext= TonyApplication.getApp().getApplicationContext();
        sharedPreferences=mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }

    public  static SharPreUtil getInstance(String fileNmae){
        synchronized (mLock) {
            if (mSharPreUtil == null) {
                mSharPreUtil=new SharPreUtil(fileNmae);
            }
        }
        return mSharPreUtil;
    }

    //保存
    public void putStringValue(String key,String value){
        editor=sharedPreferences.edit();
        editor.putString(key,value).commit();
    }
    public void putIntValue(String key,int value){
        editor=sharedPreferences.edit();
        editor.putInt(key,value).commit();
    }
    public void putLongValue(String key,long value){
        editor=sharedPreferences.edit();
        editor.putLong(key,value).commit();
    }
    public void putBooleanValue(String key,boolean value){
        editor=sharedPreferences.edit();
        editor.putBoolean(key,value).commit();
    }

    //读取
    public String getStringValue(String key){
        return sharedPreferences.getString(key,null);
    }
    public int getIntValue(String key){
        return sharedPreferences.getInt(key,0);
    }
    public long getLoingValue(String key){
        return sharedPreferences.getLong(key,0);
    }
    public boolean getBooleanValue(String key){
        return sharedPreferences.getBoolean(key,false);
    }

}
