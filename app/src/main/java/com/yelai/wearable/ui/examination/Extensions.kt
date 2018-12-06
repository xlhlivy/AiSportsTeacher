package com.yelai.wearable.ui.examination

/**
 * Created by hr on 18/11/11.
 *  * status： 0：未开考 1：考试中 2：考试完成
 * type : 1：身高体重 2：握力 3：引体向上 4：仰卧起坐 5：1000/800m跑步 6：50m跑步 7：肺活量 8：做立体前驱
 */

val examTypes = arrayOf("身高体重", "握力", "引体向上", "仰卧起坐", "1000/800m跑步", "50m跑步", "肺活量", "做立体前驱")

fun String.toExamStatus(): String {
    when (this) {
        "0" -> {
            return "未开始考试"
        }
        "1" -> {
            return "考试中"
        }
        "2" -> {
            return "考试完成"
        }
    }
    return this
}

fun String.toExamType(): String {
    try {
        return examTypes[this.toInt() - 1]
    } catch (e: Exception) {
    }
    return this
}

fun Long.to50Txt(): String {
    val second = this / 1000
    return "$second${((this - second * 1000) / 10)}"
}

fun Long.to800Txt(): String {
    return secondsToString((this).toInt())
}

fun secondsToString(time: Int): String {
    val second = time % 60
    val minute = time / 60 % 60
    val hour = time / 60 / 60
    val secondStr = if (second < 10) "0$second" else second.toString()
    val minuteStr = if (minute < 10) "0$minute" else minute.toString()
    val hourStr = if (hour < 10) "0$hour" else hour.toString()
    return (if (hour == 0) "" else "${hourStr}h") + minuteStr + "′" + secondStr + "″"
}