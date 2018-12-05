package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.entity.CategoryEntity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.examination_category_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.textColorResource

/**
 * Created by hr on 18/9/16.
 */

class ChoiceCategoryActivity : BaseActivity<PViod>() {

    internal val adapter: Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("选择体侧项目", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("提交")
        mToolbar.setRightTextVisibility(View.VISIBLE)
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected

        dividerLine.visibility = View.VISIBLE

        backgroundLayout.backgroundColorResource = R.color.white

//        backgroundLayout.backgroundColorResource = R.color.white

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
//        contentLayout.recyclerView.layoutManager = GridLayoutManager(context, 2)


        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(this, R.layout.view_loading, null))


        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()


        adapter.addData(listOf(
                CategoryEntity("身高体重", listOf("身高(米)","体重(公斤)"),"缺考3人",0,1),
                CategoryEntity("握力", listOf("力度(公斤)"),"缺考2人",1,2),
                CategoryEntity("引体向上", listOf("个数"),"",0,3),
                CategoryEntity("仰卧起坐", listOf("个数"),"缺考2人",0,4)
                ))

        adapter.notifyDataSetChanged()

    }


    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(ChoiceCategoryActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


    internal inner  class Adapter(context: Context) : SimpleRecAdapter<CategoryEntity, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.examination_category_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {

//                when(position){
//                    0->{
//                        ScoreRecordRunStopWatchActivity.launch(this@ChoiceCategoryActivity.context)
//                    }
//                    1->{
//                        ScoreRecordRunActivity.launch(this@ChoiceCategoryActivity.context)
//                    }
//                    2->{
//                        ScoreRecordSitupsActivity.launch(this@ChoiceCategoryActivity.context)
//                    }
//                    else->{
//                        ScoreRecordHeightAndWeightActivity.launch(this@ChoiceCategoryActivity.context)
//                    }
//                }


//                if (recItemClick != null) {
////                    this@CourseMessageBoardFragment.callback?.callback(item)
//                }
            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: CategoryEntity) {
                itemView.tvName.text = "考试项目:" + data.name
                itemView.tvLeakNum.text = data.leakNum


                itemView.tvDesc1.visibility = View.INVISIBLE
                itemView.tvDesc2.visibility = View.INVISIBLE
                itemView.tvDesc3.visibility = View.INVISIBLE


                data.description.forEachIndexed { index, value ->
                    run {

                        when(index){
                            0->{
                                itemView.tvDesc1.text = value
                                itemView.tvDesc1.visibility = View.VISIBLE
                            }
                            1->{
                                itemView.tvDesc2.text = value
                                itemView.tvDesc2.visibility = View.VISIBLE
                            }
                            2->{
                                itemView.tvDesc3.text = value
                                itemView.tvDesc3.visibility = View.VISIBLE

                            }
                        }
                    }
                }

                itemView.tvStart.text = if (data.status == 0) {"开始考试"} else "编辑成绩"


//                itemView.tvName.text = data.username
//                itemView.tvUserName.text = data.username
//                itemView.tvTime.text = data.time
//                itemView.tvContent.text = data.content
            }

        }
    }


}
