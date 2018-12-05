package com.yelai.wearable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hr on 18/11/8.
 * type : 1、教学课 2、训练课 3、兴趣课
 * times: [{“week”:3,”start”:”16:20”,”end”:”18:30”},{“week”:6,”start”:”16:20”,”end”:”18:30”}]
 */

public class CourseTime implements Serializable {

    private String id;

    private String title;

    private String type;

    private String times;//总可数
    private String number;//已上多少次

    private String timesId;

    private String startTime;

    private String endTime;

    private String timeStr;
    private String lessonId;

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTimesId() {
        return timesId;
    }

    public void setTimesId(String timesId) {
        this.timesId = timesId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
