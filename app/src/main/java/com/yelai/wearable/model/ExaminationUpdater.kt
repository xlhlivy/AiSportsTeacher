package com.yelai.wearable.model

import com.google.gson.annotations.JsonAdapter
import com.yelai.wearable.net.gson.EmptyStringTypeAdapter
import java.io.Serializable

class ExaminationUpdater : Serializable{

    @JsonAdapter(EmptyStringTypeAdapter::class) var title:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var teacherId:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var teacherName:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var type:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var lessonId:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var item:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var img:String = ""


    var students:MutableList<String> = ArrayList<String>(0)

    @JsonAdapter(EmptyStringTypeAdapter::class) var status:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var createTime:String = ""

    @JsonAdapter(EmptyStringTypeAdapter::class) var examId:String = ""//修改的时候将id 设置成为examId
}