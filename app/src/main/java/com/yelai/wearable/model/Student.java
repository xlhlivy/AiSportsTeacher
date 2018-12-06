package com.yelai.wearable.model;

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

public class Student implements Serializable {


    private String headImg;

    private String name;
//    签到（0：未签到 1：已签到 2：请假）
    private int signed;//签到

//    abnormal：异常（0：否 1：是）
    private int abnormal;//异常

//    private int memberId;//

    private int heart;//

    /**
     * 仅用于传递参数
     */
    private String courseType;

    /**
     * 仅用于传递参数
     */
    private String timesId;
    /**
     * member_id : 1012
     */

    private int member_id;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getTimesId() {
        return timesId;
    }

    public void setTimesId(String timesId) {
        this.timesId = timesId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSigned() {
        return signed;
    }

    public void setSigned(int signed) {
        this.signed = signed;
    }

    public int getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(int abnormal) {
        this.abnormal = abnormal;
    }

//    public int getMemberId() {
//        return memberId;
//    }

//    public void setMemberId(int memberId) {
//        this.memberId = memberId;
//    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
}
