package com.tony.utils.business.entity;


/**
  * EventBus消息实体
  * @author: Tony
  * @date: 2019/5/21 16:01
  */
public class EventMsg {
     private int msgType;
     //纬度信息
     double latitude =0.0 ;
     //经度信息
     double longitude =0.0;

    private boolean isRefesh=false;

     public EventMsg(int msgType, double latitude, double longitude) {
         this.msgType = msgType;
         this.latitude = latitude;
         this.longitude = longitude;
     }

    public EventMsg(int msgType, boolean isRefesh) {
        this.msgType = msgType;
        this.isRefesh = isRefesh;
    }

    public boolean isRefesh() {
        return isRefesh;
    }

    public void setRefesh(boolean refesh) {
        isRefesh = refesh;
    }

    public int getMsgType() {
         return msgType;
     }

     public void setMsgType(int msgType) {
         this.msgType = msgType;
     }

     public double getLatitude() {
         return latitude;
     }

     public void setLatitude(double latitude) {
         this.latitude = latitude;
     }

     public double getLongitude() {
         return longitude;
     }

     public void setLongitude(double longitude) {
         this.longitude = longitude;
     }
 }
