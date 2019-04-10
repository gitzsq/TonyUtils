package com.tony.utils.app;

import android.app.Application;
import android.content.Context;

import com.tony.utils.utils.pic.pictools.ImageItem;

import java.util.List;

public class TonyApplication extends Application {

    static TonyApplication app;
    private Context appContext;

    //选择照片的list
    private static List<ImageItem> imageList = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
        app=this;
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
}
