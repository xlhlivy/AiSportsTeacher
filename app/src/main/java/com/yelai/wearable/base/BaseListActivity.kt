package com.yelai.wearable.base

import android.os.Bundle
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.model.Page
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.io.Serializable

/**
 * Created by hr on 18/9/16.
 */

abstract class BaseListActivity<D : Serializable,P : IPresent<*>>() : BaseActivity<P>(){

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        if(adapter.dataSource.size == 0){
            contentLayout.refreshState(false)
            contentLayout.showError()
        }
    }



    public open fun list(pager: Page<List<D>>) {

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

    val adapter:SimpleRecAdapter<D, ViewHolder<D>> by lazy {
         initAdapter()
    }

    abstract fun initAdapter():SimpleRecAdapter<D, ViewHolder<D>>;

    open fun divider(){
//        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
    }


    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
        divider()
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

//        contentLayout.showLoading()
//        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))


        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                this@BaseListActivity.onRefresh()
            }

            override fun onLoadMore(page: Int) {
                this@BaseListActivity.onLoadMore(page)
            }
        }

        contentLayout.showContent()

        contentLayout.recyclerView.refreshData()

    }

    abstract fun onRefresh();

    abstract fun onLoadMore(page:Int);

//
//    internal inner class Adapter(context: Context) : SimpleRecAdapter<D,ViewHolder<D>>(context) {
//
//        override fun newViewHolder(itemView: View): ViewHolder<D> {
//            return object : ViewHolder<D>(itemView) {
//                override fun setData(data: D) {
//
//                }
//            }
//        }
//
//        override fun getLayoutId(): Int {
//            return R.layout.mine_sport_history_item
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder<D>, position: Int) {
//            val item = data[position]
//
//        }
//
//    }


}
