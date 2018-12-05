package com.yelai.wearable.contract;

import com.yelai.wearable.model.DiscoveryDetail;
import com.yelai.wearable.model.DiscoveryItem;
import com.yelai.wearable.model.Page;
import com.yelai.wearable.model.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface DiscoveryService {

    /**
     * id  非必填字段
     * @param params
     * @return
     */
    @POST("index.php/Home/News/getDetail")
    Flowable<Result<DiscoveryDetail>> detail(@Body Map<String, Object> params);



    /**
     * mark  非必填字段
     * page 当前页 默认1
     * size  分页大小 默认10
     * @param params
     * @return
     */
    @POST("index.php/Home/News/getList")
    Flowable<Result<Page<List<DiscoveryItem>>>> list(@Body Map<String, Object> params);




}
