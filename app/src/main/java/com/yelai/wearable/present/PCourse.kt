package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.net.Api
import com.yelai.wearable.net.CourseService
import com.yelai.wearable.transfer

/**
 * Created by wanglei on 2016/12/31.
 * type: 1：教学课 2：训练课 3：兴趣课
 */



class PCourse : XPresent<CourseContract.View>(), CourseContract.Presenter {

    override fun headUpload(photo: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .headUpload(mapOf("teacher_id" to AppData.user!!.uid,"data_img" to photo))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.ImageUpload, it.data)
                }
    }


    override fun imageUpload(photo: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .imageUpload(mapOf("data_img" to photo))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.ImageUpload, it.data)
                }
    }

    override fun feedback(text: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .feedback(mapOf("teacher_id" to AppData.user!!.uid,"message" to text))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.Feedback, "反馈成功")
                }
    }


    override fun courseReport(timesId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseReport(mapOf("times_id" to timesId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseReport, it.data)
                }
    }

    override fun historyCourseList(page: Int, type: Int) {

        val params = HashMap<String,Any>()

        params.put("teacher_id" , AppData.user!!.uid)

        params.put("page" , page)

        params.put("size" , 10)

        if (type != -1){
            params.put("type" , type)
        }

        Api.getService<CourseService>(CourseService::class.java)
                .historyCourseList(params)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.HistoryCourseList, it.data)
                }
    }


    override fun studentStatus(timesId: String, memberId: String?) {
        v.showIndicator()

        var params = HashMap<String,Any>()

        params["times_id"] = timesId

        if(memberId != null){
            params["member_id"] = memberId
        }

        Api.getService<CourseService>(CourseService::class.java)
                .studentStatus(params)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.StudentStatus, it.data)
                }
    }

    override fun studentInfo(memberId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .studentInfo(mapOf("member_id" to memberId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.StudentInfo, it.data)
                }
    }

    override fun courseTimesStepSave(params: Map<String, Any>) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesStepSave(params)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseTimesStepSave, "设置阶段成功")
                }
    }

    override fun courseInfo(courseId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseInfo(mapOf("lesson_id" to courseId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseInfo, it.data)
                }
    }

    override fun editCourse(params: Map<String, Any>) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .editCourse(params)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.EditCourse, "修改课程成功")
                }
    }

    override fun studentAssign(timesId: String, memberId: String, status: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .studentAssign(mapOf("times_id" to timesId,"member_id" to memberId,"status" to status))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.StudentAssign, "点名成功")
                }
    }

    override fun courseTimesStep(timesId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesStep(mapOf("times_id" to timesId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseTimesStep, it.data)
                }
    }

    override fun courseTimesStepAdd(name: String, time: String, pid: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesStepAdd(mapOf("name" to name,"time" to time,"pid" to pid))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseTimesStepAdd, "添加成功")
                }
    }

    override fun courseTimesStepDel(id: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesStepDel(mapOf("id" to id))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseTimesStepDel, "删除成功")
                }
    }

    override fun courseTimesStepUpdate(id: String, name: String, time: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesStepUpdate(mapOf("id" to id,"name" to name,"time" to time))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseTimesStepUpdate, "更新成功")
                }




    }

    override fun courseTimesDetail(timesId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesDetail(mapOf("times_id" to timesId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseTimeDetail, it.data)
                }
    }

    override fun addCourse(params: Map<String, Any>) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .addCourse(params)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.AddCourse, "添加课程成功")
                }
    }

    override fun weekCourseTimesList() {
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesList(mapOf("teacher_id" to AppData.user!!.uid,"time" to 2))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.WeekCourseTimesList, it.data)
                }
    }

    override fun todayCourseTimesList() {
        Api.getService<CourseService>(CourseService::class.java)
                .courseTimesList(mapOf("teacher_id" to AppData.user!!.uid,"time" to 1))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.TodayCourseTimesList, it.data)
                }
    }


    override fun courseList() {
        Api.getService<CourseService>(CourseService::class.java)
                .courseList(mapOf("teacher_id" to AppData.user!!.uid))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseList, it.data)
                }
    }

//    override fun publishMessage(lessonId: String, memberId: String, message: String) {
//        Api.getService<CourseService>(CourseService::class.java)
//                .publishMessage(mapOf("lesson_id" to lessonId,"member_id" to memberId,"message" to message))
//                .transfer(v)
//                .subscribe({
//                    v.hideIndicator()
//                    v.success(CourseContract.Success.PublishMessage,"发布消息成功")
//                })
//    }
//
//    override fun messageList(lessonId: String, page: Int, size: Int) {
////        v.showIndicator()
//        Api.getService<CourseService>(CourseService::class.java)
//                .messageList(mapOf("lesson_id" to lessonId,"page" to page,"size" to size))
//                .transfer(v)
//                .subscribe({
//                    v.hideIndicator()
//                    v.list(it.data)
//                })
//    }
//
//    override fun courseDetail(lessonId: String) {
//        v.showIndicator()
//        Api.getService<CourseService>(CourseService::class.java)
//                .courseDetail(mapOf("lesson_id" to lessonId))
//                .transfer(v)
//                .subscribe({
//                    v.hideIndicator()
//                    v.success(CourseContract.Success.CourseDetail,it.data)
//                })
//    }
//
//
//    }
//
//
//    override fun interestCourseList() {
//        Api.getService<CourseService>(CourseService::class.java)
//                .courseList(mapOf("type" to 3))
//                .compose(XApi.getApiTransformer<Result<List<Course>>>())
//                .compose(XApi.getScheduler<Result<List<Course>>>())
//                .compose(v.bindToLifecycle<Result<List<Course>>>())
//                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
//                    override fun onFail(error: NetError) {
//                        v.hideIndicator()
//                        v.showError(error.message!!)
//                    }
//
//                    override fun onNext(result: Result<List<Course>>) {
//                        v.hideIndicator()
//                        v.success(CourseContract.Success.InterestCourseList,result.data)
//                    }
//                })
//    }
//
//    override fun trainingCourseList() {
////        v.showIndicator()
//        Api.getService<CourseService>(CourseService::class.java)
//                .courseList(mapOf("type" to 2))
//                .compose(XApi.getApiTransformer<Result<List<Course>>>())
//                .compose(XApi.getScheduler<Result<List<Course>>>())
//                .compose(v.bindToLifecycle<Result<List<Course>>>())
//                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
//                    override fun onFail(error: NetError) {
//                        v.hideIndicator()
//                        v.showError(error.message!!)
//                    }
//
//                    override fun onNext(result: Result<List<Course>>) {
//                        v.hideIndicator()
//                        v.success(CourseContract.Success.TrainingCourseList,result.data)
//                    }
//                })
//    }

//    override fun sign(memberId: String, lessonId: String) {
//        v.showIndicator()
//        Api.getService<CourseService>(CourseService::class.java)
//                .sign(mapOf("member_id" to memberId,"lesson_id" to lessonId))
//                .compose(XApi.getApiTransformer<MapDataResult>())
//                .compose(XApi.getScheduler<MapDataResult>())
//                .compose(v.bindToLifecycle<MapDataResult>())
//                .subscribe(object : ApiSubscriber<MapDataResult>() {
//                    override fun onFail(error: NetError) {
//                        v.hideIndicator()
//                        v.showError(error.message!!)
//                    }
//
//                    override fun onNext(result: MapDataResult) {
//                        v.hideIndicator()
//                        v.success(CourseContract.Success.Sign,"报名成功")
//                    }
//                })
//    }
//
//    override fun teacherCourseList(teacherId:String) {
////        v.showIndicator()
//        Api.getService<CourseService>(CourseService::class.java)
//                .courseList(mapOf("teacher_id" to teacherId))
//                .compose(XApi.getApiTransformer<Result<List<Course>>>())
//                .compose(XApi.getScheduler<Result<List<Course>>>())
//                .compose(v.bindToLifecycle<Result<List<Course>>>())
//                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
//                    override fun onFail(error: NetError) {
//                        v.hideIndicator()
//                        v.showError(error.message!!)
//                    }
//
//                    override fun onNext(result: Result<List<Course>>) {
//                        v.hideIndicator()
//                        v.success(CourseContract.Success.TeacherCourseList,result.data)
//                    }
//                })
//    }


}
