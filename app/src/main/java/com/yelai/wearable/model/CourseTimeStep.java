package com.yelai.wearable.model;

import java.io.Serializable;
import java.util.List;

import kotlin.jvm.Transient;

/**
 * Created by hr on 18/11/8.
 * type : 1、教学课 2、训练课 3、兴趣课
 * times: [{“week”:3,”start”:”16:20”,”end”:”18:30”},{“week”:6,”start”:”16:20”,”end”:”18:30”}]
 */

public class CourseTimeStep implements Serializable {

    @Transient
    private boolean isParent = false;

    private int method = 1;// 1 add 2 modify

    private String id;

    private String timesId;

    private String name;

    private String time;

    private List<CourseTimeStep> child;

    @Transient
    private CourseTimeStep parent;

    public CourseTimeStep getParent() {
        return parent;
    }

    public void setParent(CourseTimeStep parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimesId() {
        return timesId;
    }

    public void setTimesId(String timesId) {
        this.timesId = timesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<CourseTimeStep> getChild() {
        return child;
    }

    public void setChild(List<CourseTimeStep> child) {
        this.child = child;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }
}
