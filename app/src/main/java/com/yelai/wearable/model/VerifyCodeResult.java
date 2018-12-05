package com.yelai.wearable.model;

/**
 * Created by wanglei on 2016/12/10.
 */

public class VerifyCodeResult extends BaseModel {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean isNull() {
        return data == null || data.isEmpty();
    }


}
