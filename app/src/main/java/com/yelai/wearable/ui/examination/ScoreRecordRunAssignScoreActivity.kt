package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.BasePickerView
import com.bigkoo.pickerview.view.OptionsPickerView
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListActivity
import com.yelai.wearable.model.ExaminationItem
import com.yelai.wearable.model.ExaminationScore
import com.yelai.wearable.model.Page
import com.yelai.wearable.model.Student
import com.yelai.wearable.net.ExaminationContract
import com.yelai.wearable.net.PExamination
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.examination_record_run_assign_score_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.textColorResource

/**
 * Created by hr on 18/9/16.
 */

class ScoreRecordRunAssignScoreActivity : BaseListActivity<Student,ExaminationContract.Presenter>(),ExaminationContract.View {

    override fun success(type: ExaminationContract.Success, data: Any) {
        if(type == ExaminationContract.Success.ExaminationResult){
//            list(Page(data as List<ExaminationScore>))
        }else if(type == ExaminationContract.Success.SaveExaminationResult){
            showToast("保存成绩成功")
            ScoreRecordRunActivity.launch(context,item)
//            contentLayout.recyclerView.refreshData()
        }else if(type == ExaminationContract.Success.ExaminationStudents){
            list(Page(data as List<Student>))
        }
    }

    private var tempData:ExaminationScore? = null

    override fun initAdapter(): SimpleRecAdapter<Student, ViewHolder<Student>> = object: BaseAdapter<Student>(context){

        override fun getLayoutId(): Int = R.layout.examination_record_run_assign_score_item

        override fun onBindViewHolder(holder: ViewHolder<Student>, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {
                if(scores.size != 0){
                    pvStatusOptions.show()
//                    tempData = item
                }
            }
        }

        override fun setData(itemView: View, data: Student) {
            itemView.tvName.text = data.name
            itemView.tvRank.visibility = View.GONE
            itemView.tvTime.text = data.timesId
//            if(data.rank != null && data.rank.isNotEmpty()){
//                itemView.tvRank.text = data.rank
//                itemView.tvRank.visibility = View.VISIBLE
//            }
        }

    }


    override fun onRefresh() {
//        p.examinationResult(item.id)
        p.examinationStudents(item.id)
    }

    override fun onLoadMore(page: Int) {}

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("成绩录入", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("保存")
        mToolbar.setRightTextVisibility(View.VISIBLE)
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected


        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.layoutManager = GridLayoutManager(context, 5)
        contentLayout.recyclerView.addItemDecoration(DividerItemDecoration(context))

        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                this@ScoreRecordRunAssignScoreActivity.onRefresh()
            }

            override fun onLoadMore(page: Int) {
                this@ScoreRecordRunAssignScoreActivity.onLoadMore(page)
            }
        }
        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

        contentLayout.recyclerView.refreshData()

    }

    override fun onToolbarActionsClick(which: Int, view: View?) {
        super.onToolbarActionsClick(which, view)
        if(which == SimpleToolbar.RIGHT_TEXT){
//            adapter.dataSource.forEach { it.result = it.time }
//            p.saveExaminationResult(item.id,adapter.dataSource)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PExamination = PExamination()

    val item by lazy{intent.extras.getSerializable("item") as ExaminationItem}

    val scores by lazy{intent.extras.getSerializable("scores") as ArrayList<String>}

    val scoreText by lazy{scores.map { if(item.type == "6"){it.toLong().to50Txt() }else{it.toLong().to800Txt()}}.toList()}

    companion object {
        fun launch(activity: Context,item:ExaminationItem,scores:ArrayList<String>) {
            Router.newIntent(activity as Activity)
                    .to(ScoreRecordRunAssignScoreActivity::class.java)
                    .putSerializable("scores", scores)
                    .putSerializable("item",item)
                    .launch()
        }
    }


    val pvStatusOptions:BasePickerView by lazy {

        var scoreTextWithRank = ArrayList<String>()

        scoreText.forEachIndexed { index, s ->
            scoreTextWithRank.add("第${index+1}名  ${s}")
        }

        buildTimeRequire().apply { this.setPicker(scoreTextWithRank)}
    }

    //
    fun buildTimeRequire(): OptionsPickerView<String> {


        val pvTime = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, v ->
            if(tempData != null){
                tempData!!.time = scores[options1]
                tempData!!.timeStr = scoreText[options1]
                tempData!!.rank = (options1 + 1).toString()

                adapter.notifyDataSetChanged()
            }

        })

                .isDialog(true)
                .build<String>()

        val mDialog = pvTime.dialog


//
        if (mDialog != null) {


            val windowManager = mDialog.window!!.windowManager
            val display = windowManager.defaultDisplay
            val lp = mDialog.window!!.attributes
            lp.width = (display.width).toInt() //设置宽度
            mDialog.window!!.attributes = lp



            val containerParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM)

            containerParams.leftMargin = 0
            containerParams.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = containerParams


            val contentParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            val viewGroup = pvTime.dialogContainerLayout.getChildAt(0) as ViewGroup

            viewGroup.layoutParams = contentParams


            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }



//        pvTime.setPicker(listOf("5分10秒","5分11秒","5分12秒","5分13秒","5分14秒","5分15秒"))

        return pvTime;
    }


    inner class DividerItemDecoration  constructor(context: Context) : Y_DividerItemDecoration(context) {

        override fun getDivider(itemPosition: Int): Y_Divider? {

            var color = ContextCompat.getColor(this@ScoreRecordRunAssignScoreActivity.context,R.color.white)

            var divider: Y_Divider? = null
            when (itemPosition % 5) {
                0 ->
                    //每一行第一个显示rignt和bottom
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 15f, 0f, 0f)
                            .setBottomSideLine(true, color, 2f, 0f, 0f)
                            .create()
                4 ->
                    //第二个显示Left和bottom
                    divider = Y_DividerBuilder()
                            .setLeftSideLine(true, color, 15f, 0f, 0f)
                            .setRightSideLine(true, color, 0f, 0f, 0f)
                            .setBottomSideLine(true, color, 2f, 0f, 0f)
                            .create()
                else -> {
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 2f, 0f, 0f)
                            .create()
                }
            }
            return divider
        }
    }


}
