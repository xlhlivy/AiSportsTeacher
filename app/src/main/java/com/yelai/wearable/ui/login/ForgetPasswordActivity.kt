package com.yelai.wearable.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.XActivity
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.RegisterContract
import com.yelai.wearable.isPhone
import com.yelai.wearable.present.PRegister
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.activity_login_register.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by hr on 18/9/15.
 */

class ForgetPasswordActivity : BaseActivity<RegisterContract.Presenter>(),RegisterContract.View {

    /**
     * MVP 回调
     *
     *
     */
    override fun success() {
        //跳转回登录界面还是怎么的
        showToast("重置密码成功")
        AppManager.finishCurrentActivity()
    }

    override fun showError(errorMsg: String) {
        btnVerifyCode.isClickable = true
        toast(errorMsg).show()
    }

    override fun countDownVerifyCode() {
        TimeCount(this@ForgetPasswordActivity.context, 60 * 1000, btnVerifyCode).start()
    }



    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()
        mToolbar.setLeftIcon(R.drawable.title_white_back)
        mToolbar.backgroundColorResource = R.color.background

    }

    override fun bindEvent() {
        super.bindEvent()


        btnRegister.onClick {
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

            p.resetpwd(phone,code,password)

        }


        btnVerifyCode.onClick {
            val phone = etPhone.text.trim().toString()
            if (phone.isPhone()){
                p.sendVerifyCode(phone)
                it!!.isClickable = false
            }else{
                showToast("请输入正确的电话号码")
            }
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_forget_password
    }

    override fun newP(): PRegister {
        return PRegister()
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(ForgetPasswordActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
