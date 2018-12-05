package com.yelai.wearable.ui.course

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.BasePickerView

import com.yelai.wearable.R


/**
 * 自定义透明的dialog
 */
class CourseTimePickerDialog(context: Context) : Dialog(context, R.style.CustomDialog) {

    init {
        initView()
        resize()
    }

    fun resize() {
        val windowManager = window!!.windowManager

        val display = windowManager.defaultDisplay
        val lp = window!!.attributes
        lp.width = (display.width * 0.8).toInt() //设置宽度
        window!!.attributes = lp
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
            }
        }//                if(CourseTimePickerDialog.this.isShowing())
        //                    CourseTimePickerDialog.this.dismiss();
        return true
    }

    private fun initView() {
        setContentView(R.layout.course_activity_week_date_picker)

        //        ((TextView)findViewById(R.id.tvcontent)).setText(content);

        setCanceledOnTouchOutside(true)
        val attributes = window!!.attributes
        attributes.alpha = 0.9f
        window!!.attributes = attributes
        setCancelable(false)
    }



}