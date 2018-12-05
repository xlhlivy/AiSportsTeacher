package com.yelai.wearable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hr on 18/11/8.
 *
 * member_id :学员ID
 * name : 姓名
 * signed：签到（0：未签到 1：已签到 2：请假）
 * abnormal：异常（0：否 1：是）
 * heart:心率
 */

public class StudentInfo implements Serializable {

    private String trueName;
    private String weight;
    private String height;
    private String heartRate;

    private String bmi;

    private String quiteHeartRate;

    private String pulse;//晨脉

    private String sleep;//睡眠时间

    private String health;//身体状况

    private String dayMotion;//当日运动量

    private String lessonMotion;//本课程运动量

    private String motionTime;//有效运动时间

    private String motionRange;//有效运动距离

    private String motionNumber;//有效运动次数


    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getQuiteHeartRate() {
        return quiteHeartRate;
    }

    public void setQuiteHeartRate(String quiteHeartRate) {
        this.quiteHeartRate = quiteHeartRate;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getDayMotion() {
        return dayMotion;
    }

    public void setDayMotion(String dayMotion) {
        this.dayMotion = dayMotion;
    }

    public String getLessonMotion() {
        return lessonMotion;
    }

    public void setLessonMotion(String lessonMotion) {
        this.lessonMotion = lessonMotion;
    }

    public String getMotionTime() {
        return motionTime;
    }

    public void setMotionTime(String motionTime) {
        this.motionTime = motionTime;
    }

    public String getMotionRange() {
        return motionRange;
    }

    public void setMotionRange(String motionRange) {
        this.motionRange = motionRange;
    }

    public String getMotionNumber() {
        return motionNumber;
    }

    public void setMotionNumber(String motionNumber) {
        this.motionNumber = motionNumber;
    }
}
