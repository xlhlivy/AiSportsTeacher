package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import com.jakewharton.rxbinding2.widget.RxTextView
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
import kotlinx.android.synthetic.main.examination_record_height_weight_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.textColorResource

/**
 * Created by hr on 18/9/16.
 */

class ScoreRecordHeightAndWeightActivity : BaseListActivity<ExaminationScore,ExaminationContract.Presenter>(),ExaminationContract.View {

    override fun success(type: ExaminationContract.Success, data: Any) {
        if(type == ExaminationContract.Success.ExaminationResult){

            list(Page(data as List<ExaminationScore>))
        }else if(type == ExaminationContract.Success.SaveExaminationResult){
            showToast("保存成绩成功")
            contentLayout.recyclerView.refreshData()
        }
    }

    override fun initAdapter(): SimpleRecAdapter<ExaminationScore, ViewHolder<ExaminationScore>> = object:BaseAdapter<ExaminationScore>(context){

        override fun getLayoutId(): Int = R.layout.examination_record_height_weight_item

        override fun onBindViewHolder(holder: ViewHolder<ExaminationScore>, position: Int) {
            val item = data[position]

            holder.setData(item)
        }

        override fun setData(itemView: View, data: ExaminationScore) {
            itemView.tvName.text = data.name
            itemView.etHeight.text = SpannableStringBuilder(data.height)
            itemView.etWeight.text = SpannableStringBuilder(data.weight)
            RxTextView.textChanges(itemView.etHeight).filter { it.trim().toString() != data.height }.subscribe { data.height = it.trim().toString() }
            RxTextView.textChanges(itemView.etWeight).filter { it.trim().toString() != data.weight }.subscribe { data.weight = it.trim().toString() }
        }

    }

    override fun onRefresh() {
        p.examinationResult(item.id)
    }

    override fun onLoadMore(page: Int) {}

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        mToolbar.setMiddleText( if(isEdit){"身高体重成绩编辑"} else{"身高体重考试"}, ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("保存")
        mToolbar.setRightTextVisibility(View.VISIBLE)
        mToolbar.tvRight.textColorResource = R.color.tab_text_selected

    }

    override fun onToolbarActionsClick(which: Int, view: View?) {
        super.onToolbarActionsClick(which, view)
        if(which == SimpleToolbar.RIGHT_TEXT){

            p.saveExaminationResult(item.id,adapter.dataSource)

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.examination_record_height_weight
    }

    override fun newP(): PExamination = PExamination()


    val item by lazy{intent.extras.getSerializable("item") as ExaminationItem}
    val isEdit by lazy{intent.extras.getBoolean("isEdit",false)}
    companion object {
        fun launch(activity: Context,item:ExaminationItem,isEdit:Boolean = false) {
            Router.newIntent(activity as Activity)
                    .to(ScoreRecordHeightAndWeightActivity::class.java)
                    .putSerializable("item",item)
                    .putBoolean("isEdit",isEdit)
                    .launch()
        }
    }


}
