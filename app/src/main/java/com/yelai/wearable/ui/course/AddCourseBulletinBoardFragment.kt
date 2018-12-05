package com.yelai.wearable.ui.course


import android.os.Bundle
import android.text.SpannableStringBuilder
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.yelai.wearable.R
import com.yelai.wearable.model.Course
import com.yelai.wearable.present.PViod
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.course_fragment_add_bulletin_board.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hr on 18/9/16.
 */

class AddCourseBulletinBoardFragment : KLazyFragment<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {

        tvBulletinBoard.hint = SpannableStringBuilder("公告板")

        btnSave.onClick {
            (activity as AddCourseActivity).addCourse()
        }
    }

    public fun collectionData(data:HashMap<String,Any>):Boolean{
        if(tvBulletinBoard != null && tvBulletinBoard.text.isNotEmpty()){
            data["notice"] = tvBulletinBoard.text.trim().toString()
            return true
        }
        showToast("请填写公告板内容")
        return false
    }


    var initCourse:Course? = null

    fun initDataWithView(course: Course) {

        if(tvBulletinBoard != null){

            tvBulletinBoard.text = SpannableStringBuilder(course.notice)

        }else{
            initCourse = course;
        }
    }

    override fun onStartLazy() {
        if(initCourse != null){
            initDataWithView(initCourse!!)
        }
    }




    override fun getLayoutId(): Int {
        return R.layout.course_fragment_add_bulletin_board
    }

    override fun newP(): PViod? {
        return null
    }



    companion object {

        fun newInstance(): AddCourseBulletinBoardFragment {
            val fragment = AddCourseBulletinBoardFragment()
            return fragment
        }
    }

}
