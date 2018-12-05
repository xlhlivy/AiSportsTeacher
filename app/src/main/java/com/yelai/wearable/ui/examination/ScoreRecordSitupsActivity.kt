package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.SpannableStringBuilder
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
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
import com.yelai.wearable.net.ExaminationContract
import com.yelai.wearable.net.PExamination
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.examination_record_situps_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.textColorResource

/**
 * Created by hr on 18/9/16.
 */

class ScoreRecordSitupsActivity : BaseListActivity<ExaminationScore,ExaminationContract.Presenter>(),ExaminationContract.View {


    override fun success(type: ExaminationContract.Success, data: Any) {
        if(type == ExaminationContract.Success.ExaminationResult){

            list(Page(data as List<ExaminationScore>))
        }else if(type == ExaminationContract.Success.SaveExaminationResult){
            showToast("保存成绩成功")
            contentLayout.recyclerView.refreshData()
        }
    }


    override fun initAdapter(): SimpleRecAdapter<ExaminationScore, ViewHolder<ExaminationScore>>  = object: BaseAdapter<ExaminationScore>(context){

        override fun getLayoutId(): Int = R.layout.examination_record_situps_item

        override fun onBindViewHolder(holder: ViewHolder<ExaminationScore>, position: Int) {
            val item = data[position]

            holder.setData(item)
        }

        override fun setData(itemView: View, data: ExaminationScore) {
            itemView.tvName.text = data.name
            itemView.etScore.text = SpannableStringBuilder(data.result)
            RxTextView.textChanges(itemView.etScore).filter { it.trim().toString() != data.result }.subscribe {
                data.result = it.trim().toString()
            }

        }

    }

    override fun onRefresh() {
        p.examinationResult(item.id)
    }

    override fun onLoadMore(page: Int) {}


    override fun initData(savedInstanceState: Bundle?) {

        val title = if(isEdit){"编辑${item.type.toExamType()}成绩"}else{item.type.toExamType()}

        mToolbar.setMiddleText(title, ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("保存")
        mToolbar.setRightTextVisibility(View.VISIBLE)
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected

        backgroundLayout.backgroundColorResource = R.color.white

        contentLayout.recyclerView.layoutManager = GridLayoutManager(context, 2)

        contentLayout.recyclerView.addItemDecoration(DividerItemDecoration(context))

        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

//        contentLayout.recyclerView.isRefreshEnabled = false

        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                this@ScoreRecordSitupsActivity.onRefresh()
            }

            override fun onLoadMore(page: Int) {
                this@ScoreRecordSitupsActivity.onLoadMore(page)
            }
        }

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

        contentLayout.recyclerView.refreshData()

    }


    override fun onToolbarActionsClick(which: Int, view: View?) {
        super.onToolbarActionsClick(which, view)
        if(which == SimpleToolbar.RIGHT_TEXT){

            p.saveExaminationResult(item.id,adapter.dataSource)

        }
    }



    override fun getLayoutId(): Int {
        return R.layout.examination_record_situps
    }

    override fun newP(): PExamination = PExamination()


    val item by lazy{intent.extras.getSerializable("item") as ExaminationItem }
    val isEdit by lazy{intent.extras.getBoolean("isEdit",false)}


    companion object {
        fun launch(activity: Context,item:ExaminationItem,isEdit:Boolean = false) {
            Router.newIntent(activity as Activity)
                    .to(ScoreRecordSitupsActivity::class.java)
                    .putSerializable("item",item)
                    .putBoolean("isEdit",isEdit)
                    .launch()
        }
    }


    inner class DividerItemDecoration  constructor(context: Context) : Y_DividerItemDecoration(context) {

        override fun getDivider(itemPosition: Int): Y_Divider? {

            var color = ContextCompat.getColor(this@ScoreRecordSitupsActivity.context,R.color.white)

            var divider: Y_Divider? = null
            when (itemPosition % 2) {
                0 ->
                    //每一行第一个显示rignt和bottom
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 2f, 0f, 0f)
                            .create()
                1 ->
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
