package com.yelai.wearable.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import cn.droidlover.xdroidmvp.router.Router
import com.jakewharton.rxbinding2.widget.RxTextView
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.isPhone
import com.yelai.wearable.present.PLogin
import com.yelai.wearable.showToast
import com.yelai.wearable.ui.HomeActivity
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_login_password.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.toast


/**
 * Created by hr on 18/9/15.
 */

class LoginActivity : BaseActivity<LoginContract.Presenter>(),LoginContract.View {

    override fun success() {
        HomeActivity.launch(context)
        AppManager.finishCurrentActivity()
    }

    override fun showError(errorMsg: String) {
        btnVerifyCode.isClickable = true
        toast(errorMsg).show()
    }

    override fun countDownVerifyCode() {
        TimeCount(this@LoginActivity.context, 60 * 1000, btnVerifyCode).start()
    }

    var mLoginModel : Int = 1
    //1 pwd   2 verify

    override fun initData(savedInstanceState: Bundle?) {

        btnLoginWithPassword.textColorResource = R.color.text_normal
        btnLoginWithVerifyCode.textColorResource = R.color.text_selected


        clPwd.visibility = View.VISIBLE

        clVerifyCode.visibility = View.GONE

        btnRegisterOrForgotPwd.text = "忘记密码?"
        btnRegisterOrForgotPwd.visibility = View.VISIBLE
    }

    var pwdVisible = false;

    override fun bindEvent() {
        super.bindEvent()

        val publishSubject = PublishSubject.create<Int>()


        btnLoginWithPassword.onClick {

            if(mLoginModel == 1)return@onClick


            btnLoginWithPassword.textColorResource = R.color.text_normal
            btnLoginWithVerifyCode.textColorResource = R.color.text_selected


            clPwd.visibility = View.VISIBLE
            clVerifyCode.visibility = View.GONE

            btnRegisterOrForgotPwd.text = "忘记密码?"
            btnRegisterOrForgotPwd.visibility = View.VISIBLE
            mLoginModel = 1

            publishSubject.onNext(mLoginModel)

        }

        btnPwd.onClick {
            pwdVisible = !pwdVisible;

            if(pwdVisible){
                etPwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD//输入为密码且可见
                btnPwd.imageResource = R.drawable.icon_pwd_hide
            }else{
                etPwd.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT // 设置文本类密码（默认不可见），这两个属性必须同时设置
                btnPwd.imageResource = R.drawable.icon_pwd_show
            }
        }



        btnLoginWithVerifyCode.onClick {

            if(mLoginModel == 2)return@onClick


            btnLoginWithVerifyCode.textColorResource = R.color.text_normal
            btnLoginWithPassword.textColorResource = R.color.text_selected

            clPwd.visibility = View.GONE
            clVerifyCode.visibility = View.VISIBLE

            btnRegisterOrForgotPwd.visibility = View.GONE

            mLoginModel = 2

            publishSubject.onNext(mLoginModel)


        }


        btnRegisterOrForgotPwd.onClick {

            //forgot pwd or goto register
            if(mLoginModel == 1){
                //忘记密码

                ForgetPasswordActivity.launch(this@LoginActivity.context)

            }

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


        btnLogin.onClick {

            val phone = etPhone.text.trim().toString()

//            if (!phone.isPhone()){
//                showToast("请输入正确的电话号码")
//                return@onClick
//            }

            val type = mLoginModel;//1 密码登录   2 验证码登录

            val code = etVerifyCode.text.trim().toString()

            val password = etPwd.text.trim().toString()

//            if(type == 1){
//                if (password.length < 6){
//                    showToast("请填写密码")
//                    return@onClick
//                }
//            }else{
//                if(code.length < 3){
//                    showToast("请填写验证码")
//                    return@onClick
//                }
//            }

            p.login(mapOf<String,Any>("mobile" to phone,"type" to type,"code" to code,"password" to password))
        }





        Observable.combineLatest(
                RxTextView.textChanges(etPhone),
                RxTextView.textChanges(etVerifyCode),
                RxTextView.textChanges(etPwd),
                publishSubject,
                Function4<CharSequence,CharSequence,CharSequence,Int,Boolean>{t1, t2, t3, t4 ->
                    t1.trim().toString().isPhone() && ((t4 == 1 && t3.isNotEmpty() && t3.trim().length > 4) || (t4 == 2 && t2.isNotEmpty() && t2.trim().length >= 4))
                 }
        ).subscribe {
                btnLogin.alpha = if(it){ 1f } else { .5f }
                btnLogin.isClickable = it
        }

        publishSubject.onNext(1)//触发上面的监听事件,导致界面上的元素改变成对应的状态



    }




    override fun getLayoutId(): Int {
        return R.layout.activity_login_password
    }

    override fun newP(): PLogin? {
        return PLogin()
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(LoginActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
