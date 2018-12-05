package com.yelai.wearable.model

import com.google.gson.annotations.JsonAdapter
import com.yelai.wearable.net.gson.EmptyStringTypeAdapter
import java.io.Serializable

/**
 * Created by hr on 18/11/11.
 * status： 0：未开考 1：考试中 2：考试完成
 * type : 1：身高体重 2：握力 3：引体向上 4：仰卧起坐 5：1000/800m跑步 6：50m跑步 7：肺活量 8：做立体前驱
 */

//class ExaminationScore : Serializable{
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var memberId:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var name:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var headImg:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var timeStr:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var time:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var height:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var weight:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var result:String = ""
//
//}
