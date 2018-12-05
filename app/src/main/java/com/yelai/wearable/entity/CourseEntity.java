package com.yelai.wearable.entity;

import java.io.Serializable;

/**
 * Created by hr on 18/9/16.
 */

public class CourseEntity implements Serializable{

    private int background;

    private String name;

    private String teacher;

    private String time;

    private int type;

    private boolean isOwner;

    public CourseEntity(int background, String name, String teacher, String time, int type, boolean isOwner) {
        this.background = background;
        this.name = name;
        this.teacher = teacher;
        this.time = time;
        this.type = type;
        this.isOwner = isOwner;
    }

    public CourseEntity() {
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
