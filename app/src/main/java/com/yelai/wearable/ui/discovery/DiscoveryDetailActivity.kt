package com.yelai.wearable.ui.discovery

import android.app.Activity
import android.content.Context
import android.os.Bundle
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.DiscoveryContract
import com.yelai.wearable.load
import com.yelai.wearable.model.DiscoveryDetail
import com.yelai.wearable.net.Api
import com.yelai.wearable.present.PDiscovery
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.callback.SimpleImageFixCallback
import kotlinx.android.synthetic.main.discovery_activity_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class DiscoveryDetailActivity() : BaseActivity<DiscoveryContract.Presenter>(),DiscoveryContract.View {


    override fun detail(detail: DiscoveryDetail) {

        tvTitle.text = detail.title

        tvCollegeName.text = DiscoveryFragment.collageNameMap[detail.mark] + " " + detail.createTime

//        RichText.fromHtml(detail.content).into(tvDescription)
        RichText.fromHtml(detail.content)
                .autoFix(false)
                .fix(object: SimpleImageFixCallback(){
                    override fun onInit(holder: ImageHolder) {
                        if(!holder.source.startsWith("http")){
                            holder.source =  Api.API_BASE_URL + holder.source
                        }
                    }
                }).bind(context).into(tvDescription);
        ivBackground.load(detail.pic)

    }

    override fun onDestroy() {
        super.onDestroy()
        RichText.clear(context)
    }


    override fun initData(savedInstanceState: Bundle?) {

        val id = intent.extras.getString("id")

        p.detail(mapOf("id" to id))

    }

    override fun bindEvent() {
        super.bindEvent()
        ivLeft.onClick { AppManager.finishCurrentActivity() }
    }



    override fun getLayoutId(): Int = R.layout.discovery_activity_detail

    override fun newP(): PDiscovery = PDiscovery()

    companion object {
        fun launch(activity: Context,id:String) {
            Router.newIntent(activity as Activity)
                    .to(DiscoveryDetailActivity::class.java)
                    .putString("id",id).launch()
                    }
        }

}
