package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.DiscoveryContract
import com.yelai.wearable.contract.DiscoveryService
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.contract.UserContract
import com.yelai.wearable.model.*
import com.yelai.wearable.net.Api
import com.yelai.wearable.ui.login.LoginActivity

/**
 * Created by hr on 18/9/15.
 */

class PDiscovery : XPresent<DiscoveryContract.View>(), DiscoveryContract.Presenter {
    override fun list(params: Map<String, Any>) {
        v.showIndicator()
        Api.getService<DiscoveryService>(DiscoveryService::class.java)
                .list(params)
                .compose(XApi.getApiTransformer<Result<Page<List<DiscoveryItem>>>>())
                .compose(XApi.getScheduler<Result<Page<List<DiscoveryItem>>>>())
                .compose(v.bindToLifecycle<Result<Page<List<DiscoveryItem>>>>())
                .subscribe(object : ApiSubscriber<Result<Page<List<DiscoveryItem>>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<Page<List<DiscoveryItem>>>) {
                        v.hideIndicator()
                        v.list(result.data)
//                        result.data.apply {v.list(data,hasMore())}
                    }
                })
    }

    override fun detail(params: Map<String, Any>) {
        v.showIndicator()
        Api.getService<DiscoveryService>(DiscoveryService::class.java)
                .detail(params)
                .compose(XApi.getApiTransformer<Result<DiscoveryDetail>>())
                .compose(XApi.getScheduler<Result<DiscoveryDetail>>())
                .compose(v.bindToLifecycle<Result<DiscoveryDetail>>())
                .subscribe(object : ApiSubscriber<Result<DiscoveryDetail>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<DiscoveryDetail>) {
                        v.hideIndicator()
                        v.detail(result.data)
                    }
                })
    }

}
