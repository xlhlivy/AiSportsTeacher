package com.yelai.wearable.ui.mine

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
import com.yelai.wearable.present.PViod
import com.yelai.wearable.ui.mine.SportHistoryFragment.Type
import kotlinx.android.synthetic.main.mine_sport_history_activity.*
import java.util.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class SportHistoryActivity : BaseActivity<PViod>(){


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("全部", "周","月","本学期")
//    1：周 2：月 3：本学期
    private val mTabEntities = ArrayList<CustomTabEntity>()

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("历史管理", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()


        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i],0, 0))
        }

        mFragments.add(SportHistoryFragment.newInstance(Type.ALL))
        mFragments.add(SportHistoryFragment.newInstance(Type.WEEK))
        mFragments.add(SportHistoryFragment.newInstance(Type.MONTH))
        mFragments.add(SportHistoryFragment.newInstance(Type.TERM))

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)


        initViewpager()

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

        viewPager.offscreenPageLimit = 4;
        viewPager.currentItem = 0
    }

    override fun getLayoutId(): Int = R.layout.mine_sport_history_activity

    override fun newP(): PViod = PViod()


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

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(SportHistoryActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

}
