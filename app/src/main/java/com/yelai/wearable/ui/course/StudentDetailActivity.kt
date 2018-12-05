package com.yelai.wearable.ui.course

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.*
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.model.Student
import com.yelai.wearable.model.StudentInfo
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.present.PCourse
import kotlinx.android.synthetic.main.course_activity_student_detail.*
import kotlinx.android.synthetic.main.course_item.view.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class StudentDetailActivity : BaseActivity<CourseContract.Presenter>(), CourseContract.View{

    override fun getLayoutId(): Int = R.layout.course_activity_student_detail

    override fun success(type: CourseContract.Success, data: Any) {

        if(type == CourseContract.Success.StudentInfo){

            data as StudentInfo

            tvName.text = "姓名: ${data.trueName}"

            tvBmi.text =    "BMI : ${data.bmi}"

            tvHeight.text = "身高: ${data.height} cm"

            tvWeight.text = "体重: ${data.weight} kg"

//            tvCourseType.text = "什么课"

            tvHeartbeat.text = data.quiteHeartRate

            tvMorningHeartbeat.text = data.pulse

            tvSleepTime.text = data.sleep

            tvStatus.text = data.health

            tvSport.text = data.dayMotion

            tvCourseSport.text = data.lessonMotion

            tvTime.text = data.motionTime

            tvDistance.text = data.motionRange

            tvCount.text = data.motionNumber

        }else if(type == CourseContract.Success.StudentAssign){

            showToast(if(remarkStudent != null && remarkStudent == "1"){"签到成功"}else{"移除成功"})

            AppData.backWithData(StudentDetailActivity::class.java)

            AppManager.finishCurrentActivity()
        }
    }

    var remarkStudent :String? = null

    override fun bindEvent() {
        super.bindEvent()

        tvSign.onClick {

            remarkStudent = "1"
            p.studentAssign(student!!.timesId,student!!.memberId.toString(),"1")
        }

        tvRemove.onClick {
            remarkStudent = "5"
            p.studentAssign(student!!.timesId,student!!.memberId.toString(),"5")
        }




    }


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("学生详情", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        student = intent.extras.getSerializable("student")  as Student?


        if(student == null)return


        if(student!!.signed == 1){

            tvSign.text = "已签到"
            tvSign.isClickable = false
            tvSign.alpha = .5f
        }


        when(AppData.getCourseStatus(student!!.timesId)){


            "1"->{//点名

            }
            "2"->{//正在上课

            }
            else->{
                tvSign.text = "签 到"
                tvSign.alpha = 1.0f
                tvSign.isClickable = false
                tvSign.backgroundColorResource = R.color.text_grey_color
                tvSign.textColorResource = R.color.white

            }

        }



//        when (student!!.memberId % 3){
//            0->{
//                tvScore.setCurrentProgress(1f)
//                tvScore.setEmptyColorRes(R.color.white)
//                tvScore.setFullColorRes(R.color.tab_text_selected)
//
//                cbCheck.isChecked = true
//                cbCheck.backgroundResource = R.drawable.course_sign_full_green_checked
//            }
//            1->{
//
//                tvScore.setCurrentProgress(0f)
//                tvScore.setEmptyColorRes(R.color.white)
//                tvScore.setFullColorRes(R.color.tab_text_unselected)
//
//                cbCheck.isChecked = false
//                cbCheck.backgroundResource = R.drawable.course_sign_unchecked
//
//            }
//            2->{
//
//                tvScore.setCurrentProgress(0f)
//                tvScore.setEmptyColorRes(R.color.white)
//                tvScore.setFullColorRes(R.color.tab_text_selected)
//                cbCheck.isChecked = true
//                cbCheck.backgroundResource = R.drawable.course_sign_empty_green_checked
//            }
//        }

        initScore()

        when(student!!.courseType){
            "1"->{
                tvCourseType.text = "教学课";

                tvCourseType.setBackgroundResource(R.drawable.day_left_circle_green_background)

            }

            "2"->{
                tvCourseType.text = "训练课";
                tvCourseType.setBackgroundResource(R.drawable.day_left_circle_orange_background)
            }

            "3"->{
                tvCourseType.text = "兴趣课";
                tvCourseType.setBackgroundResource(R.drawable.day_left_circle_red_background)

            }
            else->{
                tvCourseType.text = "训练课";
                tvCourseType.setBackgroundResource(R.drawable.day_left_circle_orange_background)
            }
        }


        p.studentInfo(student!!.memberId.toString())

    }


    private fun initScore(){

        if(student == null)return
        
        val data = student!!
        
        tvScore.text = data.heart.toString()

        tvScore.setCurrentProgress(data.heart.toFloat()/120)


        if (data.signed == 0){
            tvScore.setCurrentProgress(0f)
            tvScore.setEmptyColorRes(R.color.white)
            tvScore.setFullColorRes(R.color.tab_text_unselected)

            cbCheck.isChecked = false
            cbCheck.backgroundResource = R.drawable.course_sign_unchecked
        }else if(data.signed == 1){

            if(data.heart > 80){

                tvScore.setCurrentProgress(1f)
                tvScore.setEmptyColorRes(R.color.white)
                tvScore.setFullColorRes(R.color.tab_text_selected)

                cbCheck.isChecked = true
                cbCheck.backgroundResource = R.drawable.course_sign_full_green_checked

            }else{

                tvScore.setCurrentProgress(0f)
                tvScore.setEmptyColorRes(R.color.white)
                tvScore.setFullColorRes(R.color.tab_text_selected)
                cbCheck.isChecked = true
                cbCheck.backgroundResource = R.drawable.course_sign_empty_green_checked
            }

        }

    }

    var student:Student? = null




    override fun newP(): PCourse = PCourse()



    companion object {
        fun launch(activity: Context,student: Student? = null) {
            Router.newIntent(activity as Activity)
                    .to(StudentDetailActivity::class.java)
                    .putSerializable("student",student)
                    .launch()
        }
    }

}
