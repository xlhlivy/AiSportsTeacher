package com.yelai.wearable.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.XActivity
import cn.droidlover.xdroidmvp.net.NetError
import com.yelai.wearable.App
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.net.HRIView
import com.yelai.wearable.ui.MainActivity
import io.reactivex.annotations.NonNull
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.lang.reflect.Method


/**
 * @author xuhao
 * created: 2017/10/25
 * desc:BaseActivity基类
 */
abstract class BaseActivity<P : IPresent<*>> : XActivity<P>(),EasyPermissions.PermissionCallbacks, HRIView<P> {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarDarkMode()
        AppManager.addActivity(this);
    }


    private val indicatorView:View by lazy{
        View.inflate(context, R.layout.view_loading, findViewById(cn.droidlover.xdroidmvp.R.id.root_indicator))
    }



    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }


    override fun showIndicator(){
        indicatorView.visibility = View.VISIBLE
    }

    override fun hideIndicator(){
        indicatorView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        if(AppManager.currentActivity() == this){
            AppManager.popCurrentActivity()
        }
//        App.getRefWatcher(this)?.watch(this)
    }

    //检查系统权限是，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
    fun isLackPermission(permission: Array<String>): Boolean {
        var result = true
        for (p in permission) {
            if (ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_DENIED) {
                return true
            } else {
                result = false
            }
        }
        return result
    }


//    override fun initData(savedInstanceState: Bundle?) {
//
//    }


    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        }
    }


    //修改字体
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun showError(error: NetError?) {
//        if (error != null) {
//            when (error.type) {
//                NetError.ParseError -> errorView.setMsg("数据解析异常")
//
//                NetError.AuthError -> errorView.setMsg("身份验证异常")
//
//                NetError.BusinessError -> errorView.setMsg("业务异常")
//
//                NetError.NoConnectError -> errorView.setMsg("网络无连接")
//
//                NetError.NoDataError -> errorView.setMsg("数据为空")
//
//                NetError.OtherError -> errorView.setMsg("其他异常")
//            }
//            contentLayout.showError()
//        }
    }


}


