package com.yelai.wearable.entity;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kotlin.jvm.Transient;

/**
 * Created by hr on 18/11/9.
 */

public class Time implements Serializable {

    private int weekIndex = -1;

    private Date startTime;

    private Date endTime;

    private int index;
    private TextView bindView;

    public String text(){

        String text = week() + startTimeText() + "~" + endTimeText();

        if(text.length() < 4) return  "";

        return text;

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TextView getBindView() {
        return bindView;
    }

    public void setBindView(TextView bindView) {
        this.bindView = bindView;
    }

    public static Time defaultTime(int index, TextView bindView){

        Time time = new Time();

        time.weekIndex = 0;

        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY,10);
        start.set(Calendar.MINUTE,0);

        time.startTime = start.getTime();


        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY,10);
        end.set(Calendar.MINUTE,45);

        time.endTime = end.getTime();

        time.index = index;

        time.bindView = bindView;


        return time;

    }


    public void copyFrom(Time time){
        this.weekIndex = time.weekIndex;
        this.startTime = time.startTime;
        this.endTime = time.endTime;
        this.index = time.index;
        this.bindView = time.bindView;
    }


    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public static final List<String> weekData = new ArrayList<>();

    static {
        weekData.add("星期一");
        weekData.add("星期二");
        weekData.add("星期三");
        weekData.add("星期四");
        weekData.add("星期五");
        weekData.add("星期六");
        weekData.add("星期天");
    }

    public String week(){
        if(weekIndex == -1)return "";
        return weekData.get(weekIndex);
    }



    public String startTimeText(){
        if(startTime == null)return  "";
        return sdf.format(startTime);
    }


    public String endTimeText(){
        if(endTime == null) return "";
        return sdf.format(endTime);
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (weekIndex != time.weekIndex) return false;
        if (startTime != null ? !startTime.equals(time.startTime) : time.startTime != null)
            return false;
        return endTime != null ? endTime.equals(time.endTime) : time.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = weekIndex;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    public Time4Server convert(){
        Time4Server time4Server = new Time4Server();
        time4Server.setWeek(this.weekIndex + 1);
        time4Server.setStart(this.startTimeText());
        time4Server.setEnd(this.endTimeText());
        return time4Server;
    }

}
