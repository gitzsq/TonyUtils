package com.tony.utils.ui.activity;



import com.tony.utils.R;


/**
  * 高德地图
  * @author: Tony
  * @date: 2019/5/14 10:02
  */
public class AmapActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_amap;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
     //获取当前缩放等级(未开启定位图层,在fragment中oncreatview生命周期中无法获取到,可以在Onresume中获取)
//     public float getZoom(){
//         float zoom = aMap.getCameraPosition().zoom;
//         return zoom;
//     }
//     //获取地图可视化区域的四个顶点的经纬度
//     public List<LatLng> getRange(){
//         List<LatLng> latLngs=new ArrayList<>();
//         VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion();
//         LatLng farLeft = visibleRegion.farLeft;     //可视区域的左上角。
//         LatLng farRight = visibleRegion.farRight;   //可视区域的右上角。
//         LatLng nearLeft = visibleRegion.nearLeft;   //可视区域的左下角。
//         LatLng nearRight = visibleRegion.nearRight; //可视区域的右下角。
//         latLngs.add(farLeft);
//         latLngs.add(farRight);
//         latLngs.add(nearLeft);
//         latLngs.add(nearRight);
//         return  latLngs;
//     }
//     //可视化区域西南和东北角的两个坐标
//     public List<LatLng>  getBounds(){
//         List<LatLng> latLngs=new ArrayList<>();
//         VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion();
//         LatLngBounds latLngBounds = visibleRegion.latLngBounds;   //由可视区域的四个顶点形成的经纬度范围
//         LatLng southwest = latLngBounds.southwest;      //西南角坐标
//         LatLng northeast = latLngBounds.northeast;      //东北角坐标
//         latLngs.add(southwest);
//         latLngs.add(northeast);
//         return latLngs;
//     }
//     //返回一个从地图位置转换来的屏幕位置--经纬度坐标转屏幕坐标
//     public int[] getScreenXY(LatLng latLng){
//         int [] xy=new int[2];
//         Point point = aMap.getProjection().toScreenLocation(latLng);
//         int x=point.x;    //x轴坐标
//         int y=point.y;    //y轴坐标
//         xy[0]=x;
//         xy[1]=y;
//         return xy;
//     }
}
