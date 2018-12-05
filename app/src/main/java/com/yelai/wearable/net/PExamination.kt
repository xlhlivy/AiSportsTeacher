package com.yelai.wearable.net

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.ExaminationScore
import com.yelai.wearable.model.ExaminationUpdater
import com.yelai.wearable.net.ExaminationContract.Success
import com.yelai.wearable.transfer


/**
 * Created by wanglei on 2016/12/31.
 * status： 0：未开考 1：考试中 2：考试完成
 * type : 1：身高体重 2：握力 3：引体向上 4：仰卧起坐 5：1000/800m跑步 6：50m跑步 7：肺活量 8：做立体前驱
 */



class PExamination : XPresent<ExaminationContract.View>(), ExaminationContract.Presenter {

    override fun imageUpload(photo: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .imageUpload(mapOf("teacher_id" to AppData.user!!.uid,"data_img" to photo))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.ImageUpload, it.data)
                }
    }

    override fun qrcode(content: String) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .qrcode(mapOf("content" to content))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.QRCODE, it.data)
                }
    }

    override fun examinationInfo(examId: String) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .examinationInfo(mapOf("exam_id" to examId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.ExaminationInfo, it.data)
                }
    }

    override fun saveExamination(params: ExaminationUpdater) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .saveExamination(params)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.SaveExamination, "保存成功")
                }
    }

    override fun examinationList() {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .examinationList(mapOf("teacher_id" to AppData.user!!.uid))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.ExaminationList, it.data)
                }
    }

    override fun examinationItemList(examId: String) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .examinationItemList(mapOf("exam_id" to examId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.ExaminationItemList, it.data)
                }
    }

    override fun saveExaminationResult(itemId: String, result: List<ExaminationScore>) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .saveExaminationResult(mapOf("item_id" to itemId,"result" to result))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.SaveExaminationResult, "录入成绩成功")
                }
    }

    override fun saveExaminationItemStatus(itemId: String, status: String) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .saveExaminationItemStatus(mapOf("item_id" to itemId,"status" to status))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.SaveExaminationItemStatus, "修改考试状态成功")
                }
    }

    override fun examinationResult(itemId: String) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .examinationResult(mapOf("item_id" to itemId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.ExaminationResult, it.data)
                }
    }

    override fun examinationStudents(itemId: String) {
        v.showIndicator()
        Api.getService<ExaminationService>(ExaminationService::class.java)
                .examinationStudents(mapOf("item_id" to itemId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(Success.ExaminationResult, it.data)
                }
    }

}
