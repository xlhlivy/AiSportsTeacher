package com.yelai.wearable.sdk.controller;

import android.util.Log;

import com.yelai.wearable.sdk.helper.BleHelper;

/**
 * Created by Administrator on 2016/12/28.
 */
public class Notification {

    public static final int SEND_NOTI_TYPE_QQ=0;
    public static final int SEND_NOTI_TYPE_WECHAT=1;
    public static final int SEND_NOTI_TYPE_PHONE=2;
    public static final int SEND_NOTI_TYPE_SMS=3;
    public static final int SEND_NOTI_TYPE_FACEBOOK=4;
    public static final int SEND_NOTI_TYPE_TWITTER=5;
    public static final int SEND_NOTI_TYPE_GMAIL=6;
    public static final int SEND_NOTI_TYPE_WHATSUP=7;

    private final String TAG="Notification";

    private BleHelper service;

//    private String packageName;     //发送应用的包名

    private int type;               //由哪个应用发送

    private String content;         //消息内容

    public Notification(BleHelper service, int type, String content) {
        this.service = service;
        this.type = type;
        this.content = content;

    }

    public void sendNotifi(int id) {
        if (content==null){
            byte[] value = getBleSendValue(1, type, id);
            service.set_message_notify(value);
            return;
        }
        int count=getPackageCount();
        for (int i=0;i<count;i++){
            String send;
            if (i!=count-1) {
                send = content.substring(i * 8, i * 8 + 8);
            }else {
                if (count*8>content.length()){
                    send=content.substring(i*8,content.length());
                }else {
                    send=content.substring(i*8,count*8);
                }
            }
            Log.d(TAG, "send:" + send + "    id:" + id + "   type" + type + "    count:" + count);
            byte[] value=getBleSendValue(count, type, id, send.toCharArray());
            service.set_message_notify(value);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPackageCount(){
        int count=0;
        if (content.length()>=24){
            count=3;
        }else {
            int merchant=content.length()/8;
            int remainder=content.length()%8;
            if (remainder==0){
                count=merchant;
            }else {
                count=merchant+1;
            }
        }
        return count;
    }

    private byte[] getBleSendValue(int count,int type,int id) {
        byte[] value=new byte[20];
        value[0]=0x20;
        value[1]= (byte) id;
        value[2]= (byte) count;
        value[3]= (byte) type;
        return value;
    }

    private byte[] getBleSendValue(int count,int type,int id,char[] content){
        byte[] value=new byte[20];
        value[0]=0x20;
        value[1]= (byte) id;
        value[2]= (byte) count;
        value[3]= (byte) type;
        for (int i=0;i<content.length;i++){
            byte[] b=charToByte(content[i]);
            value[4+(i*2)]=b[0];
            value[5+(i*2)]=b[1];
        }
        if (content.length<8){
            for (int i=4+content.length*2;i<value.length;i++){
                value[i]=0;
            }
        }
        return value;
    }

    public  byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }
}
