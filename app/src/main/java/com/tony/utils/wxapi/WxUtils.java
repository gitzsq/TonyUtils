package com.tony.utils.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/***
 * 微信工具类
 *
 * Alan 2019-2-19 14:18:11
 * */
public class WxUtils {

    private String WX_APP_ID = "wx421e9fca7afdda1f";
    private static WxUtils wxUtils=null;
    private IWXAPI WXapi;

    private WxUtils(){

    }
    public static WxUtils getInstance(){
        if(wxUtils==null){
            synchronized (WxUtils.class){
                if(wxUtils==null){
                    wxUtils=new WxUtils();
                }
            }
        }

      return wxUtils;
    }


    /**
     * 登录微信
     */
    public void WXLogin(Context mContext) {
        WXapi = WXAPIFactory.createWXAPI(mContext, WX_APP_ID, true);
//        if(WXapi != null) {
//            WXapi.unregisterApp();
//        }
//        WXapi.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);

    }

}
