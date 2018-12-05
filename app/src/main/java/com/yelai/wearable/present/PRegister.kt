package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.contract.RegisterContract
import com.yelai.wearable.model.MapDataResult
import com.yelai.wearable.net.Api
import com.yelai.wearable.net.LoginService

/**
 * Created by hr on 18/9/15.
 */

class PRegister : XPresent<RegisterContract.View>(), RegisterContract.Presenter {
    override fun resetpwd(mobile: String, code: String, password: String) {
        v.showIndicator()
        Api.getService<LoginService>(LoginService::class.java)
                .forgetPassword(mapOf("mobile" to mobile, "password" to password, "code" to code))
                .compose(XApi.getApiTransformer<MapDataResult>())
                .compose(XApi.getScheduler<MapDataResult>())
                .compose(v.bindToLifecycle<MapDataResult>())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(gankResults: MapDataResult) {
                        v.hideIndicator()
                        v.success()
                    }
                })
    }

    override fun sendVerifyCode(mobile: String) {
        v.showIndicator()
        Api.getService<LoginService>(LoginService::class.java)
                .getVerifyCode(mapOf("mobile" to mobile, "type" to 5))
                .compose(XApi.getApiTransformer<MapDataResult>())
                .compose(XApi.getScheduler<MapDataResult>())
                .compose(v.bindToLifecycle<MapDataResult>())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(gankResults: MapDataResult) {
                        v.hideIndicator()
                        v.countDownVerifyCode()
                    }
                })

    }


}
