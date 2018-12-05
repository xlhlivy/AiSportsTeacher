package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.ui.course.SearchActivity

/**
 * Created by xuhao on 2017/11/30.
 * desc: 搜索契约类
 */
interface SearchContract {

//    interface View : IView<Presenter> {
//        /**
//         * 设置热门关键词数据
//         */
//        fun setHotWordData(string: ArrayList<String>)
//
//        /**
//         * 设置搜索关键词返回的结果
//         */
//        fun setSearchResult(result:ArrayList<TeacherEntity>)
//        /**
//         * 关闭软件盘
//         */
//        fun closeSoftKeyboard()
//        /**
//         * 设置空 View
//         */
//        fun setEmptyView()
//
//        fun showError(errorMsg: String,errorCode:Int)
//    }


    interface Presenter : IPresent<SearchActivity> {
        /**
         * 获取热门关键字的数据
         */
        fun requestHotWordData()

        /**
         * 查询搜索
         */
        fun querySearchData(words:String)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}