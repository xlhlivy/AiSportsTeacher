package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class BodyInfoActivity : BaseActivity<PViod>(){


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("体型录入", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()


    }


    override fun getLayoutId(): Int = R.layout.mine_body_info_activity

    override fun newP(): PViod = PViod()


    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(BodyInfoActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
