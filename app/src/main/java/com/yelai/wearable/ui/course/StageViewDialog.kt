package com.yelai.wearable.ui.course

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.SpannableStringBuilder
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
import com.yelai.wearable.R
import com.yelai.wearable.model.CourseTimeStep
import kotlinx.android.synthetic.main.course_activity_stage_view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * 自定义透明的dialog
 */
class StageViewDialog constructor(context: Context,val callback:Callback) : Dialog(context, R.style.CustomDialog) {


    var courseTimeStep:CourseTimeStep? = null

    interface Callback{

        fun done(step:CourseTimeStep)
    }


    init {
        setContentView(R.layout.course_activity_stage_view)

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

        if(courseTimeStep != null && tvStageName != null && tvTimeRequire != null){

            tvStageName.text = SpannableStringBuilder(courseTimeStep!!.name.toString())

            minus = courseTimeStep!!.time

            tvTimeRequire.text = "${minus}分钟"
        }
    }


    val pvStatusOptions by lazy{buildTimeRequire()}

    fun initView() {

        btnCancel.onClick {
            dismiss()
        }

        btnSave.onClick {

            val courseName = tvStageName.text.trim().toString()

            if(courseName.isEmpty()){
                Toast.makeText(this@StageViewDialog.context,"请填写阶段名称",Toast.LENGTH_SHORT).show()
                return@onClick
            }


            if(courseTimeStep!= null){
                courseTimeStep!!.name = courseName
                courseTimeStep!!.time = minus
                callback.done(courseTimeStep!!)
            }
            dismiss()
        }


        tvTimeRequire.onClick {


            if(courseTimeStep != null){
                val index = options.indexOf("${minus}分钟")

                if(index > -1){

                    pvStatusOptions.setSelectOptions(index)
                }

            }

            pvStatusOptions.show()
        }

    }
//

    val options by lazy{
        listOf("5分钟","10分钟","15分钟","20分钟","25分钟","30分钟")
    }

    var minus:String = ""

    fun buildTimeRequire(): OptionsPickerView<String> {
        val pvTime = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, v ->

            val text = options[options1];

            tvTimeRequire.text = text

            minus = text.substring(0,text.length - 2)


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

        pvTime.setPicker(options)

        return pvTime;
    }


}