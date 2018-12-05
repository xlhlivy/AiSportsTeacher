package com.yelai.wearable.ui.course

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yelai.wearable.R
import com.yelai.wearable.entity.MessageEntity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.course_item_message.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * Created by hr on 18/9/16.
 */

class CourseMessageBoardFragment : KLazyFragment<PViod>() {

    internal val adapter:Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()


        adapter.addData(listOf(MessageEntity("张三","7小时💰","巴拉巴拉",1),MessageEntity("张三","7小时💰","巴拉巴拉",1),MessageEntity("张三","7小时💰","巴拉巴拉",1)))

        adapter.notifyDataSetChanged()

    }


    override fun getLayoutId(): Int {
        return R.layout.course_fragment_message_board
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {

        fun newInstance(): CourseMessageBoardFragment {
            val fragment = CourseMessageBoardFragment()
            return fragment
        }
    }

    internal inner  class Adapter(context: Context) : SimpleRecAdapter<MessageEntity,Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.course_item_message
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {
                if (recItemClick != null) {
//                    this@CourseMessageBoardFragment.callback?.callback(item)
                }
            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: MessageEntity) {
                itemView.tvUserName.text = data.username
                itemView.tvTime.text = data.time
                itemView.tvContent.text = data.content
            }

        }
    }
}
