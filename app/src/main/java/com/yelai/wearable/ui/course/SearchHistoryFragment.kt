package com.yelai.wearable.ui.course

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yelai.wearable.R
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.course_item_search_history_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * Created by hr on 18/9/16.
 */

class SearchHistoryFragment : KLazyFragment<PViod>() {

    internal val adapter:Adapter by lazy {
        Adapter(context)
    }

    var mData:List<String>? = null

    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.text_black_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

    }


    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PViod? {
        return null
    }

    override fun onResume() {
        super.onResume()
        adapter.setData(mData)
        adapter.notifyDataSetChanged()
    }


    open fun historyData(data:List<String>){
        mData = data
    }

    companion object {

        fun newInstance(): SearchHistoryFragment {
            val fragment = SearchHistoryFragment()
            return fragment
        }
    }

    interface Callback{
        fun callback(name:String)
    }

    public var callback:Callback? = null


    internal inner  class Adapter(context: Context) : SimpleRecAdapter<String,Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.course_item_search_history_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {
                 this@SearchHistoryFragment.callback?.callback(item)
            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: String) {
                itemView.tvCourseTeacher.text = data
            }

        }
    }
}
