package com.yelai.wearable.model

import java.io.Serializable

/**
 * Created by hr on 18/11/11.
 */

class Examination : Serializable{

    var id:String = ""

    var title:String = ""

    var teacherId:String = ""

    var teacherName:String = ""

    var type:String = ""

    var lessonId:String = ""

    var item:String = ""

    var img:String = ""

    var students:MutableList<Student> = ArrayList<Student>(0)

    var status:String = ""

    var createTime:String = ""

//    var examId:String = ""//修改的时候将id 设置成为examId


    fun toUpdater():ExaminationUpdater{
        var updater = ExaminationUpdater()
        updater.apply {
            examId = id
            title
            teacherId
            teacherName
            img
            type
            lessonId
            item
            students = this@Examination.students.map { it.member_id.toString() }.toMutableList()
        }
        return updater
    }

}


