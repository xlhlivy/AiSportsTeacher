package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by wanglei on 2016/12/10.
 */

public class DiscoveryDetail implements Serializable {

    private String id;
    private String title;
    private String pic;
    private String content;
    private String mark;
    public String createTime;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }



    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DiscoveryDetail{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", content='" + content + '\'' +
                ", mark='" + mark + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
