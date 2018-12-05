package com.yelai.wearable.net;

import java.util.HashMap;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String API_BASE_URL = "http://sport.aieye8.com/";

    public static final String IMAGE_URL = API_BASE_URL  + "data/upload/";

    private static HashMap<Class,Object> serviceMap = new HashMap<>();

    public static <T> T getService(Class clazz){
        if(!serviceMap.containsKey(clazz)){
            synchronized (Api.class){
                if(!serviceMap.containsKey(clazz)){
                    serviceMap.put(clazz,XApi.getInstance().getRetrofit(API_BASE_URL, true).create(clazz));
                }
            }
        }
        return (T)serviceMap.get(clazz);
    }


    private static GankService gankService;

    @Deprecated
    public static GankService getGankService() {
        if (gankService == null) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankService.class);
                }
            }
        }
        return gankService;
    }



}
