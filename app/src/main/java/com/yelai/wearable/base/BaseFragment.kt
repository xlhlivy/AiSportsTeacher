package com.yelai.wearable.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.yelai.wearable.AppManager
import com.yelai.wearable.net.HRIView
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.sport_fragment.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * Created by hr on 18/9/16.
 */

abstract class BaseFragment<P : IPresent<*>>() : KLazyFragment<P>(),EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks,HRIView<P> {

//    override fun showIndicator() {
//
//    }
//    override fun hideIndicator() {
//    }

    override fun showIndicator() {
        (context as BaseActivity<*>).showIndicator()
    }

    override fun hideIndicator() {
        (context as BaseActivity<*>).hideIndicator()
    }

    override fun showError(errorMsg: String) {

        (context as BaseActivity<*>).showError(errorMsg)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE && resultCode == Activity.RESULT_CANCELED){
            AppManager.finishCurrentActivity()
        }else if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE && resultCode == Activity.RESULT_OK){
            requirePermission()
        }
    }



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


    val permissionNames = mapOf<String,String>(
            Manifest.permission.READ_PHONE_STATE to "读取手机状态",
            Manifest.permission.WRITE_EXTERNAL_STORAGE to "存储服务",
//            Manifest.permission.ACCESS_COARSE_LOCATION to "获取位置服务",
            Manifest.permission.ACCESS_FINE_LOCATION to "获取位置服务")

    open fun requirePermission(){

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

            if(permissionNames.containsKey(str)){

                sb.append(permissionNames[str])
                sb.append("\n")
            }

        }

        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(context, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        }
    }


    override fun onRationaleAccepted(requestCode: Int) {
        println("onRationaleAccepted")
    }

    override fun onRationaleDenied(requestCode: Int) {

        println("onRationaleDenied")

    }


}
