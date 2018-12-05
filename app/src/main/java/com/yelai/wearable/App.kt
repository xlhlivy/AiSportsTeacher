package com.yelai.wearable

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.multidex.MultiDex
import android.util.Log
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.NetProvider
import cn.droidlover.xdroidmvp.net.RequestHandler
import cn.droidlover.xdroidmvp.net.XApi
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
//import com.squareup.leakcanary.LeakCanary
//import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.crashreport.CrashReport
import com.yelai.wearable.utils.DisplayManager
import com.zzhoujay.richtext.RichText
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import kotlin.properties.Delegates
import android.os.StrictMode



/**
 * Created by wanglei on 2016/12/31.
 */

class App : Application() {

//    private var refWatcher: RefWatcher? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        CrashReport.initCrashReport(applicationContext, "80328442f5", true);

        context = applicationContext
//        refWatcher = setupLeakCanary()
        initConfig()
        DisplayManager.init(this)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

        XApi.registerProvider(object : NetProvider {

            override fun configInterceptors(): Array<Interceptor> {
                return emptyArray()
            }

            override fun configHttps(builder: OkHttpClient.Builder) {

            }

            override fun configCookie(): CookieJar? {
                return null
            }

            override fun configHandler(): RequestHandler? {
                return null
            }

            override fun configConnectTimeoutMills(): Long {
                return 0
            }

            override fun configReadTimeoutMills(): Long {
                return 0
            }

            override fun configLogEnable(): Boolean {
                return true
            }

            override fun handleError(error: NetError): Boolean {
                return false
            }

            override fun dispatchProgressEnable(): Boolean {
                return false
            }
        })
    }
//
//    private fun setupLeakCanary(): RefWatcher {
//        return if (LeakCanary.isInAnalyzerProcess(this)) {
//            RefWatcher.DISABLED
//        } else LeakCanary.install(this)
//    }


    /**
     * 初始化配置
     */
    private fun initConfig() {

        RichText.initCacheDir(this)

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("hao_zz")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }


    companion object {

        private val TAG = "App"

        var context: Context by Delegates.notNull()
            private set

//        fun getRefWatcher(context: Context): RefWatcher? {
//            val myApplication = context.applicationContext as App
//            return myApplication.refWatcher
//        }

    }

//    companion object {
//
//        var context: Context? = null
//            private set
//    }
}
