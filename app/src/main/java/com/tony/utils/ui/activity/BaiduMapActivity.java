package com.tony.utils.ui.activity;

import android.graphics.Color;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Gradient;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.tony.utils.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class BaiduMapActivity extends BaseActivity {


    @BindView(R.id.bmapview)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private Gradient gradient;
    private HeatMap heatMap;

    private List<LatLng> latLngList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initView() {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //开启热力图
        mBaiduMap.setBaiduHeatMapEnabled(true);
        //设置渐变颜色值
        int[] DEFAULT_GRADIENT_COLORS = {Color.rgb(102, 225, 0), Color.rgb(255, 0, 0)};
//设置渐变颜色起始值
        float[] DEFAULT_GRADIENT_START_POINTS = {0.2f, 1f};
//构造颜色渐变对象
        Gradient gradient = new Gradient(DEFAULT_GRADIENT_COLORS, DEFAULT_GRADIENT_START_POINTS);
        //以下数据为随机生成地理位置点，开发者根据自己的实际业务，传入自有位置数据即可
        List<LatLng> randomList = new ArrayList<LatLng>();

        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            // 116.220000,39.780000 116.570000,40.150000
            int rlat = r.nextInt(370000);
            int rlng = r.nextInt(370000);
            int lat = 39780000 + rlat;
            int lng = 116220000 + rlng;
            LatLng ll = new LatLng(lat / 1E6, lng / 1E6);
            randomList.add(ll);
        }

        //构造HeatMap
//在大量热力图数据情况下，build过程相对较慢，建议放在新建线程实现
        HeatMap mCustomHeatMap = new HeatMap.Builder()
                .data(randomList)
                .gradient(gradient)
                .build();

//在地图上添加自定义热力图
        mBaiduMap.addHeatMap(mCustomHeatMap);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
