package com.tony.utils.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间封装类
 *
 * @author Tony
 * @time 2019/4/19 16:24
 */
public class DateUtils {
    private volatile static DateUtils dateUtils;

    public static DateUtils getInstance() {
        if (dateUtils == null) {
            synchronized (DateUtils.class) {
                if (dateUtils == null) {
                    dateUtils = new DateUtils();
                }
            }
        }
        return dateUtils;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    //获取前月的第一天
    public String getFirstDay() {
        String firstDay = "";
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        firstDay = format.format(cal_1.getTime());
        return firstDay;
    }

    //获取前月的最后一天
    public String getLastDay() {
        String lastDay = "";
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        lastDay = format.format(cale.getTime());
        return lastDay;
    }
    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public  String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance(); date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public  String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }



}
