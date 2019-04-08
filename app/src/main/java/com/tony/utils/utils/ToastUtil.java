package com.tony.utils.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 吐司管理类
 * @author Tony
 * @time 2019/4/4 11:28
 */
public class ToastUtil {

    public static void show (CharSequence text, Context context){
        try{
           if(text!=null){
               Toast toast = null;
               if(text.length()<10){
                   toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
               }else{
                   toast=Toast.makeText(context,text,Toast.LENGTH_LONG);
               }
               toast.show();
           }
        }catch (Exception e){
            e.printStackTrace();
            Looper.prepare();
            Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }


}
