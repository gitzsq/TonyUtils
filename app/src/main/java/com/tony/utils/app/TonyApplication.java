package com.tony.utils.app;

import android.app.Application;
import android.content.Context;

public class TonyApplication extends Application {

    static TonyApplication app;
    private Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
        app=this;
    }

    public static TonyApplication getApp(){
        return app;
    }
}
