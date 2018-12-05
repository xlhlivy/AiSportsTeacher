package com.yelai.wearable.model;

/**
 * Created by hr on 18/11/11.
 */

public class PageFactory<T> {

    public static <T> Page<T> instance(){
        Page<T> page = new Page<>();
        return page;
    }

}
