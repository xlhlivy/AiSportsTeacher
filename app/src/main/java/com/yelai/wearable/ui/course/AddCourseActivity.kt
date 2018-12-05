package com.yelai.wearable.ui.course

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import cn.droidlover.xdroidmvp.router.Router
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.model.Course
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.course_activity_detail.*
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

open class AddCourseActivity : BaseActivity<CourseContract.Presenter>(), CourseContract.View{

    override fun success(type: CourseContract.Success, data: Any) {

        if(type == CourseContract.Success.AddCourse){
            showToast(data as String)

            AppData.backWithData(AddCourseActivity::class.java)

            AppManager.finishCurrentActivity()
        }else if(type == CourseContract.Success.EditCourse){
            showToast(data as String)

            AppData.backWithData(AddCourseActivity::class.java)

            AppManager.finishCurrentActivity()
        }else if(type == CourseContract.Success.CourseInfo){

            data as Course

            baseInfo.initDataWithView(data)

            bulletinBoard.initDataWithView(data)

        }

    }


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("基础信息","公告栏")
    private val mTabEntities = ArrayList<CustomTabEntity>()

    private val baseInfo by lazy{
        AddCourseBaseInformationFragment.newInstance(isEdit)
    }

    private val bulletinBoard by lazy{
        AddCourseBulletinBoardFragment.newInstance()
    }

    private val isEdit by lazy{intent.extras.getBoolean("isEdit",false)}

    private val courseId by lazy{intent.extras.getString("courseId","")}


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText(if(isEdit){"编辑课程"}else{"添加课程"}, ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i],0, 0))
        }

        mFragments.add(baseInfo)
        mFragments.add(bulletinBoard)

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)

        initViewpager()

        if(isEdit && courseId.isNotEmpty()){
            p.courseInfo(courseId)
        }
    }


    public fun addCourse(){

        val params = HashMap<String,Any>()

        if (isEdit){

            params["id"] = courseId
            if(bulletinBoard.collectionData(params)){
                p.editCourse(params)
            }
        }else{
            params["teacher_id"] = AppData.user!!.uid

            if(baseInfo.collectionData(params) && bulletinBoard.collectionData(params)){

                p.addCourse(params)
            }
        }
    }


    private fun initViewpager(){


        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
                if (position == 0) {
//                    tabLayout.showMsg(0, 2)
                    //                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        viewPager.offscreenPageLimit = 2;
        viewPager.currentItem = 0
    }

    override fun getLayoutId(): Int = R.layout.course_activity_add

    override fun newP(): PCourse = PCourse()



    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }


    companion object {
        fun launch(activity: Context,courseId:String = "",isEdit:Boolean = false) {
            Router.newIntent(activity as Activity)
                    .to(AddCourseActivity::class.java)
                    .putString("courseId",courseId)
                    .putBoolean("isEdit",isEdit)
                    .launch()
        }
    }

}
