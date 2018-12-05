package com.yelai.wearable.ui.examination

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.SpannableStringBuilder
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import cn.droidlover.xdroidmvp.XDroidConf
import cn.droidlover.xdroidmvp.imageloader.ILFactory
import cn.droidlover.xdroidmvp.imageloader.ILoader
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.Course
import com.yelai.wearable.model.Examination
import com.yelai.wearable.model.ExaminationUpdater
import com.yelai.wearable.net.Api
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.showToast
import com.yelai.wearable.utils.FileUtils
import com.yelai.wearable.widget.CheckBoxView
import kotlinx.android.synthetic.main.examination_choice_class.*
import kotlinx.android.synthetic.main.examination_choice_class_item.view.*
import org.jetbrains.anko.forEachChild
import org.jetbrains.anko.forEachChildWithIndex
import org.jetbrains.anko.sdk25.coroutines.onClick
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.*
import java.util.*

/**
 * Created by hr on 18/9/16.
 */

class ChoiceClassFragment : BaseFragment<CourseContract.Presenter>(),CourseContract.View {

    override fun getLayoutId(): Int = R.layout.examination_choice_class

    override fun newP(): PCourse = PCourse()

    var isEdit = false

    var img:String? = null



    override fun success(type: CourseContract.Success, data: Any) {

        if(type == CourseContract.Success.CourseList){

            data as List<Course>

            if(exam != null){
                data.forEach {
                    it.isChecked = false
                    if(it.id == exam!!.lessonId){
                        it.isChecked = true
                    }
                }
            }

            adapter.setData(data)

        }else if(type == CourseContract.Success.ImageUpload){

            val url = (data as String)
            img = url
            exam?.img = url
            loadImage(url)
        }

    }

    fun loadImage(url:String){
        if(url.isEmpty())return
        val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
        options.scaleType = ImageView.ScaleType.FIT_XY

        ILFactory.getLoader().loadNet(ivBackground, Api.IMAGE_URL + url, options)
    }

    fun initViewWithData(examination: Examination){

        exam = examination


        if(tvStageName != null){
            tvTeacherName.text = examination.teacherName

            tvStageName.text = SpannableStringBuilder(examination.title)

//        tvStageName.isEnabled = false



            examinationItems.forEach {
                if(examination.item.contains(it.textView.tag.toString(),true)){
                    it.isChecked = true
                }
            }

            adapter.dataSource.forEach {
                it.isChecked = false

                if(it.id == examination.lessonId){
                    it.isChecked = true
                }
            }

            loadImage(exam!!.img)

        }
    }

    override fun onResume() {
        super.onResume()
        if(exam != null){
            initViewWithData(exam!!)
        }
    }


    var exam:Examination? = null



    fun collectionData(examination:ExaminationUpdater):Boolean{

        examination.title = tvStageName.text.trim().toString()

        if(examination.title.isEmpty()){
            showToast("请填写测试名称")
            return false
        }

        if(exam == null){
            examination.teacherId = AppData.user!!.uid

            examination.teacherName = AppData.user!!.name

            examination.img = if(img == null){""}else{img!!}
        }else{
            examination.teacherId = exam!!.teacherId

            examination.teacherName = exam!!.teacherName

            examination.examId = exam!!.id

            examination.img = exam!!.img
        }


        examination.type = "1"

        examination.item = examinationItems.filter { it.isChecked }.map { it.textView.tag }.toList().joinToString(separator = ",")

        if(examination.item.isEmpty()){

            showToast("请选择体测项目")
            return false
        }

        val course = adapter.dataSource.firstOrNull { it.isChecked }

        if(course == null){
            showToast("请选择体测班级")
            return false
        }

        examination.lessonId =  course.id

        return true
    }


    /**
     * 可以将固定的布局修改成为recyclerview
     *
     */

    val examinationItems = ArrayList<CheckBoxView>(8)


    override fun initData(savedInstanceState: Bundle?) {

        llExaminationItem.forEachChildWithIndex { idx, view ->
            if (idx == 0)return@forEachChildWithIndex
            if (view is ViewGroup){
                view.forEachChild {
                    if(it is CheckBoxView) examinationItems.add(it)
                }
            }
        }

        examinationItems.forEachIndexed { index, checkBoxView ->
            checkBoxView.textView.text = examTypes[index]
            checkBoxView.textView.tag = (index + 1).toString()
            checkBoxView.isChecked = true

            checkBoxView.onClick {
                checkBoxView.isChecked = !checkBoxView.isChecked
            }

//            checkBoxView.isEnabled = false
        }

        ivBackground.onClick {

            imagePickerView.show()
        }

        tvTeacherName.text = AppData.user!!.name


        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(DividerItemDecoration(context))
        recyclerView.adapter = XRecyclerAdapter(adapter)

        p.courseList()

    }

    val adapter by lazy{Adapter(context)}


    inner class Adapter(context: Context) : BaseAdapter<Course>(context) {
        override fun getLayoutId(): Int = R.layout.examination_choice_class_item

        override fun onBindViewHolder(holder: ViewHolder<Course>, position: Int) {
            val item = data[position]
            holder.setData(item)

            holder.itemView.cbv.onClick {
                data.forEach { it.isChecked = false }
                item.isChecked = true
                adapter.notifyDataSetChanged()
            }

        }

        override fun setData(itemView: View, data: Course) {
            itemView.cbv.textView.text = data.title
            itemView.cbv.isChecked = data.isChecked

        }

    }



        inner class DividerItemDecoration  constructor(context: Context) : Y_DividerItemDecoration(context) {

        override fun getDivider(itemPosition: Int): Y_Divider? {

            var color = ContextCompat.getColor(this@ChoiceClassFragment.context,R.color.white)

            var divider: Y_Divider? = null
            when (itemPosition % 2) {
                0 ->
                    //每一行第一个显示rignt和bottom
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                1 ->
                    //第二个显示Left和bottom
                    divider = Y_DividerBuilder()
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                else -> {
                    divider = Y_DividerBuilder()
                            .setRightSideLine(true, color, 5f, 0f, 0f)
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                }
            }
            return divider
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

        p.imageUpload("data:image/png;base64,${objectStr}");


    }



    companion object {

        fun newInstance(isEdit:Boolean = false): ChoiceClassFragment {
            val fragment = ChoiceClassFragment()
            fragment.isEdit = isEdit
            return fragment
        }
    }
}
