package com.yelai.wearable.ui.course

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.View
import com.yelai.wearable.R
import kotlinx.android.synthetic.main.course_activity_mark_attendance_view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * 自定义透明的dialog
 */
class MarkAttendanceViewDialog constructor(context: Context,val callback:Callback) : Dialog(context, R.style.CustomDialog) {


    var status:String = "1"

    var memberId:String? = null

    interface Callback{
        fun done(memberId:String,status:String);
    }

    init {
        setContentView(R.layout.course_activity_mark_attendance_view)

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

        if(id4 == null || rgMark == null)return
        isClicked = false
        id4.text = "设为异常"
        id4.tag = "3"
        when(status){
            "1"->{
                rgMark.checkRadioButton(findViewById(R.id.id2))
                rgMark.check(R.id.id2)
            }
            "2"->{
//                rgMark.check(R.id.id1)
                rgMark.checkRadioButton(findViewById(R.id.id1))
                rgMark.check(R.id.id1)


            }
            "3"->{
                id4.text = "取消异常"
                id4.tag = "4"
                rgMark.checkRadioButton(findViewById(R.id.id4))
                rgMark.check(R.id.id4)

            }
            "4"->{
                id4.text = "设为异常"
                id4.tag = "3"
            }
            "5"->{
//                rgMark.check(R.id.id3)
                rgMark.checkRadioButton(findViewById(R.id.id3))
                rgMark.check(R.id.id3)


            }
        }
    }

    private var isClicked = false

    fun initView() {

//        1：签到 2：请假 3：设为异常 4：取消异常 5：移除
        btnCancel.onClick { dismiss() }

        btnSave.onClick {

            if(!isClicked){
                status = findViewById<View>(rgMark.checkedRadioButtonId).tag.toString();
            }

            callback.done(memberId!!,status)
            dismiss()
        }

        rgMark.setOnCheckedChangeListener { group, checkedId ->

            val tag = findViewById<View>(checkedId).tag ?: return@setOnCheckedChangeListener;

            status = tag as String

            isClicked = true

        }

//        rgMark.setOnCheckedChangeListener { group, checkedId ->
//
//       }


    }


}