package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.sport_type_item.view.*
import org.jetbrains.anko.toast


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class DeviceActivity : BaseActivity<PViod>(){

    internal val adapter: Adapter by lazy {
        Adapter(context)
    }


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("我的设备", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()


        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.text_black_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)
        contentLayout.showContent()


        adapter.setData(listOf(
                "搜索设备","解除绑定","血压校准"))

        adapter.notifyDataSetChanged()

    }



    override fun getLayoutId(): Int = R.layout.recyclerview_layout

    override fun newP(): PViod = PViod()


    internal inner class Adapter(context: Context) : SimpleRecAdapter<String, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.sport_type_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {

            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: String) {

                itemView.tvSportName.text = data
            }

        }
    }

    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(DeviceActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

}
