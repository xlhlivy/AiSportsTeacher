package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.model.Examination
import com.yelai.wearable.model.ExaminationUpdater
import com.yelai.wearable.net.ExaminationContract
import com.yelai.wearable.net.PExamination
import com.yelai.wearable.present.PViod
import com.yelai.wearable.ui.course.StudentsFragment
import kotlinx.android.synthetic.main.course_activity_detail.*
import org.jetbrains.anko.textColorResource
import java.util.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class EditClassActivity : BaseActivity<ExaminationContract.Presenter>(),ExaminationContract.View{


    override fun getLayoutId(): Int = R.layout.examination_activity_add_class

    override fun newP(): PExamination = PExamination()


    override fun success(type: ExaminationContract.Success, data: Any) {
        if(type == ExaminationContract.Success.SaveExamination){

            AppData.backWithData(EditClassActivity::class.java)

            AppManager.finishCurrentActivity()

        }
    }


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("班级选择", "扫码加入")
    private val mTabEntities = ArrayList<CustomTabEntity>()

    private val choiceClassFragment by lazy{ChoiceClassFragment.newInstance(exam != null)}
    private val scanJoinFragment by lazy{ScanJoinFragment.newInstance(exam != null)}

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("编辑体测", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("保存")
        mToolbar.setRightTextVisibility(View.VISIBLE)
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected


        if(exam != null){
            if(isScan){
                mFragments.add(scanJoinFragment)
            }else{
                mFragments.add(choiceClassFragment)
            }
            tabLayout.visibility = View.GONE
            mTabEntities.add(TabEntity(mTitles[0],0, 0))

        }else{
            for (i in mTitles.indices) {
                mTabEntities.add(TabEntity(mTitles[i],0, 0))
            }

            mFragments.add(choiceClassFragment)
            mFragments.add(scanJoinFragment)
        }


        viewPager.adapter = MyPagerAdapter(supportFragmentManager)

        initViewpager()

        if(isScan){
            scanJoinFragment.initViewWithData(exam!!)

        }else{
            choiceClassFragment.initViewWithData(exam!!)
        }

    }

    override fun onToolbarActionsClick(which: Int, view: View) {
        super.onToolbarActionsClick(which, view)
        if (which == SimpleToolbar.RIGHT_TEXT){

            val examination = ExaminationUpdater()
            if(exam == null){
                if(tabLayout.currentTab == 0 && choiceClassFragment.collectionData(examination)){
                    p.saveExamination(examination)

                }else if(tabLayout.currentTab == 1 && scanJoinFragment.collectionData(examination)){

                    p.saveExamination(examination)

                }
            }else{
                if((isScan && scanJoinFragment.collectionData(examination)) || (!isScan && choiceClassFragment.collectionData(examination))){
                    p.saveExamination(examination)
                }
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

    val exam by lazy{intent.extras.getSerializable("examination") as Examination?}

    val isScan by lazy{exam!!.type == "2"}

    companion object {
        fun launch(activity: Context,examination:Examination) {
            Router.newIntent(activity as Activity)
                    .to(EditClassActivity::class.java)
                    .putSerializable("examination",examination)
                    .launch()
        }
    }

}
