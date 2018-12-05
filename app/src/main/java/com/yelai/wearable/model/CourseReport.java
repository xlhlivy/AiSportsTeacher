package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/8.
 * type : 1、教学课 2、训练课 3、兴趣课
 * times: [{“week”:3,”start”:”16:20”,”end”:”18:30”},{“week”:6,”start”:”16:20”,”end”:”18:30”}]
 */

public class CourseReport implements Serializable {


    private String title;

    private String type;

    private String times;//总可数
    private String number;//已上多少次

    private String timeStr;//上课时间

    private String strength;//运动强度

    private String density;//运动密度

    private String step;//平均步数

    private String motion;//平均运动量

    private String heart;//平均心率

    private String consume;//平均消耗

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }
}
