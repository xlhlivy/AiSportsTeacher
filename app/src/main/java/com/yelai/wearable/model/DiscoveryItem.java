package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by wanglei on 2016/12/10.
 */

public class DiscoveryItem implements Serializable {

    private String id;
    private String title;
    private String pic;
    private String desc;
    private String mark;
//    内容类型 1：文本 2：链接
    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "DiscoveryItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", desc='" + desc + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }

}
