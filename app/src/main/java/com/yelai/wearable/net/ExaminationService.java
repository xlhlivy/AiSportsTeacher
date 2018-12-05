package com.yelai.wearable.net;

import com.yelai.wearable.model.Course;
import com.yelai.wearable.model.Examination;
import com.yelai.wearable.model.ExaminationItem;
import com.yelai.wearable.model.ExaminationScore;
import com.yelai.wearable.model.ExaminationUpdater;
import com.yelai.wearable.model.GankResults;
import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Qrcode;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.Student;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface ExaminationService {

    /**
     * 获取体侧信息  用于修改体侧信息使用
     * exam_id
     * @return
     */
    @POST("index.php/Home/Exam/getExamInfo")
    Flowable<Result<Examination>> examinationInfo(@Body Map<String,Object> params);


    /**
     * 添加修改体侧信息
     * @return
     */
    @POST("index.php/Home/Exam/setExam")
    Flowable<MapDataResult> saveExamination(@Body ExaminationUpdater params);


    /**
     * 获取体侧信息  用于修改体侧信息使用
     * teacher_id
     * @return
     */
    @POST("index.php/Home/Exam/getExamList")
    Flowable<Result<List<Examination>>> examinationList(@Body Map<String,Object> params);



    /**
     * 考试的具体内容，一般来讲分为几个小项目
     * 获取体测项目列表  用于考试用->在体侧的首页上  点击开始考试之后实际上是调用这个接口
     * exam_id
     * @return
     */
    @POST("index.php/Home/Exam/getExamItemList")
    Flowable<Result<List<ExaminationItem>>> examinationItemList(@Body Map<String,Object> params);



    /**
     * 录入学生的 小项目考试成绩
     * item_id
     * result  List<ExaminationScore>
     * @return
     */
    @POST("index.php/Home/Exam/setStudentResult")
    Flowable<Result<MapDataResult>> saveExaminationResult(@Body Map<String,Object> params);



    /**
     * 设置体侧的小项目的状态
     * item_id
     * status 项目状态 1:考试中 2:考试完成
     * @return
     */
    @POST("index.php/Home/Exam/setItemStatus")
    Flowable<Result<MapDataResult>> saveExaminationItemStatus(@Body Map<String,Object> params);




    /**
     * 获取学员的成绩，一般来讲分为几个小项目
     * 这个是用来获取单独项目的考试成绩
     * item_id
     * @return
     */
    @POST("index.php/Home/Exam/getStudentResult")
    Flowable<Result<List<ExaminationScore>>> examinationResult(@Body Map<String,Object> params);



    /**
     * 获取学员列表
     * 单独的每一个小项目，获取一次学员
     * item_id
     * @return
     */
    @POST("index.php/Home/Exam/getStudentList")
    Flowable<Result<List<Student>>> examinationStudents(@Body Map<String,Object> params);


    /**
     * 获取学员列表
     * 单独的每一个小项目，获取一次学员
     * item_id
     * @return
     */
    @POST("index.php/Home/Member/qrcode")
    Flowable<Result<Qrcode>> qrcode(@Body Map<String,Object> params);

}


