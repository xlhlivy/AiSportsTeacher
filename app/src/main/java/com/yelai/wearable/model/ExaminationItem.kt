package com.yelai.wearable.model

import java.io.Serializable

/**
 * Created by hr on 18/11/11.
 * status： 0：未开考 1：考试中 2：考试完成
 * type : 1：身高体重 2：握力 3：引体向上 4：仰卧起坐 5：1000/800m跑步 6：50m跑步 7：肺活量 8：做立体前驱
 */

class ExaminationItem : Serializable{

    var id:String = ""

    var title:String = ""

    var examId:String = ""

    var type:String = ""

    var status:String = ""

    var miss:String = ""

    var tag:MutableList<String> = ArrayList<String>()

    var time:String = ""

    var require:String = ""

    var isEdit:Boolean = false


}
