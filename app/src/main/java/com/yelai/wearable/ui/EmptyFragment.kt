package com.yelai.wearable.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.droidlover.xdroidmvp.mvp.KLazyFragment

import com.yelai.wearable.R
import com.yelai.wearable.present.PViod

import cn.droidlover.xdroidmvp.mvp.XLazyFragment
import kotlinx.android.synthetic.main.fragment_empty.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find

/**
 * Created by hr on 18/9/16.
 */

class EmptyFragment : KLazyFragment<PViod>() {

    private var title: String? = null

    override fun initData(savedInstanceState: Bundle?) {
        tvText.text = title
//        roundView.setAngle(180.0f);
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_empty
    }

    override fun newP(): PViod? {
        return null
    }

    companion object {

        fun newInstance(title: String): EmptyFragment {
            val fragment = EmptyFragment()
            fragment.title = title
            return fragment
        }
    }
}
