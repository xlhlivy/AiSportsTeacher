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
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.model.CourseTimeDetail
import com.yelai.wearable.model.Page
import com.yelai.wearable.model.Student
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.load
import com.yelai.wearable.present.PCourse
import kotlinx.android.synthetic.main.course_activity_detail.*
import kotlin.collections.ArrayList


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class CourseDetailActivity : BaseActivity<CourseContract.Presenter>(), CourseContract.View{

    override fun getLayoutId(): Int = R.layout.course_activity_detail

//    private var name: String? = null
//    //    签到（0：未签到 1：已签到 2：请假）
//    private var signed: Int = 0//签到
//
//    //    abnormal：异常（0：否 1：是）
//    private var abnormal: Int = 0//异常
//
//    private var memberId: Int = 0//
//
//    private var heart: Int = 0//

    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.CourseTimeDetail){

            data as CourseTimeDetail

            initViewWithData(data)

            bulletinBoard.notice(data.notice)

            studentsFragment.courseTimeDetail = data;


//            var list = ArrayList<Student>()
//
//            var i = 0
//            do {
//                list.add(Student().apply {
//                    name = "张三娃${i}"
//                    signed = 0
//                    abnormal = 0
//                    memberId = i
//                    heart = i * 10
//                })
//            }while (++i < 20)



            studentsFragment.list(Page<List<Student>>().apply {
                pages = 1
                currPage = 1
                count = 1
                this@apply.data = data.students
            })

        }
    }

    private fun initViewWithData(data:CourseTimeDetail){
        when(data.type){
            "1"->{
                tvCourseType.text = "教学课";
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_green_background)
                ivBackground.load(R.drawable.course_item_background_banner1)

            }

            "2"->{
                tvCourseType.text = "训练课";
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                ivBackground.load(R.drawable.course_item_background_banner2)

            }

            "3"->{
                tvCourseType.text = "兴趣课";
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_red_background)
                ivBackground.load(R.drawable.course_item_background_banner3)


            }
            else->{
                tvCourseType.text = "训练课";
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                ivBackground.load(R.drawable.course_item_background_banner2)

            }
        }

        tvCourseName.text = data.title

        tvCourseDetail.text = "课次: ${data.number}/${data.times}"

        tvCourseTime.text = "上课时间: ${data.startTime}"

        tvAssign.text = "${data.signed}"

        tvExceptionCnt.text = "${data.abnormal}"

        tvStep.text = "${data.step}"

    }


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("公告栏", "学生列表")
    private val mTabEntities = ArrayList<CustomTabEntity>()

    private val timesId by lazy{intent.extras.getString("timesId")}


    private val bulletinBoard by lazy{
        CourseBulletinBoardFragment.newInstance()
    }

    private val studentsFragment by lazy {
        StudentsFragment.newInstance()
    }



    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("课程详情", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i],0, 0))
        }

        mFragments.add(bulletinBoard)
        mFragments.add(studentsFragment)

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)


        initViewpager()


        p.courseTimesDetail(timesId)

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
        fun launch(activity: Context,timesId:String) {
            Router.newIntent(activity as Activity)
                    .to(CourseDetailActivity::class.java)
                    .putString("timesId",timesId)
                    .launch()
        }
    }

}
