package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.net.HRIView

/**
 * Created by xuhao on 2017/11/30.
 * desc: 资讯和信息
 */
interface CourseContract {

    enum class Success{
        CourseList,
        AddCourse,
        EditCourse,
        TodayCourseTimesList,
        WeekCourseTimesList,
        CourseTimeDetail,
        CourseTimesStep,
        CourseTimesStepAdd,
        CourseTimesStepDel,
        CourseTimesStepUpdate,
        StudentAssign,
        CourseInfo,
        CourseTimesStepSave,
        StudentStatus,
        StudentInfo,
        HistoryCourseList,
        CourseReport,
        Feedback,

        ImageUpload,

        TeacherCourseList,
        Sign,
        InterestCourseList,
        TrainingCourseList,
        CourseDetail,
        MessageList,
        PublishMessage,
    }

    interface View : HRIView<Presenter> {

        fun success(type: Success, data:Any){}//除分页以外的通用返回

//        fun list(page:Page<List<Message>>){}//分页

    }

    interface Presenter : IPresent<View> {

        /**
         * 获取运动的类型
         * @return
         */
//        fun teacherCourseList(teacherId:String)
//
//        fun sign(memberId:String,lessonId:String)
//
//        fun interestCourseList()
//
//        fun trainingCourseList()

        fun feedback(text:String)

        fun addCourse(params:Map<String,Any>)

        fun editCourse(params:Map<String,Any>)

        fun courseInfo(courseId:String)

        fun historyCourseList(page:Int,type:Int)

        fun courseReport(timesId:String)

        fun imageUpload(photo:String)

        fun headUpload(photo:String)


        fun courseList()

        fun todayCourseTimesList()

        fun weekCourseTimesList()

        fun courseTimesDetail(timesId:String)


        /**
         * 获取课程阶段
         * times_id
         * @return
         */
        fun courseTimesStep(timesId:String)


        fun courseTimesStepSave(params:Map<String,Any>)

        /**
         * 课次中的阶段，添加小阶段
         * name 阶段名称
         * time 阶段时长
         * pid  父阶段ID
         * @return
         */
        fun courseTimesStepAdd(name:String,time:String,pid:String)

        /**
         * 课次中的阶段，删除小阶段
         * id
         * @return
         */
         fun courseTimesStepDel(id:String)


        /**
         * 课次中的阶段，修改小阶段信息
         * id   阶段ID
         * name 阶段名称
         * time 阶段时长
         * @return
         */
         fun courseTimesStepUpdate(id:String,name:String,time:String)

         fun studentAssign(timesId:String,memberId:String,status:String)

         fun studentStatus(timesId:String,memberId:String? = null)

         fun studentInfo(memberId:String)

//        fun courseDetail(lessonId:String)
//
//        fun messageList(lessonId:String,page:Int,size:Int = 10)
//
//        fun publishMessage(lessonId:String,memberId:String,message:String)


    }
}