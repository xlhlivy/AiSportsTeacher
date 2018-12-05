package com.yelai.wearable.ui.course

import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.yelai.wearable.R
import com.yelai.wearable.present.PViod

/**
 * Created by hr on 18/9/16.
 */

class InterestCourseFragment : KLazyFragment<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {
//        statusView.showEmpty(
//        contentLayout.showError()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_empty
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {

        fun newInstance(): InterestCourseFragment {
            val fragment = InterestCourseFragment()
            return fragment
        }
    }
}
