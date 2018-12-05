package com.yelai.wearable.ui.examination

import android.os.Bundle
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.yelai.wearable.*
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListFragment
import com.yelai.wearable.model.Examination
import com.yelai.wearable.model.Page
import com.yelai.wearable.net.ExaminationContract
import com.yelai.wearable.net.ExaminationContract.Success
import com.yelai.wearable.net.PExamination
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.examination_fragment.*
import kotlinx.android.synthetic.main.examination_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hr on 18/9/16.
 */

class ExaminationFragment : BaseListFragment<Examination,ExaminationContract.Presenter>(),ExaminationContract.View {

    override fun getLayoutId(): Int = R.layout.examination_fragment

    override fun newP(): PExamination = PExamination()

    override fun success(type: ExaminationContract.Success, data: Any) {
        if(type == Success.ExaminationList){
            list(Page(data as List<Examination>))
        }else if(type == Success.ExaminationInfo){
            val examination = data as Examination
            EditClassActivity.launch(this@ExaminationFragment.context,examination)
        }
    }

    override fun initAdapter(): SimpleRecAdapter<Examination, ViewHolder<Examination>>  = object : BaseAdapter<Examination>(context){
        override fun getLayoutId(): Int = R.layout.examination_item

        override fun onBindViewHolder(holder: ViewHolder<Examination>, position: Int) {

            val item = data[position]

            holder.setData(item)

            holder.itemView.tvStart.onClick {
                ExaminationActivity.launch(this@ExaminationFragment.context,item.id)
            }

            holder.itemView.tvContinue.onClick {
                ExaminationActivity.launch(this@ExaminationFragment.context,item.id)
            }


            holder.itemView.tvEdit.onClick {
                p.examinationInfo(item.id)
            }

        }

        override fun setData(itemView: View, data: Examination) {
//            val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
//            options.scaleType = ImageView.ScaleType.FIT_XY
//            ILFactory.getLoader().loadResource(itemView.ivBackground, data.background, options)

//            itemView.tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
//            itemView.tvCourseType.text = "训练课";

            itemView.tvCourseType.visibility = View.GONE


            itemView.tvCourseName.text = data.title

            itemView.tvCourseDetail.text = "${data.teacherName}"

            itemView.tvStart.gone()
            itemView.tvEdit.gone()
            itemView.tvContinue.gone()
            itemView.tvFinish.gone()

            itemView.ivBackground.load(R.drawable.course_item_background_banner1)


            itemView.ivBackground.load(data.img)


            when(data.status){
                "0"->{

                    itemView.tvStart.visible()
                    itemView.tvEdit.visible()

                }
                "1"->{

                    itemView.tvContinue.visible()

                }
                "2"->{

                    itemView.tvFinish.visible()

                }
            }

        }

    }

    override fun onStartLazy() {

        if(AppData.isBackFromPageWithDataAndCleanData(AddClassActivity::class.java)){
            contentLayout.recyclerView.refreshData()
        }

    }

    override fun onRefresh() {
        p.examinationList()
    }

    override fun onLoadMore(page: Int) {}

    override fun divider() {
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        backgroundLayout.backgroundColorResource = R.color.white

//        ScoreRecordSitupsActivity.launch(this@ExaminationFragment.context)
//        ScoreRecordHeightAndWeightActivity.launch(this@ExaminationFragment.context)
//        ScoreRecordRunActivity.launch(this@ExaminationFragment.context)

        tvAddClass.onClick { AddClassActivity.launch(this@ExaminationFragment.context) }

    }



    companion object {

        fun newInstance(): ExaminationFragment {
            val fragment = ExaminationFragment()
            return fragment
        }
    }
}
