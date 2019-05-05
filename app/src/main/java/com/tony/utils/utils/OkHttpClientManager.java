package com.tony.utils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * okhttp 封装类  by Alan
 *
 * **/
public class OkHttpClientManager {
    private OkHttpClient client;
    private volatile static OkHttpClientManager manager;   //防止多个线程访问时
    private final String TAG = OkHttpClientManager.class.getSimpleName();  //获得类名
    private Handler handler;

    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串数据
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    //文件传输
    MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");

    private OkHttpClientManager() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    //采用单例模式获取对象
    public static OkHttpClientManager getInstance() {
        OkHttpClientManager instance = null;
        if (manager == null) {
            synchronized (OkHttpClientManager.class) { //同步代码块
                if (instance == null) {
                    instance = new OkHttpClientManager();
                    manager = instance;
                }
            }
        }
        return manager;
    }


    /**
     * 异步请求，请求返回Json对象
     *
     * @param url
     * @param callback
     */

    public void asyncJsonObjectByUrl(final int requestType,String url, final JsonObjectCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(requestType,response.body().string(), callback);
                }
            }
        });
    }


    /**
     * 模拟表单的提交
     *
     * @param url
     * @param param
     * @param callback
     */
    public void sendComplexForm(final int requestType,String url, Map<String, String> param, final JsonObjectCallback callback) {
        FormBody.Builder form_builder = new FormBody.Builder();  //表单对象，包含以input开始的对象，模拟一个表单操作，以HTML表单为主
        //如果键值对不为空，且值不为空
        if (param != null && !param.isEmpty()) {
            //循环这个表单，zengqiang for循环
            for (Entry<String, String> entry : param.entrySet()) {
                form_builder.add(entry.getKey(), entry.getValue());
            }
        }
        //声明一个请求对象体
        RequestBody request_body = form_builder.build();
        //采用post的方式进行提交
        Request request = new Request.Builder().url(url).post(request_body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(requestType,response.body().string(), callback);
                }
            }
        });
    }
//    public void sendComplexForm(final int requestType,String url, Map<String, String> param, final JsonObjectCallback callback) {
//        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//        //循环添加文件
//        Set<Entry<String, String>> entrys = param.entrySet();  //此行可省略，直接将map.entrySet()写在for-each循环的条件中
//        for(Map.Entry<String, String> entry:entrys){
//            System.out.println("key值："+entry.getKey()+" value值："+entry.getValue());
//            requestBodyBuilder.addFormDataPart(entry.getKey(),entry.getKey(), RequestBody.create(MutilPart_Form_Data, entry.getValue()));
//        }
//        RequestBody requestBody = requestBodyBuilder.build();
//        Request request = new Request.Builder() .url( url) .post(requestBody) .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response != null && response.isSuccessful()) {
//                    onSuccessJsonObjectMethod(requestType,response.body().string(), callback);
//                }
//            }
//        });
//    }

    /**
     * 请求返回相应结果的是Json对象
     *
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonObjectMethod(final int requestType,final String jsonValue, final JsonObjectCallback callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(requestType,new JSONObject(jsonValue));
                    } catch (JSONException e) {

                    }
                }
            }
        });
    }


    private void onSuccessImgMethod(final int requestType,final Bitmap bitmap, final ImgCallback callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(requestType,bitmap);
                    } catch (Exception e) {

                    }

                }
            }
        });
    }


    /**
     * 异步请求,请求返回Json字符串
     *
     * @param url
     * @param callback
     */
    public void asyncJsonStringByURL(final int requestType,String url, final  JsonStringCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            //enqueue是调用了一个入队的方法
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonStringMethod(requestType,response.body().string(), callback);
                }
            }
        });

    }



    /**
     * 异步请求，请求返回图片
     *
     * @param url
     * @param callback
     */
    public void asyncDownLoadImgtByUrl(final int requestType,String url, final ImgCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    byte[] data = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    onSuccessImgMethod(requestType,bitmap, callback);

                    System.out.println(data.length);
                }
            }
        });

    }


    /**
     * 请求返回的是JSON字符串
     *
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonStringMethod(final int requestType,final String jsonValue, final JsonStringCallback callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(requestType,jsonValue);
                    } catch (Exception e) {

                    }
                }
            }
        });

    }
    /**
     * 向服务器提交String请求
     *
     * @param url
     * @param content
     * @param callback
     */
    public void sendStringByPost(final int requestType,String url, String content, final JsonObjectCallback callback) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, content)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(requestType,response.body().string(), callback);
                }
            }
        });

    }

    //回调
    public interface JsonStringCallback {
        void onResponse(int requestType, String result);
    }


    public interface ImgCallback {
        void onResponse(int requestType, Bitmap bitmap);
    }

    public interface JsonObjectCallback {
        void onResponse(int requestType, JSONObject jsonObject);
    }


}
