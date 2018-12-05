package com.flyco.tablayout.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /** Fragment切换数组 */
    private ArrayList<Fragment> mFragments;
    /** 当前选中的Tab */
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        initFragments();
    }

    /** 初始化fragments */
    private void initFragments() {
        for (Fragment fragment : mFragments) {

//            if (!fragment.isAdded()&&null == mFragmentManager.findFragmentByTag( tag )) {
//                FragmentTransaction ft = mFragmentManager.beginTransaction();
//                mFragmentManager.executePendingTransactions();
//                ft.add(mContainerViewId, fragment, tag );
//                ft.commitAllowingStateLoss();
//            }

            if (!fragment.isAdded()){
                mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment).commit();
            }
        }

        setFragments(0);
    }

    /** 界面切换控制 */
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                ft.show(fragment);
                fragment.setUserVisibleHint(true);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        mCurrentTab = index;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }
}