package com.yelai.wearable.ui.discovery

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.imageloader.ILFactory
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.R
import com.yelai.wearable.base.WebActivity
import com.yelai.wearable.contract.DiscoveryContract
import com.yelai.wearable.load
import com.yelai.wearable.model.DiscoveryItem
import com.yelai.wearable.model.Page
import com.yelai.wearable.net.Api
import com.yelai.wearable.present.PDiscovery
import kotlinx.android.synthetic.main.discovery_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * Created by hr on 18/9/16.
 */

class DiscoveryFragment : KLazyFragment<DiscoveryContract.Presenter>(),DiscoveryContract.View{

    override fun list(pager:Page<List<DiscoveryItem>>) {

        val page = pager.currPage

        if (page > 1) {
            adapter.addData(pager.data)
        } else {
            adapter.setData(pager.data)
        }

        contentLayout.recyclerView.setPage(page, pager.pages)

        if (adapter.itemCount < 1) {
            contentLayout.showEmpty()
            return
        }else{
            contentLayout.showContent()
        }

    }




    internal val adapter:Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
//        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

//        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))


        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                p.list(mapOf("page" to 1))
            }

            override fun onLoadMore(page: Int) {
                p.list(mapOf("page" to page))
            }
        }


//        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

//        adapter.addData(listOf(CollegeEntity("电子科大","无标题💰","内容是什么",1), CollegeEntity("张三","7小时💰","巴拉巴拉",1), CollegeEntity("张三","7小时💰","巴拉巴拉",1)))

//        adapter.notifyDataSetChanged()


//        p.list(emptyMap())
        contentLayout.recyclerView.refreshData()

    }



    override fun getLayoutId(): Int {
        return R.layout.discovery_fragment
    }

    override fun newP(): PDiscovery = PDiscovery()


    internal inner  class Adapter(context: Context) : SimpleRecAdapter<DiscoveryItem, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.discovery_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {

                if(item.type == "1"){
                    DiscoveryDetailActivity.launch(context,item.id)
                }else{

                    WebActivity.launch(activity,item.url,item.title);

                }

            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: DiscoveryItem) {

                itemView.tvCollegeName.text = collageNameMap[data.mark]
                itemView.tvTitle.text = data.title
                itemView.tvDescription.text = data.desc
                itemView.ivBackground.load(data.pic)

//                val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
//                options.scaleType = ImageView.ScaleType.FIT_XY
//                ILFactory.getLoader().loadNet(itemView.ivBackground, Api.API_BASE_URL + data.pic, null)
//                itemView.ivBackground
//                with(itemView){
////                    tvCollegeName.text = data.name
//                    tvTitle.text = data.title
//                    tvDescription.text = data.title
//                }
            }

        }
    }


    companion object {

        val collageNameMap = mapOf<String,String>("1" to "电子科大","2" to "四川大学" ,"3" to "联盟资讯")

        fun newInstance(): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            return fragment
        }
    }
}
