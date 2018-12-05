package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by wanglei on 2016/12/10.
 */

public class Page<T> implements Serializable {

    public Page(){

    }

    public Page(T data){
        currPage = 1;
        count = 1;
        pages = 1;
        this.data = data;
    }


    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private int count;

    private int pages;

    private int currPage;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }


    public boolean hasMore(){
        return currPage != pages;
    }


}
