package com.yelai.wearable.ui.mine

import android.os.Bundle
import android.view.View
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yelai.wearable.R
import com.yelai.wearable.adapter.CourseAdapter
import com.yelai.wearable.adapter.ItemClick
import com.yelai.wearable.entity.CourseEntity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource

/**
 * Created by hr on 18/9/16.
 */

class CourseFragment : KLazyFragment<PViod>() {

    var adapter:CourseAdapter? = null;
    override fun initData(savedInstanceState: Bundle?) {
//        statusView.showEmpty()
        adapter = CourseAdapter(context)

        var list = listOf<CourseEntity>(CourseEntity(R.drawable.course_item_background_banner1,"身体唤醒课程","张三哇","2/3课时",R.drawable.course_icon_item_interest,false),
                CourseEntity(R.drawable.course_item_background_banner1,"身体唤醒课程","张三哇","2/3课时",R.drawable.course_icon_item_interest,false),
                CourseEntity(R.drawable.course_item_background_banner1,"身体唤醒课程","张三哇","2/3课时",R.drawable.course_icon_item_interest,false),
                CourseEntity(R.drawable.course_item_background_banner1,"身体唤醒课程","张三哇","2/3课时",R.drawable.course_icon_item_interest,false))

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        backgroundLayout.backgroundColorResource = R.color.white

//        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
//            override fun onRefresh() {
//
////                p!!.loadData(getType(), 1)
//            }
//
//            override fun onLoadMore(page: Int) {
////                p!!.loadData(getType(), page)
//            }
//        }

//        contentLayout.refreshEnabled(false)
        contentLayout.recyclerView.isRefreshEnabled = false
        //        contentLayout.recyclerView.useDefLoadMoreView()

        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)

//
//
//        if (errorView == null) {
//            errorView = StateView(context)
//        }
//        contentLayout.errorView(errorView)
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))


        adapter!!.addData(list)

        adapter?.recItemClick = object : ItemClick<CourseEntity, CourseAdapter.ViewHolder>() {
            override fun onItemClick(position: Int, model: CourseEntity?, tag: Int, holder: CourseAdapter.ViewHolder?) {

//                CourseDetailActivity.launch(this@CourseFragment.context)

            }
        }

        contentLayout.recyclerView.setPage(1, 1)

        adapter?.notifyDataSetChanged()

        contentLayout.showContent()


//        if (getAdapter().getItemCount() < 1) {
//            contentLayout.showEmpty()
//            return
//        }


//        statusView.showContent()
    }

    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {

        fun newInstance(): CourseFragment {
            val fragment = CourseFragment()
            return fragment
        }
    }
}
