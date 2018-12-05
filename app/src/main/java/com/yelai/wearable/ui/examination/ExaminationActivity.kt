package com.yelai.wearable.ui.examination

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListActivity
import com.yelai.wearable.gone
import com.yelai.wearable.invisible
import com.yelai.wearable.model.ExaminationItem
import com.yelai.wearable.model.Page
import com.yelai.wearable.net.ExaminationContract
import com.yelai.wearable.net.ExaminationContract.Success
import com.yelai.wearable.net.PExamination
import com.yelai.wearable.visible
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.examination_category_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hr on 18/9/16.
 */

class ExaminationActivity : BaseListActivity<ExaminationItem,ExaminationContract.Presenter>(),ExaminationContract.View {

    override fun getLayoutId(): Int = R.layout.recyclerview_layout

    override fun newP(): PExamination = PExamination()

    override fun success(type: ExaminationContract.Success, data: Any) {
        if(type == Success.ExaminationItemList){
            list(Page(data as List<ExaminationItem>))
        }
    }

    override fun initAdapter(): SimpleRecAdapter<ExaminationItem, ViewHolder<ExaminationItem>>  = object : BaseAdapter<ExaminationItem>(context){
        override fun getLayoutId(): Int = R.layout.examination_category_item

        override fun onBindViewHolder(holder: ViewHolder<ExaminationItem>, position: Int) {

            val item = data[position]

            holder.setData(item)


            var lambda = {
                when(item.type){
                    "1"->{//身高体重

                        ScoreRecordHeightAndWeightActivity.launch(this@ExaminationActivity.context,item)
                    }

                    "5"->{//1000/800米
                        ScoreRecordRunStopWatchActivity.launch(this@ExaminationActivity.context,item)
                    }
                    "6"->{//50米跑步
                        ScoreRecordRunStopWatchActivity.launch(this@ExaminationActivity.context,item)
                    }

                    else->{
                        ScoreRecordSitupsActivity.launch(this@ExaminationActivity.context,item)
                    }
                }
            }

            //开始考试
            holder.itemView.tvStart.onClick {

                lambda()

//                //        ScoreRecordSitupsActivity.launch(this@ExaminationFragment.context)
//                //        ScoreRecordHeightAndWeightActivity.launch(this@ExaminationFragment.context)
//                //        ScoreRecordRunActivity.launch(this@ExaminationFragment.context)
//                Toast.makeText(this@ExaminationActivity.context,"开始考试", Toast.LENGTH_LONG).show()

            }

            //考试中，继续考试
            holder.itemView.tvContinue.onClick {

                lambda()

//                Toast.makeText(this@ExaminationActivity.context,"继续考试", Toast.LENGTH_LONG).show()

            }

            //考试结束编辑成绩
            holder.itemView.tvFinish.onClick {
                when(item.type){
                    "1"->{//身高体重
                        ScoreRecordHeightAndWeightActivity.launch(this@ExaminationActivity.context,item,true)
                    }

                    "5"->{//1000/800米
                        ScoreRecordRunActivity.launch(this@ExaminationActivity.context,item)

//                        ScoreRecordRunStopWatchActivity.launch(this@ExaminationActivity.context,item)
                    }
                    "6"->{//50米跑步
                        ScoreRecordRunActivity.launch(this@ExaminationActivity.context,item)

//                        ScoreRecordRunStopWatchActivity.launch(this@ExaminationActivity.context,item)
                    }

                    else->{
                        ScoreRecordSitupsActivity.launch(this@ExaminationActivity.context,item)
                    }
                }
//                Toast.makeText(this@ExaminationActivity.context,"编辑成绩", Toast.LENGTH_LONG).show()

            }

        }

        override fun setData(itemView: View, data: ExaminationItem) {
//            val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
//            options.scaleType = ImageView.ScaleType.FIT_XY
//            ILFactory.getLoader().loadResource(itemView.ivBackground, data.background, options)

//            itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
//            itemView.tvCourseType.text = "训练课";


            itemView.tvName.text = "考试项目: ${data.title}"

            val desc = arrayOf(itemView.tvDesc1,itemView.tvDesc2,itemView.tvDesc3)
            desc.forEach { it.gone() }

            data.tag.forEachIndexed { index, value ->
                if(index < 3){
                    desc[index].visible()
                    desc[index].text = value
                }
            }

            itemView.tvLeakNum.text = if(data.miss == "0"){""}else{"缺考${data.miss}人"}

            itemView.tvStart.invisible()
            itemView.tvFinish.gone()
            itemView.tvContinue.gone()
            when(data.status){
                "0"->{

                    itemView.tvStart.visible()
                }
                "1"->{

                    itemView.tvContinue.visible()
                }
                else->{

                    itemView.tvFinish.visible()
                }
            }
        }

    }

    val examId:String by lazy{intent.extras.getString("examId")}

    override fun onRefresh() {
        p.examinationItemList(examId)
    }

    override fun onLoadMore(page: Int) {}

    override fun divider() {
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
    }

    override fun initData(savedInstanceState: Bundle?) {

        super.initData(savedInstanceState)
        backgroundLayout.backgroundColorResource = R.color.white
        dividerLine.visibility = View.VISIBLE

        mToolbar.setMiddleText("选择体测项目", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

//        ScoreRecordSitupsActivity.launch(this@ExaminationFragment.context)
//        ScoreRecordHeightAndWeightActivity.launch(this@ExaminationFragment.context)
//        ScoreRecordRunActivity.launch(this@ExaminationFragment.context)

    }



    companion object {
        fun launch(activity: Context, examId:String) {
            Router.newIntent(activity as Activity)
                    .to(ExaminationActivity::class.java)
                    .putString("examId",examId)
                    .launch()
        }
    }
}
