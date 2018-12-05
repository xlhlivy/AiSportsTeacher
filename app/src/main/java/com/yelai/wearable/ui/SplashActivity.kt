package com.yelai.wearable.ui

import android.Manifest
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.ui.HomeActivity
import com.yelai.wearable.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by hr on 18/9/15.
 */

class SplashActivity : BaseActivity<PViod>() {

    private var textTypeface: Typeface?=null

    private var descTypeFace: Typeface?=null

    private var alphaAnimation: AlphaAnimation?=null

    init {
//        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
//        descTypeFace = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun initData(savedInstanceState: Bundle?) {
//        tv_app_name.typeface = textTypeface
//        tv_splash_desc.typeface = descTypeFace
//        tv_version_name.text = "v${AppUtils.getVerName(MyApplication.context)}"

//        DensityUtil.setScreenSize(this)

        //渐变展示启动屏
        alphaAnimation= AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectTo()
            }
            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })

//        StatusBarUtil.darkMode(context,R.color.background,0f)

        checkPermission()
    }


    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun newP(): PViod = PViod()



    fun redirectTo() {
        //是否已经登录，如果登录了，就直接跳转到HomeActivity 否则跳转到登录注册页面

        if(AppData.user != null){
            //已经登录过了->
            //检查信息是否完备
            HomeActivity.launch(this@SplashActivity.context)

        }else{
            LoginActivity.launch(this@SplashActivity.context)
        }
//        val intent = Intent(this, if(App.isLogin()){HomeActivity::class.java} else {LoginActivity::class.java})
//        startActivity(intent)
        AppManager.finishCurrentActivity()

    }

    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
//        ,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "乐生应用需要以下权限，请允许", 0, *perms)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE)
                        && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    if (alphaAnimation == null)return

                    if (alphaAnimation != null) {
                        view.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }
}
