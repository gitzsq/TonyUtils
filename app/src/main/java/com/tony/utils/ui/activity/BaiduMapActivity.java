package com.tony.utils.ui.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.FileTileProvider;
import com.baidu.mapapi.map.Gradient;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Tile;
import com.baidu.mapapi.map.TileOverlay;
import com.baidu.mapapi.map.TileOverlayOptions;
import com.baidu.mapapi.map.TileProvider;
import com.baidu.mapapi.map.UrlTileProvider;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tony.utils.R;
import com.tony.utils.utils.GlideCircleTransform;
import com.tony.utils.utils.UIUtil;
import com.tony.utils.utils.pic.pictools.FileUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaiduMapActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback, FileUtils.GlideLoadBitmapCallback {
    @BindView(R.id.test_img)
    ImageView testImg;
    private TileOverlay tileOverlay;//瓦片图对象
    // 设置瓦片图的在线缓存大小，默认为20 M
    private static final int TILE_TMP = 20 * 1024 * 1024;
    private static final int MAX_LEVEL = 21;
    private static final int MIN_LEVEL = 3;
    private boolean mapLoaded = false;
    MapStatusUpdate mMapStatusUpdate;

    //    private  final String onlineUrl = "http://api0.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&udt=20150601&customid=light";
    private final String onlineUrl = "http://47.100.49.112:8088/fishser/onboard/map/marineElements?dataname=shou&datatype=sst&xmin=130&xmax=140&ymin=30&ymax=40&z=6";
    @BindView(R.id.bmapview)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private Gradient gradient;
    private HeatMap heatMap;
    Tile offlineTile;
    private List<LatLng> latLngList = new ArrayList<>();
    private Bitmap bm;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initView() {
        mContext=BaiduMapActivity.this;
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(16.0f);
        builder.target(new LatLng(39.914935D, 116.403119D));
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        FileUtils.getBitmap(mContext,onlineUrl,this);

//        initOffTile();
//        onlineTile();
//        initHeatMap();

    }

    private void addOverLay() {
        double latitude = 40.014697;
        double longitude = 116.197299;

//        -180.0,42.2315028,-158.325,82.431502
        LatLng southwest = new LatLng(-75.2315028, -180.0);//西南
        LatLng northeast = new LatLng(75.431502, -158.325);//东北
        LatLngBounds bounds = new LatLngBounds.Builder().include(southwest)
                .include(northeast).build();//得到一个地理范围对象
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromBitmap(bm);
//                .fromResource(R.drawable.sss);
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        groundOverlayOptions.image(bitmap2);//显示的图片
        groundOverlayOptions.positionFromBounds(bounds);//显示的位置
//        groundOverlayOptions.transparency(0.7f);//显示的透明度
        mBaiduMap.addOverlay(groundOverlayOptions);
    }


    //初始化热力图
    private void initHeatMap() {
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

    private void initOffTile() {
        TileOverlayOptions options = new TileOverlayOptions();
        // 构造显示瓦片图范围，当前为世界范围
        LatLng northeast = new LatLng(38, 115);
        LatLng southwest = new LatLng(39, 116);
        // 设置离线瓦片图属性option
        options.tileProvider(tileProvider)
                .setPositionFromBounds(new LatLngBounds.Builder().include(northeast).include(southwest).build());
        // 通过option指定相关属性，向地图添加离线瓦片图对象
        tileOverlay = mBaiduMap.addTileLayer(options);

        if (mapLoaded) {
            setMapStatusLimits();
        }
    }

    private void setMapStatusLimits() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(38.94001804746338, 115.41224644234747))
                .include(new LatLng(39.90299859954822, 116.38359947963427));
        mBaiduMap.setMapStatusLimits(builder.build());
        mBaiduMap.setMaxAndMinZoomLevel(17.0f, 16.0f);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
    }

    /**
     * 定义瓦片图的离线Provider，并实现相关接口
     * MAX_LEVEL、MIN_LEVEL 表示地图显示瓦片图的最大、最小级别
     * Tile 对象表示地图每个x、y、z状态下的瓦片对象
     */
    FileTileProvider tileProvider = new FileTileProvider() {
        @Override
        public Tile getTile(int x, int y, int z) {
            // 根据地图某一状态下x、y、z加载指定的瓦片图
            String filedir = "logo.jpg";
//           if(count==0){
            if (bm == null) {
                return null;
            }
            // 瓦片图尺寸必须满足256 * 256
            offlineTile = new Tile(bm.getWidth(), bm.getHeight(), toRawData(bm));
            bm.recycle();
//           }
            return offlineTile;
        }

        @Override
        public int getMaxDisLevel() {
            return MAX_LEVEL;
        }

        @Override
        public int getMinDisLevel() {
            return MIN_LEVEL;
        }
    };

    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    /**
     * 瓦片文件解析为Bitmap
     *
     * @param fileName
     * @return 瓦片文件的Bitmap
     */
    public Bitmap getFromAssets(String fileName) {
        AssetManager am = this.getAssets();
        InputStream is = null;
        Bitmap bm;

        try {
            is = am.open(fileName);
            bm = BitmapFactory.decodeStream(is);
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析Bitmap
     *
     * @param bitmap
     * @return
     */
    byte[] toRawData(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getWidth()
                * bitmap.getHeight() * 4);
        bitmap.copyPixelsToBuffer(buffer);
        byte[] data = buffer.array();
        buffer.clear();
        return data;
    }

    /**
     * 使用瓦片图的在线方式
     */
    private void onlineTile() {

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        if (tileOverlay != null && mBaiduMap != null) {
            tileOverlay.removeTileOverlay();
        }
//        final String urlString = mEditText.getText().toString();
        /**
         * 定义瓦片图的在线Provider，并实现相关接口
         * MAX_LEVEL、MIN_LEVEL 表示地图显示瓦片图的最大、最小级别
         * urlString 表示在线瓦片图的URL地址
         */
        TileProvider tileProvider = new UrlTileProvider() {
            @Override
            public int getMaxDisLevel() {
                return MAX_LEVEL;
            }

            @Override
            public int getMinDisLevel() {
                return MIN_LEVEL;
            }

            @Override
            public String getTileUrl() {
                return onlineUrl;
            }

        };
        TileOverlayOptions options = new TileOverlayOptions();
        // 构造显示瓦片图范围，当前为世界范围
        LatLng northeast = new LatLng(80, 180);
        LatLng southwest = new LatLng(-80, -180);
        // 通过option指定相关属性，向地图添加在线瓦片图对象
        tileOverlay = mBaiduMap.addTileLayer(options.tileProvider(tileProvider).setMaxTileTmp(TILE_TMP)
                .setPositionFromBounds(new LatLngBounds.Builder().include(northeast).include(southwest).build()));
        if (mapLoaded) {
            mBaiduMap.setMaxAndMinZoomLevel(21.0f, 3.0f);
            mBaiduMap.setMapStatusLimits(new LatLngBounds.Builder().include(northeast).include(southwest).build());
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }
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

    @Override
    public void onMapLoaded() {
        mapLoaded = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getBitmapCallback(Bitmap bitmap) {
        bm = bitmap;
        if(bm==null){
            UIUtil.showToast(this,"图片获取失败");
            return;
        }

        testImg.setImageBitmap(bm);
        addOverLay();
    }
}
