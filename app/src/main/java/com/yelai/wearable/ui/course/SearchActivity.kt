package com.yelai.wearable.ui.course

import android.content.Context
import android.os.Bundle
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.R
import xyz.sahildave.widget.SearchViewLayout
import android.support.v4.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.entity.TeacherEntity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.course_item_search_history_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.find
import java.util.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class SearchActivity : BaseActivity<PViod>(){

    var searchHistoryData : LinkedList<String> = LinkedList<String>()

    var mSearchKeyword:String = ""

    val adapter: Adapter by lazy {
        Adapter(context)
    }


    override fun initData(savedInstanceState: Bundle?) {


        showToolbar()

        mToolbar.setMiddleText("手动搜索老师",ContextCompat.getColor(this, R.color.text_black_color))


        val searchViewLayout = find(R.id.search_view_container) as SearchViewLayout

        val searchHistoryFragment = SearchHistoryFragment.newInstance();

        searchViewLayout.setExpandedContentSupportFragment(this, searchHistoryFragment)
        searchViewLayout.handleToolbarAnimation(mToolbar)
        searchViewLayout.setCollapsedHint("请输入教师名字")
        searchViewLayout.setExpandedHint("请输入教师名字")
//        searchViewLayout.setHint("Global Hint");

        val collapsed = ColorDrawable(ContextCompat.getColor(this, R.color.page_bg_color))
        val expanded = ColorDrawable(ContextCompat.getColor(this, R.color.default_color_expanded))
        searchViewLayout.setTransitionDrawables(collapsed, expanded)


        searchViewLayout.setSearchListener { searchKeyword ->
            searchViewLayout.collapse()

            searchViewLayout.setCollapsedHint(searchKeyword)

            searchHistoryData.add(searchKeyword)

            mSearchKeyword = searchKeyword

            //先于 on toggle animation 执行否则会出问题

        }
        searchViewLayout.setOnToggleAnimationListener(object : SearchViewLayout.OnToggleAnimationListener {
            override fun onStart(expanding: Boolean) {
                if (expanding) {
                    searchHistoryFragment.historyData(searchHistoryData)
                    mSearchKeyword = ""
                    searchViewLayout.setCollapsedHint("请输入教师名字")
                } else {
//                    p.search(mSearchKeyword)
                }
            }

            override fun onFinish(expanded: Boolean) {}
        })


        searchHistoryFragment.callback = object :SearchHistoryFragment.Callback{
            //先于 on toggle animation 执行否则会出问题

            override fun callback(name: String) {
                mSearchKeyword = name
                searchViewLayout.setCollapsedHint(mSearchKeyword)
            }
        }

//        searchViewLayout.setSearchBoxListener(object : SearchViewLayout.SearchBoxListener {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
////                Log.d(FragmentActivity.TAG, "beforeTextChanged: $s,$start,$count,$after")
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
////                Log.d(FragmentActivity.TAG, "onTextChanged: $s,$start,$before,$count")
//            }
//
//            override fun afterTextChanged(s: Editable) {
////                Log.d(FragmentActivity.TAG, "afterTextChanged: $s")
//            }
//        })

        initAdapter()


//        p.search(mSearchKeyword)
    }



    fun initAdapter(){

        val self = this;

        val margin = context.resources.getDimensionPixelSize(R.dimen.padding_common_h)


        with(contentLayout.recyclerView){
            verticalLayoutManager(context)
            horizontalDividerMargin(R.color.text_black_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
            adapter = XRecyclerAdapter(self.adapter)
        }

//        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
//                p.search(mSearchKeyword)
            }

            override fun onLoadMore(page: Int) {
//                p!!.loadData(getType(), page)
            }
        }
        contentLayout.refreshEnabled(true)
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(this, R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()
    }


    override fun getLayoutId(): Int = R.layout.course_activity_search

    override fun newP(): PViod = PViod()

    fun setSearchResult(result:List<TeacherEntity>){
        adapter.setData(result)
        adapter.notifyDataSetChanged()
    }




    class Adapter(context: Context) : SimpleRecAdapter<TeacherEntity, Adapter.ViewHolder>(context) {

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

            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: TeacherEntity) {
                itemView.tvCourseTeacher.text = data.name
                itemView.ivNext.visibility = View.VISIBLE
            }

        }
    }


}
