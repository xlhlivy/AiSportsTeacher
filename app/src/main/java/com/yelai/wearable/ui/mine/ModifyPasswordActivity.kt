package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.isPhone
import com.yelai.wearable.present.PLogin
import com.yelai.wearable.showToast
import com.yelai.wearable.ui.login.TimeCount
import kotlinx.android.synthetic.main.activity_modify_password.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class ModifyPasswordActivity : BaseActivity<LoginContract.Presenter>(),LoginContract.View{


    override fun success() {


    }

    override fun modifyPasswordSuccess() {

        showToast("修改密码成功")
        AppManager.finishCurrentActivity()

    }

    override fun showError(errorMsg: String) {
        btnVerifyCode.isClickable = true
        toast(errorMsg).show()
    }

    override fun countDownVerifyCode() {
        TimeCount(this@ModifyPasswordActivity.context, 60 * 1000, btnVerifyCode).start()
    }



    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("修改密码", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()


        btnSave.onClick {

            val phone = etPhone.text.trim().toString()

            val code = etVerifyCode.text.trim().toString()

            val password = etPwd.text.trim().toString()


            if (!phone.isPhone()){
                showToast("请输入正确的电话号码")
                return@onClick
            }

            if(code.length < 3){
                showToast("请填写验证码")
                return@onClick
            }

            if(password.length < 6){
                showToast("密码长度必须大于6位")
                return@onClick
            }

            p.modifyPassword(phone,code,password)

        }

        btnVerifyCode.onClick {
            val phone = etPhone.text.trim().toString()
            if (phone.isPhone()){
                p.sendVerifyCode(phone,3)
                it!!.isClickable = false
            }else{
                showToast("请输入正确的电话号码")
            }
        }


    }



    override fun getLayoutId(): Int = R.layout.activity_modify_password

    override fun newP(): PLogin = PLogin()


    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(ModifyPasswordActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

}
