package com.tony.utils.business.entity;


/**
 * Created by ThinkPad on 2018/12/25.
 */

public class Result<T>{
    private String MSG;
    private String CODE;
    private T DATA;

    public Result() {
    }

    public Result(String msg, String code, T data) {
        this.MSG = msg;
        this.CODE = code;
        this.DATA = data;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public T getDATA() {
        return DATA;
    }

    public void setDATA(T DATA) {
        this.DATA = DATA;
    }

}
