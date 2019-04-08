package com.tony.utils.business.manager;

import com.tony.utils.business.entity.LoginData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * retrofit 接口定义
 * @author Tony
 * @time 2019/4/3 22:04
 */
public interface RetrofitService {

    //登录
    @GET("login")
    Observable<LoginData> login(@Query("userName") String userName,
                                @Query("passWord") String passWord,
                                @Query("videoIP") String videoIP,
                                @Query("token") String token);


}
