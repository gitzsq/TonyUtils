package com.tony.utils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.tony.utils.app.AppConstants;
import com.tony.utils.business.entity.EventMsg;

import org.greenrobot.eventbus.EventBus;

/**
 * 百度地图工具类
 * @author: Tony
 * @date: 2019/5/21 16:26
 */
public class BdMapUtil {
   /**
    * 初始化定位参数配置
    */

   public void initLocationOption(Context mContext) {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
       LocationClient locationClient = new LocationClient(mContext.getApplicationContext());
//声明LocationClient类实例并配置定位参数
       LocationClientOption locationOption = new LocationClientOption();
       MyLocationListener myLocationListener = new MyLocationListener();
//注册监听函数
       locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
       locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
       locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
       locationOption.setScanSpan(0);
//可选，设置是否需要地址信息，默认不需要
       locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
       locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
       locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
       locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
       locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
       locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
       locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
       locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
       locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
       locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
       locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
       locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
       locationClient.setLocOption(locationOption);
//开始定位
       locationClient.start();
   }

   /**
    * 实现定位回调
    */
   public class MyLocationListener extends BDAbstractLocationListener {
       @Override
       public void onReceiveLocation(BDLocation location) {
           //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
           //以下只列举部分获取经纬度相关（常用）的结果信息
           //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

           //获取纬度信息
           double latitude = location.getLatitude();
           //获取经度信息
           double longitude = location.getLongitude();
           //获取定位精度，默认值为0.0f
           float radius = location.getRadius();
           //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
           String coorType = location.getCoorType();
           //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
           int errorCode = location.getLocType();
           Log.d("Tony 当前位置:", latitude + "," + longitude);
           EventBus.getDefault().post(new EventMsg(AppConstants.evMsgType.getLatlon,latitude ,longitude));
       }
   }
   //获取要添加的地图图层
   public GroundOverlayOptions getGolayOptions(double xmin, double ymin, double xmax, double ymax, Bitmap bm) {
       Log.d("Tony 范围",xmin+","+ymin+","+xmax+","+ymax);
       LatLng southwest = new LatLng(ymin, xmin);//西南
       LatLng northeast = new LatLng(ymax, xmax);//东北
       LatLngBounds bounds = new LatLngBounds.Builder().include(southwest)
               .include(northeast).build();//得到一个地理范围对象
       BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromBitmap(bm);
//                .fromResource(R.drawable.sss);
       GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
       groundOverlayOptions.image(bitmap2);//显示的图片
       groundOverlayOptions.positionFromBounds(bounds);//显示的位置
//        groundOverlayOptions.transparency(0.7f);//显示的透明度
       return groundOverlayOptions;
   }
    //地图单击事件
    public static BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         * @param point 点击的地理坐标
         */
        public void onMapClick(LatLng point){
            double latitude=point.latitude;
            double longitude=point.longitude;
            Log.d("Tony 点击地图：", latitude + "," + longitude);
            EventBus.getDefault().post(new EventMsg(AppConstants.evMsgType.clickMap,latitude ,longitude));
        }
        /**
         * 地图内 Poi 单击事件回调函数
         * @param poi 点击的 poi 信息
         */
        public boolean onMapPoiClick(MapPoi poi){
            return false;
        }
    };



}
