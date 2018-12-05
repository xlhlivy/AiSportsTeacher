package com.yelai.wearable.contract

import android.view.View
import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView
import com.yelai.wearable.net.HRIView

/**
 * Created by xuhao on 2017/11/30.
 * desc: 搜索契约类
 */
interface RegisterContract {

    interface View : HRIView<Presenter> {

        fun countDownVerifyCode()

//        fun showError(errorMsg: String)
//
//        fun showIndicator()
//
//        fun hideIndicator()

        fun success()
    }

    interface Presenter : IPresent<RegisterContract.View> {

        fun sendVerifyCode(mobile:String)

        fun resetpwd(mobile:String,code:String,password:String)

    }
}