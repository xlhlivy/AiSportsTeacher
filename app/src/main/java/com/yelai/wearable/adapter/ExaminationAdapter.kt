package com.yelai.wearable.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.yelai.wearable.R
import com.yelai.wearable.entity.CourseEntity

import cn.droidlover.xdroidmvp.XDroidConf
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.imageloader.ILFactory
import cn.droidlover.xdroidmvp.imageloader.ILoader
import kotlinx.android.synthetic.main.examination_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by wanglei on 2016/12/10.
 */

class ExaminationAdapter(context: Context) : SimpleRecAdapter<CourseEntity, ExaminationAdapter.ViewHolder>(context) {

    override fun newViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun getLayoutId(): Int {
        return R.layout.examination_item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.setData(item)

        holder.itemView.setOnClickListener {

            if (recItemClick != null) {

                recItemClick.onItemClick(position,item,0,holder)

            }
        }


        holder.itemView.tvStart.onClick {

            Toast.makeText(this@ExaminationAdapter.context,"tv start",Toast.LENGTH_LONG).show()

        }


        holder.itemView.tvContinue.onClick {
            Toast.makeText(this@ExaminationAdapter.context,"tv continue",Toast.LENGTH_LONG).show()

        }


        holder.itemView.tvEdit.onClick {
            Toast.makeText(this@ExaminationAdapter.context,"tv edit",Toast.LENGTH_LONG).show()

        }




    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(course:CourseEntity) {
            val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
            options.scaleType = ImageView.ScaleType.FIT_XY

            ILFactory.getLoader().loadResource(itemView.ivBackground, course.background, options)

            itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
            itemView.tvCourseType.text = "训练课";

            itemView.tvCourseName.text = course.name

            itemView.tvCourseDetail.text = "课次: ${course.teacher}"


        }

    }
}
