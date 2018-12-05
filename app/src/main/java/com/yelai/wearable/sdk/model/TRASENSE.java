//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yelai.wearable.sdk.model;

import android.content.Context;
import com.yelai.wearable.sdk.helper.NoConnectException;
import com.yelai.wearable.sdk.helper.SDK;
import java.util.Calendar;

import org.json.JSONObject;

public class TRASENSE {
    private SDK sdk;

    public TRASENSE(Context context) {
        this(context,null);
    }

    public TRASENSE(Context context,SDKCallBack sdkCallBack) {
        if(this.sdk == null) {
            this.sdk = new SDK(context, 2,sdkCallBack);
        }

    }

    public JSONObject scanDevice(int deviceName, String mac) {
        return this.sdk.scanDevice(deviceName, mac);
    }
//
//
//
//    public Map<String, Integer> scanDeviceAll(int deviceName, int scanSeconds) {
//        return this.sdk.scanDevice(deviceName, scanSeconds);
//    }

    public void disconnectDevice() throws NoConnectException {
        this.sdk.disconnectDevice();
    }

    /**
     * 设置个人数据
     * Set personal data
     * @param height    身高(cm) 60-250    height(cm) 60-250
     * @param weight    体重(kg) 10-250    weight(kg) 10-250
     * @param gender    性别(0,1) 0为女，1为男     gender 0 means female,1 means male
     * @param age       年龄     5-120     age  5-120
     * @return          结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject setProfile(double height, double weight, int gender,int age) throws NoConnectException {
        return this.sdk.setProfile(height, weight, gender, age);
    }

    public JSONObject setDateTime(Calendar cal) throws NoConnectException {

        return this.sdk.setDateTime(cal, 0, 0);
    }

    public JSONObject setAlarm(int alarm_hour_1, int alarm_minute_1, int alarm_repeat_1, int alarm_hour_2, int alarm_minute_2, int alarm_repeat_2, int alarm_hour_3, int alarm_minute_3, int alarm_repeat_3) throws NoConnectException {
        return this.sdk.setAlarm(alarm_hour_1, alarm_minute_1, alarm_repeat_1, alarm_hour_2, alarm_minute_2, alarm_repeat_2, alarm_hour_3, alarm_minute_3, alarm_repeat_3);
    }

    public JSONObject setTarget(int goalStep) throws NoConnectException {
        return this.sdk.setTarget(goalStep);
    }

    public JSONObject writeCallReminder() throws NoConnectException {
        return this.sdk.writeCallReminder();
    }

    public JSONObject setFinishSync() throws NoConnectException {
        return this.sdk.setFinishSync();
    }

    public JSONObject setBoundStateNoConfirm() throws NoConnectException {
        return this.sdk.setBoundStateNoConfirm();
    }

    public JSONObject setBoundState(int type) throws NoConnectException {
        return this.sdk.setBoundState(type);
    }

    public JSONObject getBoundInfo() throws NoConnectException {
        return this.sdk.getBoundInfo();
    }

    public JSONObject getBattery() throws NoConnectException {
        return this.sdk.getBattery();
    }

    public JSONObject getVersion() throws NoConnectException {
        return this.sdk.getVersion();
    }

    public JSONObject getActivityCount() throws NoConnectException {
        return this.sdk.getActivityCount();
    }

    public JSONObject getActivityBySn(int sn, int hour) throws NoConnectException {
        return this.sdk.getActivityBySn(sn, hour);
    }

    public JSONObject setDayMode(int hour_begin, int hour_end) throws NoConnectException {
        return this.sdk.setDayMode(hour_begin, hour_end);
    }


    /**
     * 开启扫描的方法
     * Method for scanning
     * @param names     提供需要搜索的设备名
     *                   Provide the name of the device to search for
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_SH05}
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_SH06}
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_SH08}
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_SH09}
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_SH09U}
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_H03_PRO}
     *                  {@link com.yelai.wearable.sdk.global.Config#DEVICE_Wristband}
     *                  可以自定义
     *                  Can customize
     */
    public void scan(String[] names){
        this.sdk.scan(names);
    }


    /**
     * 停止扫描
     * Stop scanning
     */
    public void stopScan(){
        this.sdk.stopScan();
    }


    /**
     * 蓝牙设备连接
     * Bluetooth connect
     * @param mac   蓝牙设备MAC地址
     *               Bluetooth device MAC address
     *
     * 注意:本方法不能在没有扫描到设备的时候直接调用,手机蓝牙扫描的时候会有设备缓存，
     *              如果缓存中部存在对应的设备，即便设备贴着手机也是连接不到的。
     * Notice:This should be called when you have already found the device.
     */
    public void connect(String mac){
        this.sdk.connect(mac);
    }

    /**
     * 开启蓝牙通知(只有开启了通知才会受到回调),所以建议连接成功后调用此方法否则可能拿不到数据。
     * Open the Bluetooth notification (only to open the notification will be callback),
     * so it is recommended to connect successfully after the call to this method or you may not get data
     */
    public void openDescriptor(){
        this.sdk.openDescriptor();
    }

}
