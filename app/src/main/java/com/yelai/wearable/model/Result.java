package com.yelai.wearable.model;

/**
 * Created by wanglei on 2016/12/10.
 */

public class Result<T> extends BaseModel {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isNull() {
        return false;
    }

}
