package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListActivity
import com.yelai.wearable.model.Course
import com.yelai.wearable.model.Page
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.load
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.ui.course.AddCourseActivity
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class CourseActivity : BaseListActivity<Course, CourseContract.Presenter>(), CourseContract.View{

    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.CourseList ){

            val page = Page<List<Course>>().apply {
                this@apply.data = data as List<Course>
                pages = 1
                count = 1
                currPage = 1
            }
            list(page)
        }
    }


    open fun onItemViewClick(data: Course){
        AddCourseActivity.launch(context,data.id,true)
    }

    override fun onResume() {
        super.onResume()
        if(AppData.isBackFromPageWithDataAndCleanData(AddCourseActivity::class.java)){
            onRefresh()
        }
    }

    override fun initAdapter(): SimpleRecAdapter<Course, ViewHolder<Course>> {

        return object : BaseAdapter<Course>(context){
            override fun getLayoutId(): Int = R.layout.course_item

            override fun onBindViewHolder(holder: ViewHolder<Course>, position: Int) {

                val item = data[position]

                holder.setData(item)

                holder.itemView.onClick {
                    onItemViewClick(item)
                }

            }
//            * type : 1、教学课 2、训练课 3、兴趣课

            override fun setData(itemView: View, data: Course) {

//                val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
//                options.scaleType = ImageView.ScaleType.FIT_XY
//                ILFactory.getLoader().loadResource(itemView.ivBackground, course.background, options)
//            ILFactory.getLoader().loadResource(itemView.ivTag, course.type,  options)

                when(data.type){
                    "1"->{
                        itemView.tvCourseType.text = "教学课";
                        itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_green_background)
                        itemView.ivBackground.load(R.drawable.course_item_background_banner1)
                    }

                    "2"->{
                        itemView.tvCourseType.text = "训练课";
                        itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                        itemView.ivBackground.load(R.drawable.course_item_background_banner2)

                    }

                    "3"->{
                        itemView.tvCourseType.text = "兴趣课";
                        itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_red_background)
                        itemView.ivBackground.load(R.drawable.course_item_background_banner3)

                    }
                    else->{
                        itemView.tvCourseType.text = "训练课";
                        itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                        itemView.ivBackground.load(R.drawable.course_item_background_banner2)

                    }
                }

                if(data.img != null){
                    itemView.ivBackground.load(data.img)
                }

                itemView.tvCourseName.text = data.title

                itemView.tvCourseDetail.visibility = View.GONE

                itemView.tvCourseTime.visibility = View.GONE

                itemView.tvCourseTime2.visibility = View.VISIBLE

                itemView.tvEdit.visibility = View.VISIBLE

                itemView.tvCourseTime2.text = "课次: ${data.count}/${data.times}"

//                itemView.tvEdit.onClick {
//
//                }

            }
        }
    }

    override fun onRefresh() {
        p.courseList()
    }

    override fun onLoadMore(page: Int) {

    }

    override fun divider() {}

    override fun newP(): PCourse = PCourse()


    override fun initData(savedInstanceState: Bundle?) {

        super.initData(savedInstanceState)

        mToolbar.setMiddleText("课程管理", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setRightText("添加课程")

        mToolbar.tvRight.textSize = 14.0f
        mToolbar.tvRight.paint.flags = Paint.UNDERLINE_TEXT_FLAG;
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected


        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()

        backgroundLayout.backgroundColorResource = R.color.white

//        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color, R.dimen.divider, R.dimen.padding_common_h, R.dimen.padding_common_h)
//        tvAddCourse.onClick {  }

    }

    override fun onToolbarActionsClick(which: Int, view: View?) {
        super.onToolbarActionsClick(which, view)
        if(which == SimpleToolbar.RIGHT_TEXT){
            AddCourseActivity.launch(context)
        }
    }


    override fun bindEvent() {
        super.bindEvent()

    }


    override fun getLayoutId(): Int = R.layout.recyclerview_layout



    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(CourseActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
