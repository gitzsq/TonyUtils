package com.tony.utils.utils;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 地图相关工具类
 * @author Tony
 * @time 2019/5/5 16:01
 */
public class MapUtils {

   private static double EARTH_RADIUS = 6371.393;

   private Context mContext;
   public MapUtils(Context context){
      this.mContext= context;
   }
   //gps转百度
    public  LatLng GoogleGps_to_Baidu(LatLng sourceLatLng){
        CoordinateConverter coordinateConverter =new CoordinateConverter();
        coordinateConverter.from(CoordinateConverter.CoordType.GPS);//sourceLatLng待转换坐标
        coordinateConverter.coord(sourceLatLng);
        return coordinateConverter.convert();
    }

   //计算两点间的直线距离
    public String getDistance(double lat1,double lng1,double lat2,double lng2){
        double radLat1=rad(lat1);
        double radLat2=rad(lat2);
        double radLng1=rad(lng1);
        double radLng2=rad(lng2);
        double a = radLat1-radLat2;
        double b = radLng1-radLng2;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));

        s=s*EARTH_RADIUS;

        return transformDistance(s);
    }

    /**
     * 截取地址
     * @param address
     * @return
     */
    public static List<Map<String, String>> addressResolution(String address) {
        String regex = "([^省]+自治区|.*?省|.*?行政区|.*?市)?([^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)?([^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?([^区]+区|.+镇)?(.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province = null, city = null, county = null, town = null, village = null;
        List<Map<String, String>> table = new ArrayList<Map<String, String>>();
        Map<String, String> row = null;
        while (m.find()) {
            row = new LinkedHashMap<String, String>();
            province = m.group(1);
            row.put("province", province == null ? "" : province.trim());
            city = m.group(2);
            row.put("city", city == null ? "" : city.trim());
            county = m.group(3);
            row.put("county", county == null ? "" : county.trim());
            town = m.group(4);
            row.put("town", town == null ? "" : town.trim());
            village = m.group(5);
            row.put("village", village == null ? "" : village.trim());
            table.add(row);
        }
        return table;
    }
    //格式化距离显示格式
    private String transformDistance(double s) {
        DecimalFormat df =new DecimalFormat("#.0");
        String dis ="";
        if(s>100){
          dis = (int)s +"km";
        }else if(s>1){
            dis =df.format(s)+"km";
        }else{
            s =Math.round(s*1000);
            dis=(int)s+"m";
        }
        return dis;
    }
   //格式化时间段(传进来的time单位为s)
   public String formatTime(int time){
        StringBuffer buffer = new StringBuffer();
        if(time >3600) {
            buffer.append(time / 3600)
                    .append("小时")
                    .append("(time/60)%60")
                    .append("分钟");

        }else if(time >60 && time<3600){
            buffer.append(time/60)
                    .append("分钟");
        }else{
            buffer.append(time)
                    .append("秒");
        }
        return buffer.toString();
   }

    private static double rad(double d){
        return d * Math.PI / 180.0;
    }

    public static double a = 6378245.0;
    private  static  double x0 =-20037508.342787;
    private  static  double y0 = 20037508.342787;
    private  static  int tileSize= 256;
    /**
     * Web墨卡托投影行列号换算
     * @param lon 经度
     * @param lat 纬度
     * @param level 切片等级
     * @return
     */
    public static int[] getMercatorTileRowCol(double lon,double lat,int level){
        double[] xy=getMercatorXY(lon,lat);
        double x = xy[0];
        double y = xy[1];

        List<TileLod> lods = TileLod.getWebTileLods();
        TileLod lod=lods.get(level);
        int col = (int) Math.floor((x-x0)/(tileSize*lod.getResolution()));
        int row = (int) Math.floor((y0 - y)/(tileSize*lod.getResolution()));
        return new int[]{col,row};
    }
    /**
     * 、经纬度坐标 => 投影坐标；google、高德、腾讯地图使用的是Web Mercator投影。
     * @param lon
     * @param lat
     * @return
     */
    public static double[] getMercatorXY(double lon, double lat) {
        double earthRad = a;
        double x = lon * Math.PI / 180.0 * earthRad;
        double a = lat * Math.PI / 180.0;
        double y = earthRad / 2.0 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
        return new double[]{x, y};
    }


}
