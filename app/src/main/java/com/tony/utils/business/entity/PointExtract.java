package com.tony.utils.business.entity;

/**
 * Created by ThinkPad on 2019/2/28.
 */

public class PointExtract {
    /**
     * 数据类型
     */
    private String dataType = "";

    /**
     * 数据高度层
     */
    private String layerHeight = "";

    /**
     * 起报时间
     */
    private String baseDate;

    /**
     * 预报时效
     */
    private int fHour;


    /**
     * 任意点的经度
     */
    private double longitude;

    /**
     * 任意点的纬度
     */
    private double latitude;


    /**
     * 观测的数值
     */
    private double pointValue;


    /**
     * 观测到的角度，如果有，如风、浪、流的角度
     */
    private int pointAngle;

    /**
     * 观测到的方向，如果有，如风、浪、流的方位表示，NNE-东东北
     */
    private String pointDirectionText;

    /**
     * 观测数值的量纲单位
     */
    private String valueUnits;

    /**
     * NC点位数据的描述信息，如是否找到相应的文件，等等
     */
    private String description;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLayerHeight() {
        return layerHeight;
    }

    public void setLayerHeight(String layerHeight) {
        this.layerHeight = layerHeight;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public int getfHour() {
        return fHour;
    }

    public void setfHour(int fHour) {
        this.fHour = fHour;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getPointValue() {
        return pointValue;
    }

    public void setPointValue(double pointValue) {
        this.pointValue = pointValue;
    }

    public int getPointAngle() {
        return pointAngle;
    }

    public void setPointAngle(int pointAngle) {
        this.pointAngle = pointAngle;
    }

    public String getPointDirectionText() {
        return pointDirectionText;
    }

    public void setPointDirectionText(String pointDirectionText) {
        this.pointDirectionText = pointDirectionText;
    }

    public String getValueUnits() {
        return valueUnits;
    }

    public void setValueUnits(String valueUnits) {
        this.valueUnits = valueUnits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
