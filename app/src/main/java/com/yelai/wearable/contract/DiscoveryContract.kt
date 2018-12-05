package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.model.*
import com.yelai.wearable.net.HRIView
import retrofit2.http.Body

/**
 * Created by xuhao on 2017/11/30.
 * desc: 资讯和信息
 */
interface DiscoveryContract {

    interface View : HRIView<Presenter> {

        fun detail(detail: DiscoveryDetail){}

        fun list(page:Page<List<DiscoveryItem>>){}

    }

    interface Presenter : IPresent<View> {

         fun detail(@Body params: Map<String, Any>)

         fun list(@Body params: Map<String, Any>)

    }
}