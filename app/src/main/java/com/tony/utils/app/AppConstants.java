package com.tony.utils.app;

/**
 * App常量配置
 * @author Tony
 * @time 2019/4/3 21:43
 */
public class AppConstants {
    
    //网络请求baseUrl
    public static final String BASE_URL = "http://222.66.82.4/shm/";
    //android 请求token
    public static final String TOKEN = "4CE19CA8FCD150A4";

    //可选图片的最大值
    public static final int PIC_MAX_NUM =2;

    public  interface evMsgType{
        int getLatlon=1;//传输经纬度
        int clickMap=2;//地图点击事件
        int isRefreshHC=3;//是否刷新航次列表
    }
}
