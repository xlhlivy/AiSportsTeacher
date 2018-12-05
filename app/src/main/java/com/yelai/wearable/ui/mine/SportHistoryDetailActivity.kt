package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.load
import com.yelai.wearable.model.CourseReport
import com.yelai.wearable.present.PCourse
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.mine_sport_history_detail.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class SportHistoryDetailActivity : BaseActivity<CourseContract.Presenter>(),CourseContract.View{


    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.CourseReport){

            data as CourseReport


            when(data.type){
                "1"->{
                    tvCourseType.text = "教学课";
                    tvCourseType.setBackgroundResource(R.drawable.day_right_circle_green_background)
                    ivBackground.load(R.drawable.course_item_background_banner1)

                }

                "2"->{
                    tvCourseType.text = "训练课";
                    tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                    ivBackground.load(R.drawable.course_item_background_banner2)

                }

                "3"->{
                    tvCourseType.text = "兴趣课";
                    tvCourseType.setBackgroundResource(R.drawable.day_right_circle_red_background)
                    ivBackground.load(R.drawable.course_item_background_banner3)


                }
                else->{
                    tvCourseType.text = "训练课";
                    tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                    ivBackground.load(R.drawable.course_item_background_banner2)


                }
            }


            if(data.img != null){
                ivBackground.load(data.img)
            }


            tvCourseName.text = data.title

            tvCourseDetail.text = "课次: ${data.number}/${data.times}"

            tvCourseTime.text = "上课时间: ${data.timeStr}"


            tvStrength.text = data.strength

            tvDensity.text = data.density

            tvStep.text = data.step

            tvMotion.text = data.motion

            tvHeart.text = data.heart

            tvConsume.text = data.consume

            //给界面赋值



        }
    }

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("课程报表", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()


        p.courseReport(timesId);

    }

    override fun getLayoutId(): Int = R.layout.mine_sport_history_detail

    override fun newP(): PCourse = PCourse()

    val timesId:String by lazy{intent.extras.getString("timesId","")}

    companion object {

        fun launch(activity: Context,timesId:String) {
            Router.newIntent(activity as Activity)
                    .to(SportHistoryDetailActivity::class.java)
                    .putString("timesId",timesId)
                    .launch()
        }
    }

}
