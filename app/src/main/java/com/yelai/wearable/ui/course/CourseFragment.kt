package com.yelai.wearable.ui.course

import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.yelai.wearable.AppData

import com.yelai.wearable.R
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.course_fragment.*
import org.jetbrains.anko.sdk25.coroutines.onClick

import java.util.ArrayList

/**
 * Created by hr on 18/9/16.
 */

class CourseFragment : KLazyFragment<PViod>() {


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("今日课程", "本周课程")
    private val mTabEntities = ArrayList<CustomTabEntity>()



    override fun initData(savedInstanceState: Bundle?) {

        mTabEntities.clear()
        mFragments.clear()

        tvAddCourse.paint.flags = Paint. UNDERLINE_TEXT_FLAG;

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], 0,0))
        }

        mFragments.add(TodayCourseFragment.newInstance())
        mFragments.add(WeekCourseFragment.newInstance())
        ctlLayout.setTabData(mTabEntities,childFragmentManager,R.id.ctlContent,mFragments)
    }


    override fun bindEvent() {
        super.bindEvent()
        tvAddCourse.onClick { AddCourseActivity.launch(this@CourseFragment.context) }


//        tvAddCourse.onClick {
////            mWeekDatePicker.show()
//        }

//        PickTimeDialog(context).create().cantlable();


    }

//    override fun onResume() {
//        super.onResume()
//
//
//
//        if(AppData.isBackFromPageWithDataAndCleanData(AddCourseActivity::class.java)){
//
//            println("back data course fragment")
//        }
//
//    }


    override fun getLayoutId(): Int {
        return R.layout.course_fragment
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {

        fun newInstance(): CourseFragment {
            val fragment = CourseFragment()
            return fragment
        }
    }
}
