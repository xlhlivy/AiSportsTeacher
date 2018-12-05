package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/12/3.
 */

public class Qrcode implements Serializable{
    private String qrcode;


    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
