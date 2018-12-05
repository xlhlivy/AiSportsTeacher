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
import com.yelai.wearable.entity.MessageEntity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.mine_physique_detail_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class PhysiqueDetailActivity : BaseActivity<PViod>(){

    internal val adapter: Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("我的体质", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()


        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()


        adapter.addData(listOf(MessageEntity("张三","7小时💰","巴拉巴拉",1),MessageEntity("张三","7小时💰","巴拉巴拉",1),MessageEntity("张三","7小时💰","巴拉巴拉",1)))

        adapter.notifyDataSetChanged()

    }


    override fun getLayoutId(): Int = R.layout.recyclerview_layout

    override fun newP(): PViod = PViod()


    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(PhysiqueDetailActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

    internal inner  class Adapter(context: Context) : SimpleRecAdapter<MessageEntity, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.mine_physique_detail_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {
//                SportHistoryDetailActivity.launch(this@PhysiqueDetailActivity.context)
            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: MessageEntity) {
                itemView.tvName.text = "体重"
                itemView.tvScore.text = "(80)"
                itemView.tvValue.text = "180"
                itemView.tvUnity.text = "CM";
            }

        }
    }

}
