package com.yelai.wearable.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import com.yelai.wearable.R
import com.yelai.wearable.entity.CourseEntity

import cn.droidlover.xdroidmvp.XDroidConf
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.imageloader.ILFactory
import cn.droidlover.xdroidmvp.imageloader.ILoader
import kotlinx.android.synthetic.main.course_item.view.*

/**
 * Created by wanglei on 2016/12/10.
 */

class CourseAdapter(context: Context) : SimpleRecAdapter<CourseEntity, CourseAdapter.ViewHolder>(context) {

    override fun newViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun getLayoutId(): Int {
        return R.layout.course_item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.setData(item)

        holder.itemView.setOnClickListener {

            if (recItemClick != null) {

                recItemClick.onItemClick(position,item,0,holder)

            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(course:CourseEntity) {
            val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
            options.scaleType = ImageView.ScaleType.FIT_XY

            ILFactory.getLoader().loadResource(itemView.ivBackground, course.background, options)
//            ILFactory.getLoader().loadResource(itemView.ivTag, course.type,  options)

            itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
            itemView.tvCourseType.text = "训练课";

            itemView.tvCourseName.text = course.name

            itemView.tvCourseDetail.text = "课次: ${course.teacher}"

            itemView.tvCourseTime.text = "上课时间: ${course.time}"

        }

    }
}
