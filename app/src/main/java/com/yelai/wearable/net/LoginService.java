package com.yelai.wearable.net;


import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.User;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface LoginService {


    /**
     * 获取验证码接口
     * <p>
     * mobile : 电话
     * type : 1 登录  2 注册  3 重置密码  4：老师登录
     *
     * @param params
     * @return
     */
    @POST("index.php/home/member/send_sms")
    Flowable<MapDataResult> getVerifyCode(@Body Map<String, Object> params);

    @POST("index.php/Home/Teacher/login")
    Flowable<Result<User>> login(@Body Map<String, Object> params);

    @POST("index.php/Home/Teacher/modifyPwd")
    Flowable<MapDataResult> modifyPassword(@Body Map<String, Object> params);

    @POST("index.php/Home/Teacher/forgetPwd")
    Flowable<MapDataResult> forgetPassword(@Body Map<String, Object> params);


}
