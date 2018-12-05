package com.yelai.wearable.ui.course

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.telecom.Call
import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.BasePickerView
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.yelai.wearable.R
import com.yelai.wearable.calendar
import com.yelai.wearable.entity.Time
import com.yelai.wearable.toHHmm
import com.yelai.wearable.toYearAndMonth
import kotlinx.android.synthetic.main.course_activity_week_date_picker.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


/**
 * 自定义透明的dialog
 */
class WeekDatePickerViewDialog constructor(context: Context, val time:Time, var callback: Callback) : Dialog(context, R.style.CustomDialog) {

    @FunctionalInterface
    public interface Callback{
        fun done(time:Time)
    }




    init {
        setContentView(R.layout.course_activity_week_date_picker)

        setCanceledOnTouchOutside(true)
        val windowManager = window!!.windowManager
        val display = windowManager.defaultDisplay
        val lp = window!!.attributes
        lp.width = (display.width * 0.8).toInt() //设置宽度
//        lp.alpha = 0.9f
        window!!.attributes = lp
        setCancelable(false)

        initView()
    }


    val weekPickerView by lazy{buildWeekPicker()}

    val startTimePickerView by lazy{buildStartTimePicker()}

    val endTimePickerView by lazy{buildEndTimePicker()}


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK//拦截返回按键事件
            -> {

            }
        }
        return true
    }


    override fun show() {
        super.show()

        if(time.weekIndex != -1 && weekPickerView != null){
            tvWeek.text = time.week()
            weekPickerView.setSelectOptions(time.weekIndex)
        }

        if(time.startTime != null && startTimePickerView != null){
            startTimePickerView.setDate(time.startTime.calendar())
            tvStart.text = time.startTimeText()
        }

        if(time.endTime != null && endTimePickerView != null){
            endTimePickerView.setDate(time.endTime.calendar())
            tvEnd.text = time.endTimeText()
        }
    }



    fun initView() {

        btnCancel.onClick { dismiss() }

        btnSave.onClick {

            if(time.startTime.after(time.endTime)){

                Toast.makeText(this@WeekDatePickerViewDialog.context,"开始时间必须小于结束时间哦",Toast.LENGTH_SHORT).show()

                return@onClick
            }

            callback.done(time)
            dismiss()
        }


        tvWeek.onClick {
            if(time.weekIndex != -1){
                weekPickerView.setSelectOptions(time.weekIndex)
            }
            weekPickerView.show()
        }


        tvStart.onClick {

            if(time.startTime != null){
                startTimePickerView.setDate(time.startTime.calendar())
            }

            startTimePickerView.show()
        }

        tvEnd.onClick {
            if(time.endTime != null){
                endTimePickerView.setDate(time.endTime.calendar())
            }
            endTimePickerView.show()
        }
    }


    //
    fun buildWeekPicker(): OptionsPickerView<String> {
        val pvTime = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, v ->
            time.weekIndex = options1
            tvWeek.text = time.week()
        })
//                .setTitleText("城市选择")
//                .setContentTextSize(20)//设置滚轮文字大小
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
//                .setSelectOptions(0)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setLabels("省","","")
//                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .isDialog(true)
//                .setDecorView(rootView as ViewGroup)
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



//            pvTime.dialogContainerLayout.backgroundColorResource = R.color.x_red

            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }

        pvTime.setPicker(Time.weekData)

        return pvTime;
    }

    fun buildStartTimePicker():TimePickerView{
        val pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
            time.startTime = date
            tvStart.text = time.startTimeText()
        })
                .setType(booleanArrayOf(false, false, false,true, true, false))
                //                .setRangDate(startTime,endTime)
//                .setDate()
                .isDialog(true)
                .build()

        val mDialog = pvTime.dialog
        if (mDialog != null) {

            val params = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM)

            params.leftMargin = 0
            params.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = params

            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }
        return pvTime
    }


    fun buildEndTimePicker():TimePickerView{
        val pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
            time.endTime = date
            tvEnd.text = time.endTimeText()
        })
                .setType(booleanArrayOf(false, false, false,true, true, false))
                //                .setRangDate(startTime,endTime)
                .isDialog(true)
                .build()

        val mDialog = pvTime.dialog
        if (mDialog != null) {

            val params = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM)

            params.leftMargin = 0
            params.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = params

            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }
        return pvTime
    }



}