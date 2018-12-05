package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.net.HRIView

/**
 * Created by xuhao on 2017/11/30.
 * desc: 搜索契约类
 */
interface LoginContract {

    interface View : HRIView<Presenter> {

        fun countDownVerifyCode(){}

        fun success()

        fun modifyPasswordSuccess(){}

    }

    interface Presenter : IPresent<View> {

        fun sendVerifyCode(mobile:String,type:Int=4)

        fun login(params:Map<String,Any>)

        fun modifyPassword(mobile:String,code:String,password:String)

    }
}