package com.yelai.wearable.ui.sport

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class SportRunWithoutMapActivity : BaseActivity<PViod>(){




    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("运动项目", ContextCompat.getColor(this, R.color.text_black_color))
        mToolbar.setIvLeftVisibility(View.GONE)

        showToolbar()



    }



    override fun getLayoutId(): Int = R.layout.sport_activity_run_without_map

    override fun newP(): PViod = PViod()


    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(SportRunWithoutMapActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

}
