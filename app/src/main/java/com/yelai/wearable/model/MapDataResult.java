package com.yelai.wearable.model;

import java.util.Map;

/**
 * Created by wanglei on 2016/12/10.
 */

public class MapDataResult extends BaseModel {

    private Map data;

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    @Override
    public boolean isNull() {
        return false;
    }


    public String getValue(String key){

        try {
            if (data.containsKey(key)){
                return data.get(key).toString();
            }else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
