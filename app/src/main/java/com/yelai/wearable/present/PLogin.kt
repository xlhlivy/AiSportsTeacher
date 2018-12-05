package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.model.Result
import com.yelai.wearable.model.UserInfo
import com.yelai.wearable.net.Api
import com.yelai.wearable.net.LoginService
import com.yelai.wearable.transfer

/**
 * Created by hr on 18/9/15.
 */

class PLogin : XPresent<LoginContract.View>(), LoginContract.Presenter {
    override fun modifyPassword(mobile:String,code:String,password:String) {
        v.showIndicator()
        Api.getService<LoginService>(LoginService::class.java)
                .modifyPassword(mapOf("teacher_id" to AppData.user!!.uid,"mobile" to mobile,"code" to code,"password" to password))
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    v.modifyPasswordSuccess()
                })
    }

    override fun login(params: Map<String, Any>) {
        v.showIndicator()
        Api.getService<LoginService>(LoginService::class.java)
                .login(params)
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    AppData.user = it.data
                    v.success()
                })
    }




    override fun sendVerifyCode(mobile: String,type:Int) {
        v.showIndicator()
        Api.getService<LoginService>(LoginService::class.java)
                .getVerifyCode(mapOf("mobile" to mobile,"type" to type))
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    v.countDownVerifyCode()
                })
    }
}
