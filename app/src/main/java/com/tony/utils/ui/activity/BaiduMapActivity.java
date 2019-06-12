package com.tony.utils.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.tony.utils.R;
import com.tony.utils.app.AppConstants;
import com.tony.utils.business.entity.EventMsg;
import com.tony.utils.customview.CustomSurfaceView;
import com.tony.utils.customview.DragView;
import com.tony.utils.utils.BdMapUtil;
import com.tony.utils.utils.CoordinateUtil;
import com.tony.utils.utils.ImgUtil;
import com.tony.utils.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BaiduMapActivity extends BaseActivity implements ImgUtil.GlideLoadBitmapCallback {

    @BindView(R.id.bmapview)
    MapView bmapview;
    @BindView(R.id.add_yaosu)
    Button addYaosu;
    @BindView(R.id.clear_btn)
    Button clearBtn;
    @BindView(R.id.update_zoom)
    Button updateZoom;
    @BindView(R.id.update_yaosutype)
    Button updateYaosutype;
    @BindView(R.id.range_et)
    EditText rangeEt;
    private View view;
    Unbinder unbinder;
    public Context mContext;
    private BaiduMap mBaiduMap;
    private MapStatusUpdate mMapStatusUpdate;
    private Marker mMarker;
    private double lat_click = 0.0;
    private double lon_click = 0.0;
    double range = 20.1;
    private String imgUrl = "http://47.100.49.112:8088/fishser/onboard/map/marineElements";
    private String queryPointExtractInfoUrl = "http://47.100.49.112:8088/fishser/map/point_extract";
    private int zoom = 3;
    private String[] zoomList = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private String[] ysShowNameList = {"气压", "风场", "海浪", "表面洋流", "表温"};
    private String[] ysNameList = {"gfs", "gfs", "ww3", "shou", "shou"};
    private String[] ysTypeList = {"prmsl", "10mab", "wave", "sf", "sst"};

    private String showYsname = "气压";
    private String ysName = "gfs";
    private String ysType = "prmsl";

    private double ymin = 0.0;
    private double ymax = 0.0;
    private double xmin = 0.0;
    private double xmax = 0.0;
    private double xmin1 = 0.0;
    private double xmax1 = 0.0;
    double xmi = 0.0;
    double xma = 0.0;
    private View pointExtractDialogView;
    private AlertDialog extractPointDialog;
    private DragView mDragView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initView() {
        mContext = BaiduMapActivity.this;
        EventBus.getDefault().register(this);
        mBaiduMap = bmapview.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        updateMap(33.076157, 125.673057, 8.0f);
        mBaiduMap.setOnMapClickListener(BdMapUtil.listener);//地图添加点击事件
        mDragView = (DragView)findViewById(R.id.dragview);
        mDragView.addDragView(R.layout.my_self_view, 0,400,380,760, false,false);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    //初始化地图
    private void updateMap(double x, double y, float z) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(z);
        builder.target(new LatLng(x, y));
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @OnClick({R.id.rl_changeShipInfo, R.id.add_yaosu, R.id.clear_btn, R.id.update_zoom, R.id.update_yaosutype})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.rl_changeShipInfo:
                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.add_yaosu:
                if (lat_click == 0.0 || lon_click == 0.0) {
                    UIUtil.showToast(mContext, "请选择中心点");
                    return;
                }
                if (rangeEt.getText().toString().isEmpty()) {
                    UIUtil.showToast(mContext, "请输入范围");
                    return;
                } else {
                    range = Double.valueOf(rangeEt.getText().toString());
                }
                chekdata();
                chck();
                String url = imgUrl + "?dataname=" + ysName + "&datatype=" + ysType + "&xmin=" + xmin + "&xmax=" + xmax + "&ymin=" + ymin + "&ymax=" + ymax + "&z=" + zoom;
                Log.d("Tony", "第一次imgUrl:" + url);
                UIUtil.showProgressDialog(mContext, "正在加载..");
                new ImgUtil().getBitmap(mContext, url, this, 1);

                break;
            case R.id.update_zoom:
                new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).setTitle("选择级别").setItems(zoomList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "地图级别：" + zoomList[which], Toast.LENGTH_LONG).show();
                        updateZoom.setText("地图级别：" + zoomList[which]);
                        zoom = Integer.valueOf(zoomList[which]);
                        addYaosu.performClick();
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.update_yaosutype:
                new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).setTitle("选择要素类型").setItems(ysShowNameList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "基本要素：" + ysShowNameList[which], Toast.LENGTH_LONG).show();
                        updateYaosutype.setText("基本要素：" + ysShowNameList[which]);
                        showYsname = ysShowNameList[which];
                        ysName = ysNameList[which];
                        ysType = ysTypeList[which];
                        addYaosu.performClick();
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.clear_btn:
                lat_click = 0.0;
                lon_click = 0.0;
                clearMap();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvnt(EventMsg eventMsg) {
        switch (eventMsg.getMsgType()) {
            case AppConstants
                    .evMsgType.getLatlon:
                double lat = eventMsg.getLatitude();
                double lon = eventMsg.getLongitude();
                updateMap(lat, lon, 8.0f);
                break;
            case AppConstants
                    .evMsgType.clickMap:
                lat_click = eventMsg.getLatitude();
                lon_click = eventMsg.getLongitude();
                clearMap();
                addMarker(lat_click, lon_click);
                break;

        }
    }

    //清空地图标注
    private void clearMap() {
        xmax1 = 0.0;
        xmin1 = 0.0;
        mBaiduMap.clear();
    }

    //添加标注
    private void addMarker(double lat_click, double lon_click) {
        //创建marker
        MarkerOptions ooA = new MarkerOptions().position(new LatLng(lat_click, lon_click))
                .icon(BitmapDescriptorFactory.fromBitmap(new ImgUtil().getBitmap(mContext, R.mipmap.wzicon)));
        //添加marker
        mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
        mBaiduMap.addOverlay(ooA);
        //只有添加了海洋要素图层后，才进行查询
        AlertDialog.Builder pointExtractBulider = new AlertDialog.Builder(mContext);
        pointExtractDialogView = View.inflate(mContext, R.layout.hyysinfo, null);
        String lonStr = CoordinateUtil.decimalDegree2Dms(Math.abs(lon_click)) + (lon_click < 0 ? "W" : "E");
        String latStr = CoordinateUtil.decimalDegree2Dms(Math.abs(lat_click)) + (lat_click < 0 ? "S" : "N");
        ((TextView) pointExtractDialogView.findViewById(R.id.tv_position)).setText(lonStr + "  " + latStr);
        pointExtractBulider.setView(pointExtractDialogView);
        extractPointDialog = pointExtractBulider.create();
        extractPointDialog.show();
        //在show()之后，重新设置宽度
        extractPointDialog.getWindow().setLayout(800, LinearLayout.LayoutParams.WRAP_CONTENT);
        String queryUrl = queryPointExtractInfoUrl + "?lon=" + lon_click + "&lat=" + lat_click + "&datatype=prmsl,wave,sst,ssh,chla,water_temp,wind10,sf,ssta,ssha,water_uv";
        Log.d("Tony", "单点提取url：" + queryUrl);
    }

    //获取图片回调
    @Override
    public void getBitmapCallback(Bitmap bitmap, int type) {
        if (bitmap == null) {
            UIUtil.showToast(mContext, "未获取到图层信息");
            UIUtil.cancelProgressDialog();
            return;
        }
        Log.d("Tony 类型:", type + "");
        if (type == 2) {//第二张图片
            long bytep = new ImgUtil().getFileSizes(new ImgUtil().bitmapToFile(bitmap, "test2.jpg"));
            Log.d("Tony", "bytep=" + bytep);
            Log.d("Tony", "文件大小：" + new ImgUtil().bytes2kb(bytep));
            UIUtil.showToast(mContext, "图片2大小：" + new ImgUtil().bytes2kb(bytep));
//            chck1();
            GroundOverlayOptions groundOverlayOptions = new BdMapUtil().getGolayOptions(xmi, ymin, xma, ymax, bitmap);
            mBaiduMap.addOverlay(groundOverlayOptions);
            UIUtil.cancelProgressDialog();
        } else {

            long bytep = new ImgUtil().getFileSizes(new ImgUtil().bitmapToFile(bitmap, "test1.jpg"));
            Log.d("Tony", "bytep=" + bytep);
            Log.d("Tony", "文件大小：" + new ImgUtil().bytes2kb(bytep));
            UIUtil.showToast(mContext, "图片1大小：" + new ImgUtil().bytes2kb(bytep));
            GroundOverlayOptions groundOverlayOptions = new BdMapUtil().getGolayOptions(xmin, ymin, xmax, ymax, bitmap);
            mBaiduMap.addOverlay(groundOverlayOptions);
            if (xmax1 != 0.0 || xmin1 != 0.0) {
                chck1();
                String url = imgUrl + "?dataname=" + ysName + "&datatype=" + ysType + "&xmin=" + xmi + "&xmax=" + xma + "&ymin=" + ymin + "&ymax=" + ymax + "&z=" + zoom;
                Log.d("Tony", "第二次imgUrl:" + url);
                new ImgUtil().getBitmap(mContext, url, this, 2);
            } else {
                UIUtil.cancelProgressDialog();
            }
        }

    }

    //判断经纬度是否超过±180||±90特殊处理（分两次请求组合图片）
    private void chekdata() {
        ymin = lat_click - range;
        ymax = lat_click + range;
        xmin = lon_click - range;
        xmax = lon_click + range;
        //超过±180||±90特殊处理（分两次请求组合图片）
        ymin = ymin < -75 ? -75 : ymin;
        ymax = ymax > 75 ? 75 : ymax;
        if (xmin < -180) {
            xmin1 = 180 - (Math.abs(xmin) - 180);
            xmin = -180;
        } else {
            xmin1 = 0.0;
        }
        if (xmax > 180) {
            xmax1 = -180 + (xmax - 180);
            xmax = 180;
        } else {
            xmax1 = 0.0;
        }
        xmi = xmin1 == 0.0 ? -180 : xmin1;
        xma = xmax1 == 0.0 ? 180 : xmax1;

    }

    private void chck1() {
        double a1 = 0.0;
        if (ymax < ymin) {
            a1 = ymax;
            ymax = ymin;
            ymin = a1;
        }
        double b1 = 0.0;
        if (xma < xmi) {
            b1 = xma;
            xma = xmi;
            xmi = b1;
        }
    }

    private void chck() {
        double a = 0.0;
        if (ymax < ymin) {
            a = ymax;
            ymax = ymin;
            ymin = a;
        }
        double b = 0.0;
        if (xmax < xmin) {
            b = xmax;
            xmax = xmin;
            xmin = b;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
