package com.yelai.wearable.adapter

import com.yelai.wearable.entity.CourseEntity

import cn.droidlover.xrecyclerview.RecyclerItemCallback

/**
 * Created by hr on 18/10/11.
 */

open class ItemClick<T, F> : RecyclerItemCallback<T, F>() {

    override fun onItemClick(position: Int, model: T?, tag: Int, holder: F?) {
        super.onItemClick(position, model, tag, holder)
    }

    override fun onItemLongClick(position: Int, model: T?, tag: Int, holder: F?) {
        super.onItemLongClick(position, model, tag, holder)
    }

}
