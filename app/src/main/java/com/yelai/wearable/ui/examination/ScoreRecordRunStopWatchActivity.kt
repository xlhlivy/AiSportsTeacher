package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.model.ExaminationItem
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.examination_record_run_stop_watch.*
import kotlinx.android.synthetic.main.examination_record_run_stop_watch_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by hr on 18/9/16.
 *
 * 跑步成绩第一步流程：
 *
 * 首先是停表记录成绩           ->ScoreRecordRunStopWatchActivity 测试的第一步记录成绩
 *
 * 第二步将成绩分配给学生       -> ScoreRecordRunAssignScoreActivity 分配成绩给每一名学生
 *
 * 第三步查看是否有需要修改的地方-> ScoreRecordRunActivity  成绩展示和修改界面
 *
 */

class ScoreRecordRunStopWatchActivity : BaseActivity<PViod>() {

    internal val adapter: Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText(item.type.toExamType(), ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("录入成绩")
        mToolbar.setRightTextVisibility(View.VISIBLE)
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected

        tvRequire.text = item.require
        tvScore.text = "录入成绩:  分钟"

//        backgroundLayout.backgroundColorResource = R.color.white

//        backgroundLayout.backgroundColorResource = R.color.white

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.layoutManager = GridLayoutManager(context, 3)

        contentLayout.recyclerView.addItemDecoration(DividerItemDecoration(context))


        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false

        contentLayout.loadingView(View.inflate(this, R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()


    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var time:Long = 0 //跑步用时，单位秒

    private var isStart = false

    val timeSpan:Long by lazy{if(item.type == "6"){50L}else{1L}}

    val millis:Long by lazy{if(item.type == "6"){50L}else{1000L}}

    /**
     * 开始计时
     */
    private fun startTimer() {
        isStart = true
        if (timer == null) {
            timer = Timer()
        }
        if (timerTask == null) {
            timerTask = object : TimerTask() {
                override fun run() {

                    time+=timeSpan
                    runOnUiThread {
                        tvTime.text = "时间要求:${if(item.type == "6"){time.to50Txt()}else{time.to800Txt()}}"
                    }
                }
            }
        }

        if (timer != null && timerTask != null) {

            timer!!.schedule(timerTask, millis, millis)
        }

    }

    /**
     * 结束计时
     */
    private fun stopTimer() {

        isStart = false
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }

    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun bindEvent() {
        super.bindEvent()

        rlStart.onClick {
            if(isStart){
                stopTimer()
                tvStart.text = "开始"
                tvReset.text = "复位"
            }else{
                startTimer()
                tvStart.text = "结束"
                tvReset.text = "计次"
            }
        }

        rlReset.onClick {
            if(isStart){//计次
                adapter.addData(listOf(time.toString()))
            }else{
                //复位
                time = 0
                tvTime.text = "时间要求:0"
                stopTimer()
            }
        }
    }



    override fun onToolbarActionsClick(which: Int, view: View) {
        super.onToolbarActionsClick(which, view)
        if (which == SimpleToolbar.RIGHT_TEXT){

//            var list = ArrayList<String>()
//
//            adapter.dataSource.forEach {
//                if(item.type == "6"){
//                    list.add(it)
//                }else{
//                    list.add((it.toLong() / 1000).toString())
//                }
//            }
//
            ScoreRecordRunAssignScoreActivity.launch(this,item,adapter.dataSource.map { it }.toCollection(ArrayList<String>()))
        }
    }




    override fun getLayoutId(): Int {
        return R.layout.examination_record_run_stop_watch
    }

    override fun newP(): PViod? {
        return null
    }

    val item by lazy{intent.extras.getSerializable("item") as ExaminationItem}

    companion object {
        fun launch(activity: Context,item:ExaminationItem) {
            Router.newIntent(activity as Activity)
                    .to(ScoreRecordRunStopWatchActivity::class.java)
                    .putSerializable("item",item)
                    .launch()
        }
    }


    internal inner  class Adapter(context: Context) : SimpleRecAdapter<String, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.examination_record_run_stop_watch_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(position,item)

        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(position: Int,data: String) {
                itemView.tvScore.text = if(item.type == "6"){data.toLong().to50Txt()}else{data.toLong().to800Txt()}
                itemView.tvNo.text = (position + 1).toString()
            }
        }
    }


    inner class DividerItemDecoration  constructor(context: Context) : Y_DividerItemDecoration(context) {

        override fun getDivider(itemPosition: Int): Y_Divider? {

            var color = ContextCompat.getColor(this@ScoreRecordRunStopWatchActivity.context,R.color.white)

            var divider: Y_Divider? = null
            when (itemPosition % 3) {
                0 ->
                    //每一行第一个显示rignt和bottom
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 2f, 0f, 0f)
                            .create()
                2 ->
                    //第二个显示Left和bottom
                    divider = Y_DividerBuilder()
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
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
