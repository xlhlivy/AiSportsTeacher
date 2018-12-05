package com.yelai.wearable.ui.sport

import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.yelai.wearable.R
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.sport_fragment.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hr on 18/9/16.
 */

class SportFragment : KLazyFragment<PViod>() {


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun bindEvent() {
        super.bindEvent()

        btnGo.onClick {  }

        btnSportTypeChoice.onClick {
            SportTypeChoiceActivity.launch(this@SportFragment.context)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.sport_fragment
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {

        fun newInstance(): SportFragment {
            val fragment = SportFragment()
            return fragment
        }
    }
}
