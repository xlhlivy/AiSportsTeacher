package com.yelai.wearable.ui.mine

import android.os.Bundle
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.load
import com.yelai.wearable.model.CourseTime
import com.yelai.wearable.model.Page
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.ui.course.CourseDetailActivity
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hr on 18/9/16.
 */

class SportHistoryFragment : BaseListFragment<CourseTime,CourseContract.Presenter>(),CourseContract.View {


    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.HistoryCourseList){
            list(data as Page<List<CourseTime>>)
        }
    }

    override fun initAdapter(): SimpleRecAdapter<CourseTime, ViewHolder<CourseTime>> {

        return object : BaseAdapter<CourseTime>(context){
            override fun getLayoutId(): Int = R.layout.course_item

            override fun onBindViewHolder(holder: ViewHolder<CourseTime>, position: Int) {

                val item = data[position]

                holder.setData(item)

                holder.itemView.onClick {
//                    onItemViewClick(item)
                    SportHistoryDetailActivity.launch(this@SportHistoryFragment.context,item.timesId)

                }

            }
//            * type : 1、教学课 2、训练课 3、兴趣课

            override fun setData(itemView: View, data: CourseTime) {

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

                itemView.tvCourseDetail.text = "课次: ${data.number}/${data.times}"

                itemView.tvCourseTime.text = "上课时间: ${data.startTime}"

            }
        }
    }

    override fun onRefresh() {


       val type = when(mQueryType){
            Type.ALL->-1
            Type.WEEK->1
            Type.TERM->3
            Type.MONTH->2
            else->-1
        }

        p.historyCourseList(1,type)
    }

    override fun onLoadMore(page: Int) {
        val type = when(mQueryType){
            Type.ALL->-1
            Type.WEEK->1
            Type.TERM->3
            Type.MONTH->2
            else->-1
        }
        p.historyCourseList(page,type)

    }

    //    1：周 2：月 3：本学期


    enum class Type{
        ALL,
        WEEK,
        MONTH,
        TERM
    }

    var mQueryType:Type? = null;

    override fun initData(savedInstanceState: Bundle?) {

        super.initData(savedInstanceState)
//

        backgroundLayout.backgroundColorResource = R.color.white

        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)



        contentLayout.recyclerView.refreshData()

    }

    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PCourse = PCourse()


    companion object {

        fun newInstance(type:Type): SportHistoryFragment {
            val fragment = SportHistoryFragment()
            fragment.mQueryType = type
            return fragment
        }
    }

}
