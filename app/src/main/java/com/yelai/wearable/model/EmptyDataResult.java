package com.yelai.wearable.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wanglei on 2016/12/10.
 */

public class EmptyDataResult extends BaseModel {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean isNull() {
        return false;
    }


    public String getValue(String key){

        try {
            JSONObject jsonObject = new JSONObject(data);

            if (jsonObject.has(key)){
                return jsonObject.getString(key);
            }else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
