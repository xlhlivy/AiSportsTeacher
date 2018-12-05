package com.yelai.wearable.entity

/**
 * Created by hr on 18/9/17.
 *
 * name 体侧考试项目
 *
 * description 描述，单位或者考试的项目简述
 *
 * leakNum 缺考人数
 *
 * status  当前的状态
 *
 * id  无意义
 */

data class CategoryEntity(val name: String, val description:List<String>, val leakNum:String, val status:Int,val id:Int) {
}
