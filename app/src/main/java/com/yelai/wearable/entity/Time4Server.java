package com.yelai.wearable.entity;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hr on 18/11/9.
 */

public class Time4Server implements Serializable {

    private int week;

    private String start;

    private String end;

    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.start = startTime;
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.end = endTime;
        this.endTime = endTime;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time4Server that = (Time4Server) o;

        if (week != that.week) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        return end != null ? end.equals(that.end) : that.end == null;
    }

    @Override
    public int hashCode() {
        int result = week;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    public Time convert(){
        Time time = Time.defaultTime(0,null);

        time.setWeekIndex(week - 1);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            time.setStartTime(sdf.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            time.setEndTime(sdf.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;

    }


}
