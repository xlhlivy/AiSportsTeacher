package com.yelai.wearable

import cn.droidlover.xdroidmvp.cache.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yelai.wearable.entity.KVP
import com.yelai.wearable.model.User
import com.yelai.wearable.model.UserInfo
import com.yelai.wearable.ui.course.AddCourseActivity

class AppData{



    companion object {
        val gson = Gson()

        var backData:Any? = null
        var fromPage :Class<*>? = null
        var pushData:Any? = null

        fun clear(){
            backData = null
            fromPage = null
        }

        fun isBackFromPageWithDataAndCleanData(clazz: Class<*>):Boolean{
            val back = (AppData.backData != null && AppData.fromPage != null && AppData.fromPage == clazz)
            clear()
            return back
        }

        fun isBackFromPageWithData(clazz: Class<*>):Boolean{
            return (AppData.backData != null && AppData.fromPage != null && AppData.fromPage == clazz)
        }

        fun backWithData(page:Class<*>,data:Any = true){
            backData = data
            fromPage = page
        }


        var user: User? = null
            get() {
                if(field == null){
                    val value = SharedPref.getInstance(App.context).getString("user",null) ?: return null
                    field = gson.fromJson(value,User::class.java)
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("user", gson.toJson(value))
            }


        var userInfo: UserInfo? = null
            get() {
                if(field == null){
                    val value = SharedPref.getInstance(App.context).getString("user_info",null) ?: return null
                    field = gson.fromJson(value,UserInfo::class.java)
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("user_info", gson.toJson(value))
            }


        fun isLogin():Boolean{
            return SharedPref.getInstance(App.context).getBoolean("is_login",false)
        }

        fun loginSuccess(){
            SharedPref.getInstance(App.context).putBoolean("is_login",true)
        }


        /**
         * 0 未开课->可以设置课程阶段-可以点击点名按钮
         * 1 正在点名 -> 可以操作学生的状态->将按钮设置成为开始上课的按钮
         * 2.点名已经完成正在上课中  ->  是否需要将按钮设置成为下课
         * 3 课次结束
         */
        fun setCourseStatus(timesId:String,status:String){


            val value = SharedPref.getInstance(App.context).getString("course_status","[]")

            val list:MutableList<KVP> = gson.fromJson(value,object : TypeToken<MutableList<KVP>>() {}.type)

            var target = list.firstOrNull { it.key == timesId }

            if(target == null){
                list.add(KVP().apply {
                    key = timesId
                    this.value = status
                })
            }else{
                target.value = status
            }

            SharedPref.getInstance(App.context).putString("course_status", gson.toJson(list))

        }

        fun getCourseStatus(timesId:String):String{

            val value = SharedPref.getInstance(App.context).getString("course_status","[]")

            val list:MutableList<KVP> = gson.fromJson(value,object : TypeToken<MutableList<KVP>>() {}.type)

            var target = list.firstOrNull { it.key == timesId } ?: return "0"

            return target.value
        }




//        var sportTypeList:MutableList<SportType> = ArrayList()
//            get() {
//                if(field.isEmpty()){
//                    val value = SharedPref.getInstance(App.context).getString("sport_type_list",null) ?: return field
//                    field.addAll(gson.fromJson(value,object : TypeToken<List<SportType>>() {}.type))
//                }
//                return field
//            }
//            set(value){
//                field = value
//                SharedPref.getInstance(App.context).putString("sport_type_list", gson.toJson(value))
//            }
//
//
//        var sportType:SportType? = null
//            get() {
//                if(field == null){
//                    val value = SharedPref.getInstance(App.context).getString("sport_type",null) ?: return null
//                    field = gson.fromJson(value,SportType::class.java)
//                }
//                return field
//            }
//            set(value){
//                field = value
//                SharedPref.getInstance(App.context).putString("sport_type", gson.toJson(value))
//            }
//
//        var sportStatistics:Sport? = null
//            get() {
//                if(field == null){
//                    val value = SharedPref.getInstance(App.context).getString("sport_statistics",null) ?: return null
//                    field = gson.fromJson(value,Sport::class.java)
//                }
//                return field
//            }
//            set(value){
//                field = value
//                SharedPref.getInstance(App.context).putString("sport_statistics", gson.toJson(value))
//            }
//
//
//        var myCourseList:MutableList<Course> = ArrayList()
//            get() {
//                if(field.isEmpty()){
//                    val value = SharedPref.getInstance(App.context).getString("my_course_list",null) ?: return field
//                    field.addAll(gson.fromJson(value,object : TypeToken<List<Course>>() {}.type))
//                }
//                return field
//            }
//            set(value){
//                field = value
//                SharedPref.getInstance(App.context).putString("my_course_list", gson.toJson(value))
//            }

    }

}
