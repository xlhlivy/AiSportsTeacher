package com.yelai.wearable.model;

import com.google.gson.annotations.SerializedName;
import com.yelai.wearable.entity.Time;
import com.yelai.wearable.entity.Time4Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.Transient;

/**
 * Created by hr on 18/11/8.
 * type : 1、教学课 2、训练课 3、兴趣课
 * times: [{“week”:3,”start”:”16:20”,”end”:”18:30”},{“week”:6,”start”:”16:20”,”end”:”18:30”}]
 */

public class Course implements Serializable {

    private String id;

    private String title;

    private String type;

    private String times;//总可数

    private String count;//已上多少次

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Transient
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @SerializedName("teacher_id")
    private String teacherId;

    private String teacherName;

    private String notice;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return id != null ? id.equals(course.id) : course.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    private List<Time4Server> timeList;

    public List<Time4Server> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time4Server> timeList) {
        this.timeList = timeList;
    }

    public List<Time>  list(){

        List<Time> times = new ArrayList<>();

        for (Time4Server item : timeList) {
            times.add(item.convert());
        }
        return times;

    }

}
