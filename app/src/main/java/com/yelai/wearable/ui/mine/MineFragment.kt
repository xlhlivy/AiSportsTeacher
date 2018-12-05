package com.yelai.wearable.ui.mine

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Base64
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import cn.droidlover.xdroidmvp.XDroidConf
import cn.droidlover.xdroidmvp.imageloader.ILFactory
import cn.droidlover.xdroidmvp.imageloader.ILoader
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.MapDataResult
import com.yelai.wearable.net.Api
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.present.PViod
import com.yelai.wearable.showToast
import com.yelai.wearable.utils.FileUtils
import kotlinx.android.synthetic.main.course_activity_stage_view.*
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.mine_fragment.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.*
import java.util.*


/**
 * Created by hr on 18/9/16.
 */

class MineFragment : BaseFragment<CourseContract.Presenter>(),CourseContract.View {

    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.Feedback){

            showToast(data as String)
            etFeedback.text = SpannableStringBuilder("")
        }else if(type == CourseContract.Success.ImageUpload){

            val url = (data as String)
            val user = AppData.user!!
            AppData.user = user.apply { headImg = url }

            loadHead(url)

        }

    }


    override fun initData(savedInstanceState: Bundle?) {


        tvUsername.text = AppData.user!!.name
        tvDynamic.text = ""

        loadHead(AppData.user!!.headImg)

    }


    fun loadHead(url:String){
        if(url.isEmpty())return
        val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
        options.scaleType = ImageView.ScaleType.FIT_XY
        

        ILFactory.getLoader().loadNet(ivHeader, Api.IMAGE_URL + url, options)
    }

    override fun bindEvent() {
        super.bindEvent()

//        ivHeader

        rlCourse.onClick { CourseActivity.launch(this@MineFragment.context) }


        rlHistory.onClick {SportHistoryActivity.launch(this@MineFragment.context) }

        rlDevice.onClick { DeviceActivity.launch(this@MineFragment.context) }

        rlSetting.onClick { SettingActivity.launch(this@MineFragment.context) }

        btnFeedback.onClick {

            val text = etFeedback.text.trim().toString()

            if(text.isEmpty()){
                return@onClick
            }

            p.feedback(text)

        }

        ivHeader.onClick {


            imagePickerView.show()


        }




    }

    /**图片上传 开始**/

    val options by lazy{
        listOf("相册","拍照")
    }

    val imagePickerView by lazy { buildImagePicker() }






    fun buildImagePicker(): OptionsPickerView<String> {
        val pvTime = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, v ->

            if(options1 == 0){

                getImage()

            }else{

                takePhoto()
            }

//            val text = options[options1];

//            tvTimeRequire.text = text
//
//            minus = text.substring(0,text.length - 2)


        })

                .isDialog(true)
                .build<String>()

        val mDialog = pvTime.dialog


//
        if (mDialog != null) {


            val windowManager = mDialog.window!!.windowManager
            val display = windowManager.defaultDisplay
            val lp = mDialog.window!!.attributes
            lp.width = (display.width).toInt() //设置宽度
            mDialog.window!!.attributes = lp



            val containerParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM)

            containerParams.leftMargin = 0
            containerParams.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = containerParams


            val contentParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            val viewGroup = pvTime.dialogContainerLayout.getChildAt(0) as ViewGroup

            viewGroup.layoutParams = contentParams


            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }

        pvTime.setPicker(options)

        return pvTime;
    }


    override fun onRationaleDenied(requestCode: Int) {
        super.onRationaleDenied(requestCode)
//        AppManager.finishCurrentActivity()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        // 用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要拍照权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        } else if (!EasyPermissions.hasPermissions(context, *perms.toTypedArray())) {
            requirePermission()
        }
    }


    /**
     * 点击拍照 跳转到系统拍照界面
     */
    @AfterPermissionGranted(222)
    private fun takePhoto() {


        val perms = arrayOf(Manifest.permission.CAMERA)

        if (EasyPermissions.hasPermissions(context, *perms)) {

            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                //下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(File(Environment
                                .getExternalStorageDirectory(),
                                "xiaoma.jpg")))
                startActivityForResult(intent, 222)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "拍照需要相机权限,请允许", 2, *perms)
        }



    }

    @AfterPermissionGranted(111)
    private fun getImage() {

        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (EasyPermissions.hasPermissions(context, *perms)) {
            //检查权限
            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*")
            startActivityForResult(intent, 111)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "设置头像需要访问文件权限,请允许", 1, *perms)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {

        // 如果是直接从相册获取
            111 -> if (data != null)
                startPhotoZoom(data.data)
        // 如果是调用相机拍照时
            222 -> {
                val temp = File(Environment.getExternalStorageDirectory().toString() + "/xiaoma.jpg")
                startPhotoZoom(Uri.fromFile(temp))
            }
        // 取得裁剪后的图片
            3 ->
                /**
                 * 非空判断大家一定要验证，如果不验证的话，
                 * 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException
                 */
                if (data != null) {
                    setPicToView(data)
                }
            else -> {
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    fun startPhotoZoom(uri: Uri?) {
        /*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
		 */
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150)
        intent.putExtra("outputY", 150)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, 3)
    }

    private fun decodeUriAsBitmap(uri: Uri?): Bitmap? {

        var bitmap: Bitmap? = null

        try {

            bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))

        } catch (e: FileNotFoundException) {

            e.printStackTrace()

            return null

        }

        return bitmap

    }

    private var uuid: String? = null
//    private var picPath: String? = null

    private fun setPicToView(picdata: Intent) {
        if (picdata.data != null) {
            val photo = decodeUriAsBitmap(picdata.data)
            val drawable = BitmapDrawable(photo)
            val bd = drawable as BitmapDrawable
            val bit = bd.bitmap
            val fUtil = FileUtils(activity)
            if (uuid == null) {
                uuid = UUID.randomUUID().toString()
            }
            val picPath = fUtil.getPicDirectory() + uuid
            try {
                val outStream = FileOutputStream(File(picPath))
                bit.compress(Bitmap.CompressFormat.JPEG, 100, outStream) //保存图片
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            updatePic(picPath, drawable)
            //            personalService.updateSelf();
            //            mIvPhoto.setImageDrawable(drawable);
        }else{
            val photo =  picdata.extras.getParcelable<Bitmap>("data");
//            val photo = decodeUriAsBitmap(uri)
            val drawable = BitmapDrawable(photo)
            val bd = drawable as BitmapDrawable
            val bit = bd.bitmap
            val fUtil = FileUtils(activity)
            if (uuid == null) {
                uuid = UUID.randomUUID().toString()
            }
            val picPath = fUtil.getPicDirectory() + uuid
            try {
                val outStream = FileOutputStream(File(picPath))
                bit.compress(Bitmap.CompressFormat.JPEG, 100, outStream) //保存图片
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            updatePic(picPath, drawable)

        }
    }


//    internal var homeService = HomeService()
    private fun updatePic(picPath: String, drawable: Drawable) {
//        val list = ArrayList<String>()
//        list.add(picPath)

        val baos = ByteArrayOutputStream()

        val fis = FileInputStream(picPath)

        val byteArray = ByteArray(8 * 1024);

        var read = 1;

        while(read > 0){

            read = fis.read(byteArray)

            if(read > 0){
                baos.write(byteArray,0,read)
            }
        }

        val objectStr = String(Base64.encode(baos.toByteArray(),
                Base64.DEFAULT))
        try {
            fis.close()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        p.headUpload("data:image/png;base64,${objectStr}")


    }

    /**图片上传 结束**/


    override fun getLayoutId(): Int {
        return R.layout.mine_fragment
    }

    override fun newP(): PCourse = PCourse()


    companion object {

        fun newInstance(): MineFragment {
            val fragment = MineFragment()
            return fragment
        }
    }
}
