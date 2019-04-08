package com.tony.utils.business.iview;

import com.tony.utils.business.entity.LoginData;

/**
 * 登录view表现
 * @author Tony
 * @time 2019/4/4 10:47
 */
public interface ILoginView extends View {

    void onSuccess(LoginData mLoginData);
    void onError(String result);

}
