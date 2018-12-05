package com.yelai.wearable.net;

import com.yelai.wearable.model.Course;
import com.yelai.wearable.model.CourseDetail;
import com.yelai.wearable.model.CourseReport;
import com.yelai.wearable.model.CourseTime;
import com.yelai.wearable.model.CourseTimeDetail;
import com.yelai.wearable.model.CourseTimeStep;
import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Message;
import com.yelai.wearable.model.Page;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.Student;
import com.yelai.wearable.model.StudentInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface CourseService {

    /**
     * 获取我自己的课程列表
     * teacher_id
     * @return
     */
    @POST("index.php/Home/Lesson/getTeacherLesson")
    Flowable<Result<List<Course>>> courseList(@Body Map<String,Object> params);


    /**
     * 获取我自己的课程列表
     * teacher_id
     * @return
     */
    @POST("index.php/Home/Lesson/getHistoryLesson")
    Flowable<Result<Page<List<CourseTime>>>> historyCourseList(@Body Map<String,Object> params);


    /**
     * 添加反馈
     * times_id
     * @return
     */
    @POST("index.php/Home/Member/feedBack")
    Flowable<Result<MapDataResult>> feedback(@Body Map<String,Object> params);




    /**
     * 图片上传
     * times_id
     * @return
     */
    @POST("index.php/Home/Member/upload_img")
    Flowable<EmptyDataResult> headUpload(@Body Map<String,Object> params);


    /**
     * 图片上传
     * times_id
     * @return
     */
    @POST("index.php/Home/Teacher/upload_img")
    Flowable<EmptyDataResult> imageUpload(@Body Map<String,Object> params);




    /**
     * 课程报表
     * times_id
     * @return
     */
    @POST("index.php/Home/Lesson/lessonReport")
    Flowable<Result<CourseReport>> courseReport(@Body Map<String,Object> params);

    /**
     * 添加课次
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/save")
    Flowable<MapDataResult> addCourse(@Body Map<String,Object> params);


    /**
     * 添加课次
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/update")
    Flowable<MapDataResult> editCourse(@Body Map<String,Object> params);


    /**
     * 获取课程基本信息
     * lesson_id
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/getLessonInfo")
    Flowable<Result<Course>> courseInfo(@Body Map<String,Object> params);




    /****************课次相关内容******************/

    /**
     * 获取老师的课次列表
     * teacher_id
     * time  : 1:今日课程 2:本周课程
     * @return
     */
    @POST("index.php/Home/Lesson/getLessonTimes")
    Flowable<Result<List<CourseTime>>> courseTimesList(@Body Map<String,Object> params);



    /**
     * 获取课次详情列表
     * times_id
     * @return
     */
    @POST("index.php/Home/Lesson/getTimesDetail")
    Flowable<Result<CourseTimeDetail>> courseTimesDetail(@Body Map<String,Object> params);




    /**
     * 获取课程阶段
     * times_id
     * @return
     */
    @POST("index.php/Home/Lesson/getLessonStep")
    Flowable<Result<List<CourseTimeStep>>> courseTimesStep(@Body Map<String,Object> params);



    /**
     * 设置课次，添加小阶段
     * times_id 阶段名称
     * step [step]
     * @return
     */
    @POST("index.php/Home/Lesson/setTimesStep")
    Flowable<MapDataResult> courseTimesStepSave(@Body Map<String,Object> params);




    /**
     * 课次中的阶段，添加小阶段
     * name 阶段名称
     * time 阶段时长
     * pid  父阶段ID
     * @return
     */
    @POST("index.php/Home/Lesson/addLessonStep")
    Flowable<MapDataResult> courseTimesStepAdd(@Body Map<String,Object> params);

    /**
     * 课次中的阶段，删除小阶段
     * id
     * @return
     */
    @POST("index.php/Home/Lesson/delLessonStep")
    Flowable<MapDataResult> courseTimesStepDel(@Body Map<String,Object> params);


    /**
     * 课次中的阶段，修改小阶段信息
     * id   阶段ID
     * name 阶段名称
     * time 阶段时长
     * @return
     */
    @POST("index.php/Home/Lesson/updateLessonStep")
    Flowable<MapDataResult> courseTimesStepUpdate(@Body Map<String,Object> params);




    /**
     * 学生点名签到
     * times_id   阶段ID
     * member_id 阶段名称
     * status 阶段时长
     * @return
     */
    @POST("index.php/Home/Lesson/setStudentTimeStatus")
    Flowable<MapDataResult> studentAssign(@Body Map<String,Object> params);


    /**
     * 获取学生状态信息
     * times_id   阶段ID
     * member_id  非必要字段
     * @return
     */
    @POST("index.php/Home/Lesson/getStudentStatus")
    Flowable<Result<List<Student>>> studentStatus(@Body Map<String,Object> params);



    /**
     * 学生的详细信息
     * member_id
     * @return
     */
    @POST("index.php/Home/Lesson/getStudentInfo")
    Flowable<Result<StudentInfo>> studentInfo(@Body Map<String,Object> params);






}
