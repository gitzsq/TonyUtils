package com.tony.utils.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.tony.utils.ui.activity.LoginActivity;
import com.tony.utils.ui.activity.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 抓log的服务
 */
public class LogObserverService extends Service implements Runnable {
    private String TAG = "LogObserverService";
    private boolean isObserverLog = false;
    private StringBuffer logContent = null;
    private Bundle mBundle = null;
    private Intent mIntent = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.v("TrafficService","startCommand");
//START_STICKY是service被kill掉后自动重写创建
        flags =  START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        mIntent = new Intent();
        mBundle = new Bundle();
        logContent = new StringBuffer();
        startLogObserver();
    }

    /**
     * 开启检测日志
     */
    public void startLogObserver() {
        Log.i(TAG,"startObserverLog");
        isObserverLog = true;
        Thread mTherad = new Thread(this);
        mTherad.start();
    }

    /**
     * 关闭检测日志
     */
    public void stopLogObserver() {
        isObserverLog = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLogObserver();
    }

    /**
     * 发送log内容
     * @param logContent
     */
    private void sendLogContent(String logContent){
        mBundle.putString("log",logContent);
        mIntent.putExtras(mBundle);
        mIntent.setAction(LoginActivity.LOG_ACTION);
        sendBroadcast(mIntent);
    }


    @Override
    public void run() {
        Process pro = null;
        BufferedReader bufferedReader = null;
        try {
            String[] running=new String[]{ "logcat","|find","cocos2d-x debug" };
//          pro = Runtime.getRuntime().exec("logcat");
            pro = Runtime.getRuntime().exec(running);
//          Runtime.getRuntime().exec("logcat -c").waitFor();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    pro.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //筛选需要的字串
        String strFilter="tony";
        String line = null;
        try {
            System.out.println(bufferedReader.readLine());
            while ((line =bufferedReader.readLine()) != null) {

                if (line.indexOf(strFilter) >=0) {
                    //读出每行log信息
                    System.out.println(line);
                    logContent.delete(0,logContent.length());
                    logContent.append(line);
                    logContent.append("\n");

                    Thread.yield();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
