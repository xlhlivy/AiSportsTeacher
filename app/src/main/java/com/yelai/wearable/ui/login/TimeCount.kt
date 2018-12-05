package com.yelai.wearable.ui.login

import android.content.Context
import android.os.CountDownTimer
import android.widget.Button
import com.yelai.wearable.R
import org.jetbrains.anko.backgroundResource

class TimeCount(private val context: Context, millisInFuture: Long, private val button: Button) : CountDownTimer(millisInFuture, 1000) {

    override fun onFinish() {// 计时完毕
        button.text = "获取验证码"
        button.isClickable = true
        button.backgroundResource = R.drawable.btn_background_normal
    }

    override fun onTick(millisUntilFinished: Long) {// 计时过程

        button.backgroundResource = R.drawable.btn_background_press

        button.isClickable = false//防止重复点击
        button.text = (millisUntilFinished / 1000).toString() + "秒后重发"
    }


}