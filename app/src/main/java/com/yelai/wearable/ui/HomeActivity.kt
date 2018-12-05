package com.yelai.wearable.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import cn.droidlover.xdroidmvp.mvp.XActivity
import cn.droidlover.xdroidmvp.router.Router
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.showToast
import com.yelai.wearable.ui.course.CourseFragment
import com.yelai.wearable.ui.discovery.DiscoveryFragment
import com.yelai.wearable.ui.examination.ExaminationFragment
import com.yelai.wearable.ui.mine.MineFragment
import com.yelai.wearable.ui.sport.SportFragment
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

/**
 * Created by hr on 18/9/15.
 */

class HomeActivity : BaseActivity<PViod>() {

    private val mFragments by lazy { listOf(
            CourseFragment.newInstance(),
            ExaminationFragment.newInstance(),DiscoveryFragment.newInstance(),MineFragment.newInstance()) }
    private val mTitles = arrayOf("我的课程","测试","资讯","我的")
    private val mIconUnselectIds = intArrayOf(R.drawable.tab_icon_course_normal, R.drawable.tab_icon_test_normal, R.drawable.tab_icon_information_normal,R.drawable.tab_icon_mine_normal)
    private val mIconSelectIds = intArrayOf(R.drawable.tab_icon_course_press, R.drawable.tab_icon_test_press, R.drawable.tab_icon_information_press,R.drawable.tab_icon_mine_press)
    private val mTabEntities = ArrayList<CustomTabEntity>()


    override fun initData(savedInstanceState: Bundle?) {
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

//        mFragments.add(DayFragment.newInstance())
//        mFragments.add(CourseFragment.newInstance())
//        mFragments.add(SportFragment.newInstance())
//        mFragments.add(DiscoveryFragment.newInstance())
//        mFragments.add(MineFragment.newInstance())

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)



        initViewpager()
    }

    private fun initViewpager(){

        //两位数
//        mTabLayout_2.showMsg(0, 55)
//        mTabLayout_2.setMsgMargin(0, -5f, 5f)

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

        viewPager.offscreenPageLimit = 4;

        viewPager.currentItem = 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun newP(): PViod? {
        return null
    }


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

    protected fun dp2px(dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private var lastTime:Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(keyCode== KeyEvent.KEYCODE_BACK&&event.action== KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-lastTime)>2000){
                showToast("在按一次退出程序")
                lastTime=System.currentTimeMillis();
            }else {
                AppManager.AppExit(this@HomeActivity.context)
            }
            return  true;
        }

        return super.onKeyDown(keyCode, event)
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(HomeActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

}
