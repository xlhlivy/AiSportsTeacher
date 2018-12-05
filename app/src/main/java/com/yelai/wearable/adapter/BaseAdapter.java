package com.yelai.wearable.adapter;

import android.content.Context;
import android.view.View;

import com.yelai.wearable.adapter.ViewHolder;import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;

public abstract class BaseAdapter<D extends Serializable> extends SimpleRecAdapter<D,ViewHolder<D>>{

    public BaseAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder<D> newViewHolder(View itemView) {
        return new ViewHolder<D>(itemView) {
            @Override
            public void setData(@NotNull D data) {
                BaseAdapter.this.setData(itemView,data);
            }
        };
    }

    public void setData(@NotNull View itemView,@NotNull D data){}

}
