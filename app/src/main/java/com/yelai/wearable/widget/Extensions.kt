package com.yelai.wearable.widget

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v7.widget.DefaultItemAnimator
import cn.droidlover.xrecyclerview.XRecyclerView
import cn.droidlover.xrecyclerview.divider.HorizontalDividerItemDecoration

/**
 * Created by xuhao on 2017/11/14.
 */

fun XRecyclerView.horizontalDividerMargin(@ColorRes colorId:Int,@DimenRes sizeId:Int,@DimenRes leftMarginId:Int,@DimenRes rightMarginId:Int ):XRecyclerView{

    itemAnimator = DefaultItemAnimator()
    setHasFixedSize(true)
    addItemDecoration(HorizontalDividerItemDecoration.Builder(context)
            .colorResId(colorId)
            .size(context.resources.getDimensionPixelSize(sizeId))
            .margin(context.resources.getDimensionPixelSize(leftMarginId),context.resources.getDimensionPixelSize(rightMarginId))
            .build()
    )

    return this
}








