package com.yelai.wearable.model;

import java.io.Serializable;

import kotlin.jvm.Transient;

/**
 * Created by hr on 18/11/12.
 */

public class ExaminationScore implements Serializable {

    private String memberId;
    private String name;
    private String headImg;
    private String timeStr;
    private String time;
    private String height;


    @Transient
    private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String weight;
    private String result;

//    @JsonAdapter(EmptyStringTypeAdapter::class) var memberId:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var name:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var headImg:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var timeStr:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var time:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var height:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var weight:String = ""
//
//    @JsonAdapter(EmptyStringTypeAdapter::class) var result:String = ""


}
