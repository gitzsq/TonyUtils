package com.tony.utils.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.tony.utils.R;
 /**
  * ArcGis 地图
  * @author: Tony
  * @date: 2019/7/10 16:22
  */
public class ArcGisActivity extends AppCompatActivity {
    private MapView mapView = null;
    private ArcGISTiledMapServiceLayer arcGISTiledMapServiceLayer = null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_gis);
        mContext=ArcGisActivity.this;
        initView();
    }

     private void initView() {

         mapView = (MapView)this.findViewById(R.id.map);//设置UI和代码绑定
         String strMapUrl="http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";
         this.arcGISTiledMapServiceLayer = new ArcGISTiledMapServiceLayer(strMapUrl);
         this.mapView.addLayer(arcGISTiledMapServiceLayer);
     }
 }
