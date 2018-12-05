package com.yelai.wearable.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import java.io.Serializable

abstract class ViewHolder<D : Serializable>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun setData(data: D);

}