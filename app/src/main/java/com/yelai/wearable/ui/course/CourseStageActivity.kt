package com.yelai.wearable.ui.course

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.load
import com.yelai.wearable.model.CourseTimeDetail
import com.yelai.wearable.model.CourseTimeStep
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.course_activity_stage.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class CourseStageActivity : BaseActivity<CourseContract.Presenter>(), CourseContract.View{


    override fun success(type: CourseContract.Success, data: Any) {

        when(type) {

            CourseContract.Success.CourseTimesStep->{

                data as List<CourseTimeStep>

                contentLayout.recyclerView.setPage(1, 1)

                var list = ArrayList<CourseTimeStep>()

                data.forEach {
                    list.add(it.apply { isParent = true })
                    list.addAll(it.child)
                }

                adapter.setData(list)

            }

            CourseContract.Success.CourseTimesStepSave->{
                showToast("保存成功")
                p.courseTimesStep(courseTimeDetail.times_id)
            }

//            CourseContract.Success.CourseTimesStepAdd->{
//                p.courseTimesStep(courseTimeDetail.timesId)
//            }
//
//            CourseContract.Success.CourseTimesStepDel->{
//                p.courseTimesStep(courseTimeDetail.timesId)
//
//            }
//
//            CourseContract.Success.CourseTimesStepUpdate->{
//                p.courseTimesStep(courseTimeDetail.timesId)
//
//
//            }


        }

    }


    private fun rebuildAdapterData(){

        var list = ArrayList<CourseTimeStep>()

        adapter.dataSource.filter { it.isParent }.forEach {
            list.add(it.apply { isParent = true })
            list.addAll(it.child)

            val self = it
            it.child.forEach {
                it.parent = self
                it.isParent = false
            }
        }

        adapter.setData(list)

    }

    private val mStageViewDialog by lazy { StageViewDialog(context,object :StageViewDialog.Callback{

        override fun done(step: CourseTimeStep) {

            if(step.method == 1){//添加
                step.parent.child.add(step)

                rebuildAdapterData()

            }else if(step.method == 2){//修改
//                p.courseTimesStepUpdate(step.id,step.name,step.time)
            }
        }
     })
    }

    internal val adapter: Adapter by lazy {
        Adapter(context)
    }

    private val HEADER :Int = 1
    private val CONTENT:Int = 2

    private val courseTimeDetail by lazy{intent.extras.getSerializable("detail") as CourseTimeDetail}





    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("课程阶段", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()

        mToolbar.setRightText("保存")
        mToolbar.setRightTextVisibility(View.VISIBLE)


        when(courseTimeDetail.type){
            "1"->{
                tvCourseType.text = "教学课"
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_green_background)
                ivBackground.load(R.drawable.course_item_background_banner1)

            }

            "2"->{
                tvCourseType.text = "训练课"
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                ivBackground.load(R.drawable.course_item_background_banner2)

            }

            "3"->{
                tvCourseType.text = "兴趣课"
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_red_background)
                ivBackground.load(R.drawable.course_item_background_banner3)


            }
            else->{
                tvCourseType.text = "训练课"
                tvCourseType.setBackgroundResource(R.drawable.day_right_circle_orange_background)
                ivBackground.load(R.drawable.course_item_background_banner2)

            }
        }

        tvCourseName.text = courseTimeDetail.title

        tvCourseDetail.text = "课次: ${courseTimeDetail.number}/${courseTimeDetail.times}"

        tvCourseTime.text = "上课时间: ${courseTimeDetail.start_time}"






        backgroundLayout.backgroundColorResource = R.color.white
        contentLayout.backgroundColorResource = R.color.page_bg_color


        contentLayout.recyclerView.verticalLayoutManager(context)
//        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
//        contentLayout.recyclerView.layoutManager = GridLayoutManager(context, 4)
//
//        contentLayout.recyclerView.addItemDecoration(DividerItemDecoration(context))


        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null))


        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

        p.courseTimesStep(courseTimeDetail.times_id)


//        adapter.addData(listOf(MessageEntity("张三","7小时💰","巴拉巴拉",0),
//                MessageEntity("张三","7小时💰","巴拉巴拉",1),
//                MessageEntity("张三","7小时💰","巴拉巴拉",2),
//                MessageEntity("张三","7小时💰","巴拉巴拉",3),
//                MessageEntity("张三","7小时💰","巴拉巴拉",4),
//                MessageEntity("张三","7小时💰","巴拉巴拉",5),
//                MessageEntity("张三","7小时💰","巴拉巴拉",6),
//                MessageEntity("张三","7小时💰","巴拉巴拉",7),
//                MessageEntity("张三","7小时💰","巴拉巴拉",8),
//                MessageEntity("张三","7小时💰","巴拉巴拉",9),
//                MessageEntity("张三","7小时💰","巴拉巴拉",10)))
//
//        adapter.notifyDataSetChanged()

    }

    override fun onToolbarActionsClick(which: Int, view: View?) {
        super.onToolbarActionsClick(which, view)
        if (which == SimpleToolbar.RIGHT_TEXT){
            val step = adapter.dataSource.filter { it.isParent }.toList()

            val list = ArrayList<CourseTimeStep>()

            adapter.dataSource.forEach {
                if(it.isParent){
                    list.add(it)

                    if(it.child != null){
                        it.child.forEach {
                            it.child = null
                            it.isParent = false
                            it.parent = null
                        }
                    }
                }
            }

            try{

                val data = AppData.gson.toJson(list)
                println(data)

                p.courseTimesStepSave(mapOf(
                        "times_id" to courseTimeDetail.times_id,
                        "step" to step
                ))

            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }




    override fun getLayoutId(): Int = R.layout.course_activity_stage

    override fun newP(): PCourse = PCourse()


    internal inner  class Adapter(context: Context) : SimpleRecAdapter<CourseTimeStep, Adapter.ViewHolder>(context) {

        override fun getItemViewType(position: Int): Int {
            return if(data[position].isParent){HEADER}else{CONTENT}
        }

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(if(viewType == 1) R.layout.course_stage_item1 else layoutId, parent, false)
            return newViewHolder(view)
        }

        override fun getLayoutId(): Int {
            return  R.layout.course_stage_item2
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item = data[position]

            holder.setData(item)

            if(item.isParent){
                holder.tvAdd.onClick {

                    val step = CourseTimeStep().apply {
                        method = 1
                        name = "热身"
                        time = "5"
                        parent = item
                    }

                    mStageViewDialog.courseTimeStep = step
                    mStageViewDialog.show()
                }

                holder.tvEdit.onClick {
                    item.method = 2
                    mStageViewDialog.courseTimeStep = item
                    mStageViewDialog.show()
                }

            }else{
                holder.tvEdit.onClick {
                    item.method = 2
                    mStageViewDialog.courseTimeStep = item
                    mStageViewDialog.show()
                }

                holder.tvDel.onClick {
                    item.parent.child.remove(item)
                    rebuildAdapterData()
                }
            }

        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val tvName by lazy{itemView.find<TextView>(R.id.tvName)}

            val tvTime by lazy{itemView.find<TextView>(R.id.tvTime)}

            val tvAdd:TextView by lazy{itemView.find<TextView>(R.id.tvAdd)}

            val tvEdit:TextView by lazy{itemView.find<TextView>(R.id.tvEdit)}

            val tvDel:TextView by lazy{itemView.find<TextView>(R.id.tvDel)}


            fun setData(data: CourseTimeStep) {

                tvName.text = data.name

                tvTime.text = "${data.time}分钟"
            }

        }
    }


    companion object {
        fun launch(activity: Context,detail:CourseTimeDetail) {
            Router.newIntent(activity as Activity)
                    .to(CourseStageActivity::class.java)
                    .putSerializable("detail",detail)
                    .launch()
        }
    }

}
