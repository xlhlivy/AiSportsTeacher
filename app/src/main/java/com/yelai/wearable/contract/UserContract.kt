package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.net.HRIView

/**
 * Created by xuhao on 2017/11/30.
 * desc: 搜索契约类
 */
interface UserContract {

    interface View : HRIView<Presenter> {

        fun memberInfoSuccess()

    }

    interface Presenter : IPresent<View> {

        fun memberInfo(params:Map<String,Any>)

    }
}