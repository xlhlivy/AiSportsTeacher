package com.yelai.wearable.ui.course

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.CourseTimeDetail
import com.yelai.wearable.model.Page
import com.yelai.wearable.model.Student
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.showToast
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.course_activity_detail_student_item.view.*
import kotlinx.android.synthetic.main.course_fragment_students.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hr on 18/9/16.
 */

class StudentsFragment : BaseListFragment<Student, CourseContract.Presenter>(), CourseContract.View {


    override fun success(type: CourseContract.Success, data: Any) {

        if(type == CourseContract.Success.StudentAssign){

            p.courseTimesDetail(courseTimeDetail!!.times_id)

            contentLayout.recyclerView.refreshData()

        }else if(type == CourseContract.Success.StudentStatus){

            list(Page<List<Student>>().apply {
                pages = 1
                currPage = 1
                count = 1
                this@apply.data = data as List<Student>
            })

        }else if(type == CourseContract.Success.CourseTimeDetail){
            courseTimeDetail = data as CourseTimeDetail
            onStartLazy()
        }

    }


    var courseTimeDetail : CourseTimeDetail? = null


    override fun list(pager: Page<List<Student>>) {
        if(contentLayout == null){
            adapter.setData(pager.data)
            return
        }
        super.list(pager)
    }


    override fun initAdapter(): SimpleRecAdapter<Student, ViewHolder<Student>> = Adapter(context)

    override fun onRefresh() {
        if(courseTimeDetail != null){
            p.studentStatus(courseTimeDetail!!.times_id)
        }
    }

    override fun onLoadMore(page: Int) {}




//    var courseStatus = 0  //0可以点名  1开始上课按钮  2已经开始上课了

    private val markAttendanceViewDialog by lazy{
        MarkAttendanceViewDialog(this@StudentsFragment.context,object:MarkAttendanceViewDialog.Callback{
            override fun done(memberId:String,status: String) {
                p.studentAssign(courseTimeDetail!!.times_id,memberId,status)
            }
        })
    }

    val statusObserver = PublishSubject.create<String>()
    var realStatus = "0"

    override fun initData(savedInstanceState: Bundle?) {
        contentLayout.recyclerView.layoutManager = GridLayoutManager(context, 4)
        contentLayout.recyclerView.addItemDecoration(DividerItemDecoration(context))
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)
        backgroundLayout.backgroundColorResource = R.color.white
        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.recyclerView.setPage(1, 1)
        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
//                p.courseTimesDetail(courseTimeDetail!!.times_id)
                p.studentStatus(courseTimeDetail!!.times_id)
                contentLayout.refreshState(false)
            }
            override fun onLoadMore(page: Int) {}
        }
        contentLayout.showContent()

        statusObserver.subscribe{

            if(it != realStatus){
                realStatus = it
                AppData.setCourseStatus(courseTimeDetail!!.times_id,it)
            }

            tvStage.alpha = .5f

            when(it){

                "0"->{//未开始
                    tvStage.alpha = 1.0f

                    tvSign.text = "点 名"

                }

                "1"->{//正在点名

                    tvSign.text = "开始上课"

                }

                "2"->{//正在上课
                    tvSign.text = "下课"

                }

                "3"->{//课次结束
                    tvSign.isClickable = false
                    tvSign.backgroundColorResource = R.color.text_grey_color
                    tvSign.text = "课次已结束"

                }

            }

        }

    }



    override fun onStartLazy() {
        if(courseTimeDetail == null || tvStage == null)return

        realStatus = AppData.getCourseStatus(courseTimeDetail!!.times_id)
        statusObserver.onNext(realStatus)

        if(AppData.isBackFromPageWithDataAndCleanData(StudentDetailActivity::class.java)){
            contentLayout.recyclerView.refreshData()
        }
    }


    override fun bindEvent() {
        super.bindEvent()

        tvStage.onClick {

            if (tvStage.alpha > 0.8){
                if(courseTimeDetail != null){
                    CourseStageActivity.launch(this@StudentsFragment.context,courseTimeDetail!!)
                }
            }else{
                showToast("只有未开始上课之前才可以设置阶段")
            }
        }

        tvSign.onClick {

            statusObserver.onNext((realStatus.toInt() + 1).toString())

        }

    }


    override fun getLayoutId(): Int {
        return R.layout.course_fragment_students
    }

    override fun newP(): PCourse = PCourse()


    companion object {

        fun newInstance(): StudentsFragment {
            val fragment = StudentsFragment()
            return fragment
        }
    }

    internal inner  class Adapter(context: Context) : BaseAdapter<Student>(context) {

        override fun onBindViewHolder(holder: ViewHolder<Student>, position: Int) {
            val item = data[position]
            holder.setData(item)
            holder.itemView.setOnClickListener {

                if(realStatus == "0"){
                    showToast("还未开始点名,不能操作学生")
                }else if(realStatus == "1"){

                    markAttendanceViewDialog.status = "1"

                    if(item.abnormal == 1){

                        markAttendanceViewDialog.status = "3"

                    }else if(item.signed == 1 || item.signed == 0){

                        markAttendanceViewDialog.status = "1"

                    }else if(item.signed == 2){
                        markAttendanceViewDialog.status = "2"
                    }

                    markAttendanceViewDialog.memberId = item.member_id.toString()

                    markAttendanceViewDialog.show()

                }else{
                    //查看学生详情
                    StudentDetailActivity.launch(this@StudentsFragment.context,item.apply { courseType = courseTimeDetail!!.type;timesId = courseTimeDetail!!.times_id })
                }

            }

            holder.itemView.setOnLongClickListener(object :View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    StudentDetailActivity.launch(this@StudentsFragment.context,item.apply { courseType = courseTimeDetail!!.type;timesId = courseTimeDetail!!.times_id })
                    return true
                }
            })
        }

        override fun getLayoutId(): Int = R.layout.course_activity_detail_student_item

        override fun setData(itemView: View, data: Student) {
            itemView.tvName.text = data.name
            itemView.waveLoadingView.text = data.heart.toString()

            itemView.waveLoadingView.setCurrentProgress(data.heart.toFloat()/120)



            if(data.abnormal == 1){//异常

            }else if (data.signed == 0){
                itemView.waveLoadingView.setCurrentProgress(0f)
                itemView.waveLoadingView.setEmptyColorRes(R.color.white)
                itemView.waveLoadingView.setFullColorRes(R.color.tab_text_unselected)

                itemView.cbCheck.isChecked = false
                itemView.cbCheck.backgroundResource = R.drawable.course_sign_unchecked
            }else if(data.signed == 1){

                if(data.heart > 80){

                    itemView.waveLoadingView.setCurrentProgress(1f)
                    itemView.waveLoadingView.setEmptyColorRes(R.color.white)
                    itemView.waveLoadingView.setFullColorRes(R.color.tab_text_selected)

                    itemView.cbCheck.isChecked = true
                    itemView.cbCheck.backgroundResource = R.drawable.course_sign_full_green_checked

                }else{

                    itemView.waveLoadingView.setCurrentProgress(0f)
                    itemView.waveLoadingView.setEmptyColorRes(R.color.white)
                    itemView.waveLoadingView.setFullColorRes(R.color.tab_text_selected)
                    itemView.cbCheck.isChecked = true
                    itemView.cbCheck.backgroundResource = R.drawable.course_sign_empty_green_checked
                }
            }else if(data.signed == 2){//请假
                if(data.heart > 80){

                    itemView.waveLoadingView.setCurrentProgress(1f)
                    itemView.waveLoadingView.setEmptyColorRes(R.color.white)
                    itemView.waveLoadingView.setFullColorRes(R.color.tab_text_unselected)

                    itemView.cbCheck.isChecked = true
                    itemView.cbCheck.backgroundResource = R.drawable.course_sign_full_green_checked

                }else{

                    itemView.waveLoadingView.setCurrentProgress(0f)
                    itemView.waveLoadingView.setEmptyColorRes(R.color.white)
                    itemView.waveLoadingView.setFullColorRes(R.color.tab_text_unselected)
                    itemView.cbCheck.isChecked = true
                    itemView.cbCheck.backgroundResource = R.drawable.course_sign_full_green_checked
                }
            }

        }



    }


     inner class DividerItemDecoration  constructor(context: Context) : Y_DividerItemDecoration(context) {

        override fun getDivider(itemPosition: Int): Y_Divider? {

            var color = ContextCompat.getColor(this@StudentsFragment.context,R.color.white)

            var divider: Y_Divider? = null
            when (itemPosition % 4) {
                0 ->
                    //每一行第一个显示rignt和bottom
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                3 ->
                    //第二个显示Left和bottom
                    divider = Y_DividerBuilder()
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                else -> {
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                }
            }
            return divider
        }
    }
}
