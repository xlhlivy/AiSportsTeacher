package com.yelai.wearable.net

import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.model.ExaminationScore
import com.yelai.wearable.model.ExaminationUpdater

/**
 * Created by xuhao on 2017/11/30.
 * desc: 资讯和信息
 */
interface ExaminationContract {

    enum class Success{
        ExaminationInfo,
        SaveExamination,
        ExaminationList,
        ExaminationItemList,
        SaveExaminationResult,
        SaveExaminationItemStatus,
        ExaminationResult,
        ExaminationStudents,
        QRCODE,
        ImageUpload,
    }

    interface View : HRIView<Presenter> {

        fun success(type: Success, data:Any){}//除分页以外的通用返回

    }

    interface Presenter : IPresent<View> {

        /**
         * 1 获取体侧信息  用于修改体侧信息使用
         * exam_id
         * @return
         */
         fun examinationInfo(examId:String)


        /**
         * 2 添加修改体侧信息
         * @return
         */
         fun saveExamination(params: ExaminationUpdater)


        /**
         * 3 获取体侧信息 用于首页展示
         * teacher_id
         * @return
         */
         fun examinationList()


        /**
         * 4 考试的具体内容，一般来讲分为几个小项目
         * 获取体测项目列表  用于考试用->在体侧的首页上  点击开始考试之后实际上是调用这个接口
         * exam_id
         * @return
         */
         fun examinationItemList(examId:String)


        /**
         * 5 录入学生的 小项目考试成绩
         * item_id
         * result  List<ExaminationScore>
         * @return
        </ExaminationScore> */
         fun saveExaminationResult(itemId:String,result:List<ExaminationScore>)


        /**
         * 6 设置体侧的小项目的状态
         * item_id
         * status 项目状态 1:考试中 2:考试完成
         * @return
         */
         fun saveExaminationItemStatus(itemId:String,status:String)


        /**
         * 7 获取学员的成绩，一般来讲分为几个小项目
         * 这个是用来获取单独项目的考试成绩
         * item_id
         * @return
         */
         fun examinationResult(itemId:String)


        /**
         * 8 获取学员列表
         * 单独的每一个小项目，获取一次学员
         * item_id
         * @return
         */
         fun examinationStudents(itemId:String)


         fun qrcode(content:String)

         fun imageUpload(photo: String)

        }
}