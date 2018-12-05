package com.yelai.wearable

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.droidlover.xdroidmvp.XDroidConf
import cn.droidlover.xdroidmvp.imageloader.ILFactory
import cn.droidlover.xdroidmvp.imageloader.ILoader
import cn.droidlover.xdroidmvp.net.IModel
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.net.HRIView
import com.yelai.wearable.net.Api
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by xuhao on 2017/11/14.
 */


fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(App.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}


fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun Context.dataFormat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}


fun String.isPhone():Boolean{
    val regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"
    if (this.length != 11) {
        return false
    } else {
        val p = Pattern.compile(regex)
        val m = p.matcher(this)
        val isMatch = m.matches()
        if (!isMatch) {
            return false
        }
        return isMatch
    }
}

fun EditText.isEmpty():Boolean{
    if(this.text.trim().toString().isEmpty()){
        this.context.showToast(this.hint.trim().toString())
        return true;
    }
    return false
}

fun ImageView.load(url:String){
    if(url.isEmpty())return
    val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
    options.scaleType = ImageView.ScaleType.FIT_XY
    ILFactory.getLoader().loadNet(this, Api.IMAGE_URL + url, null)
}

fun ImageView.load(id:Int){
    val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
    options.scaleType = ImageView.ScaleType.FIT_XY
    ILFactory.getLoader().loadResource(this, id, null)
}

val sdf = SimpleDateFormat("dd日 HH:mm")

val sdfYearAndMonth = SimpleDateFormat("yyyy年MM月")

val sdfYyyyMMddHHmm = SimpleDateFormat("yyyy年MM月dd日 HH:mm")

val sdfYyyyMMdd2 = SimpleDateFormat("yyyy / MM / dd")

val sdfHHmm = SimpleDateFormat("yyyy / MM / dd")

fun Long.toHHmm():String{
    val date = Date()
    date.time = this * 1000
    return sdfHHmm.format(date)
}

fun Long.toYyyyMMddWithSlash():String{
    val date = Date()
    date.time = this * 1000
    return sdfYyyyMMdd2.format(date)
}

fun Long.toYyyyMMddHHmm():String{
    val date = Date()
    date.time = this * 1000
    return sdfYyyyMMddHHmm.format(date)
}

fun Long.toYearAndMonth():String{
    val date = Date()
    date.time = this * 1000
    return sdfYearAndMonth.format(date)
}

fun Long.toDate():String{
    val date = Date()
    date.time = this * 1000
    return sdf.format(date)
}

fun Date.calendar():Calendar{
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar;
}

fun TextView.gone():TextView{
    this.visibility = View.GONE
    return this
}

fun TextView.visible():TextView{
    this.visibility = View.VISIBLE
    return this
}

fun TextView.invisible():TextView{
    this.visibility = View.INVISIBLE
    return this
}

inline fun <reified T: IModel> Flowable<T>.transfer(v: HRIView<*>): Flowable<T> {
    return this.compose(XApi.getApiTransformer<T>())
            .compose(XApi.getScheduler<T>())
            .compose(v.bindToLifecycle<T>())
            .onErrorResumeNext(io.reactivex.functions.Function<Throwable, Publisher<out T>> {
                v.hideIndicator()
                v.showError(it.message!!)
                return@Function Publisher<T> { s -> s.onComplete() }
            })
}


