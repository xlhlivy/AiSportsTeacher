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

public class Movement {
    private SDK sdk;

    public Movement(Context context,SDKCallBack sdkCallBack) {
        if(this.sdk == null) {
            this.sdk = new SDK(context, 1,sdkCallBack);
        }

    }

    public void disconnectDevice() throws NoConnectException {
        this.sdk.disconnectDevice();
    }

    /**
     * 设置当前时间及白天时间
     * Set bracelet or watch current time and day time
     * @param cal   当前日期时间  current time
     * @param hour_begin    白天时间开始时间    start time of day
     * @param hour_end      白天时间结束时间    end time of day
     * @return              结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject setDateTime(Calendar cal,int hour_begin, int hour_end) throws NoConnectException {
        return this.sdk.setDateTime(cal, hour_begin, hour_end);
    }

    /**
     * 设置闹钟（最多支持三个）
     * Set alarm clock (up to three)
     * @param alarm_hour_1      闹钟1小时       The first alarm hour
     * @param alarm_minute_1    闹钟1分钟       The first alarm minute
     * @param alarm_repeat_1    闹钟1重复日期   The first alarm repeat
     * @param alarm_hour_2      闹钟2小时       The second alarm hour
     * @param alarm_minute_2    闹钟2分钟       The second alarm minute
     * @param alarm_repeat_2    闹钟2重复日期   The second alarm repeat
     * @param alarm_hour_3      闹钟3小时       The second alarm hour
     * @param alarm_minute_3    闹钟3分钟       The first alarm minute
     * @param alarm_repeat_3    闹钟3重复日期   The first alarm repeat
     * @return                  结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject setAlarm(int alarm_hour_1, int alarm_minute_1, int alarm_repeat_1, int alarm_hour_2, int alarm_minute_2, int alarm_repeat_2, int alarm_hour_3, int alarm_minute_3, int alarm_repeat_3) throws NoConnectException {
        return this.sdk.setAlarm(alarm_hour_1, alarm_minute_1, alarm_repeat_1, alarm_hour_2, alarm_minute_2, alarm_repeat_2, alarm_hour_3, alarm_minute_3, alarm_repeat_3);
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

    /**
     * 设置目标步数
     * Set target steps
     * @param goalStep  目标步数    target steps
     * @return          结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject setTarget(int goalStep) throws NoConnectException {
        return this.sdk.setTarget(goalStep);
    }

    /**
     * 来电提醒
     * call reminder
     * @return 结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject writeCallReminder() throws NoConnectException {
        return this.sdk.writeCallReminder();
    }

    /**
     * 完成同步
     * Complete synchronization
     * @throws NoConnectException
     */
    public void setFinishSync() throws NoConnectException {
        this.sdk.setFinishSync();
    }

    /**
     * 强制绑定
     * Forced binding
     * @return  结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject setBoundStateNoConfirm() throws NoConnectException {
        return this.sdk.setBoundStateNoConfirm();
    }

    /**
     * 获取电量
     * Acquisition power
     * @return  电量  power
     * @throws NoConnectException
     */
    public JSONObject getBattery() throws NoConnectException {
        return this.sdk.getBattery();
    }

    /**
     * 获取版本号
     * Get version
     * @return  版本号     version
     * @throws NoConnectException
     */
    public JSONObject getVersion() throws NoConnectException {
        return this.sdk.getVersion();
    }

    /**
     * 获取活动记录
     * Get active record
     * @return  活动记录    active record
     * @throws NoConnectException
     */
    public JSONObject getActivityCount() throws NoConnectException {
        return this.sdk.getActivityCount();
    }

    /**
     * 获取活动数据
     * Get active data
     * @param sn    获取序列为sn的活动      Get sequence sn activity
     * @param hour  从hour小时起6小时的数据        Data from 6 hours
     * @return      JSONObject 格式数据，返回6个小时的数据   Returns 6 hours of data
     * @throws NoConnectException
     */
    public JSONObject getActivityBySn(int sn, int hour) throws NoConnectException {
        return this.sdk.getActivityBySn(sn, hour);
    }

    /**
     * 获取睡眠数据
     * Get sleep data
     * @param sn    获取序列为sn的睡眠           Get sequence sn sleep data
     * @param hour  从hour小时起6小时的数据      Data from 6 hours
     * @return      JSONObject 格式数据，返回6个小时的数据   Returns 6 hours of data
     * @throws NoConnectException
     */
    public JSONObject getSleepBySn(int sn, int hour) throws NoConnectException {
        return this.sdk.getSleepBySn(sn, hour);
    }

    /**
     * 获取手环序号，仅对TRASENSE品牌下有屏幕的手环有效。
     * Get Bracelet number, only the TRASENSE brand which have screen's bracelet effective.
     * @return  手环序号    Bracelet number
     * @throws NoConnectException
     */
    public JSONObject getID() throws NoConnectException {
        return this.sdk.getID();
    }

    /**
     * 设置触摸震动
     * Set touch vibration
     * @param isOpen    开关      switch
     * @param time      震动时间    1-255   Vibration time 1-255
     * @throws NoConnectException
     */
    public void setTouchVibration(boolean isOpen, int time) throws NoConnectException {
        this.sdk.setTouchVibration(isOpen, time);
    }

    /**
     * 设置公英制切换，仅对TRASENSE品牌下有屏幕的手环有效。
     * Set switch metric，only the TRASENSE brand which have screen's bracelet effective
     * @param unitType      公英制
     *                      {@link com.yelai.wearable.sdk.global.Global#TYPE_UNIT_METRIC}
     *                      {@link com.yelai.wearable.sdk.global.Global#TYPE_UNIT_IMPERIAL}
     * @throws NoConnectException
     */
    public void setUnit(int unitType) throws NoConnectException {
        this.sdk.setUnit(unitType);
    }

    /**
     * 设置时间制式
     * Set time format
     * @param timeType  12/24小时     12/24 hours
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_TIME_FORM_12}
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_TIME_FORM_24}
     * @throws NoConnectException
     */
    public void setTimeForm(int timeType) throws NoConnectException {
        this.sdk.setTimeForm(timeType);
    }

    /**
     * 心率操作
     * Heart rate operation
     * @param heartType     操作类型        Operation type
     *                      {@link com.yelai.wearable.sdk.global.Global#TYPE_HEART_START}
     *                      {@link com.yelai.wearable.sdk.global.Global#TYPE_HEART_STOP}
     *                      {@link com.yelai.wearable.sdk.global.Global#TYPE_HEART_GET}
     * @return  开启结果或心率数值       Open results or heart rate values
     * @throws NoConnectException
     */
    public JSONObject writeHeart(byte heartType) throws NoConnectException {
        return this.sdk.writeHeart(heartType);
    }


    /**
     * 设置心率预警
     * Heart rate warning
     * @param isOpen        是否开启心率预警           is open  Heart rate warning
     * @param high          预预警高值                 Early warning high value
     * @param low           预警低值                   Early warning low value
     * @param isAlwaysLight 是否测量心率时常亮          Is the screen  always bright
     * @return  设置心率预警结果                        Set heart rate warning results
     * @throws NoConnectException
     */
    public JSONObject writeHeartWarn(boolean isOpen,int high,int low,boolean isAlwaysLight) throws NoConnectException {
        return this.sdk.writeHeartWarn(isOpen, high, low, isAlwaysLight);
    }


    /**
     * 设置震动设置
     * Set vibration settings
     * @param type      类型
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_VIBRATION_CALL}
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_VIBRATION_DISCONNECTION}
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_VIBRATION_ALARM1}...
     * @param vibration     15S震动方式            15s Vibration mode
     * @return          结果                      results
     * @throws NoConnectException
     */
    public JSONObject setVibrationSettings(int type ,byte[] vibration) throws NoConnectException {
        return this.sdk.setVibrationSettings(type,vibration);
    }

    /**
     * 设置血压校准
     * Set pressure calibration
     * @param SBP   收缩压     systolic pressure   0-255
     * @param DBP   舒张压     diastolic pressure  0-255
     * @return
     * @throws NoConnectException
     */
    public JSONObject write_blood_pressure(int SBP,int DBP) throws NoConnectException {
        return this.sdk.write_blood_pressure(SBP, DBP);
    }

    /**
     * 消息提醒
     * Message reminder
     * @param content   消息内容（手表直接传NULL） Message content(Watch direct transmission NULL)
     * @param type      消息类型    Message type
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_QQ}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_FACEBOOK}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_GMAIL}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_PHONE}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_SMS}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_TWITTER}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_WECHAT}
     *                  {@link com.yelai.wearable.sdk.global.Config#SEND_NOTI_TYPE_WHATSUP}
     * @param id        消息id（自增长到255从1重新计数）
     * @return          结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject write_message_notify(String content,int type,int id) throws NoConnectException {
        return this.sdk.write_message_notify(content, type, id);
    }

    /**
     * 旧协议短信提醒
     * Old protocol SMS alert
     * @param number    发件人号码   Sender number
     * @return  结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject write_sms_reminder_old(String number) throws NoConnectException {
        return this.sdk.write_sms_reminder_old(number);
    }

    /**
     * 设置手环进入遥控拍照模式
     * Set the bracelet into the remote camera mode
     * @param type      进入或退出   Enter or exit
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_CAMERA_START}
     *                  {@link com.yelai.wearable.sdk.global.Global#TYPE_CAMERA_END}
     * @return          结果（0成功其他失败） result（0success，others fail）
     * @throws NoConnectException
     */
    public JSONObject setCamera(int type) throws NoConnectException {
        return this.sdk.setCamera(type);
    }

    /**
     * 写入自定义数据
     * Write custom data
     * @param value 写入数据    Write data  (length  0-20)
     *              通过{@link com.yelai.wearable.sdk.model.SDKCallBack#onSelfDeviceResponse}返回
     *              By return {@link com.yelai.wearable.sdk.model.SDKCallBack#onSelfDeviceResponse}
     * @throws NoConnectException
     */
    public void self_writeData(byte[] value) throws NoConnectException {
        this.sdk.self_writeData(value);
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
     *              如果缓存中不存在对应的设备，即便设备贴着手机也是连接不到的。
     * Notice:This should be called when you have already found the device.
     */
    public void connect(String mac){
        this.sdk.connect(mac);
    }

    /**
     * 开启蓝牙通知(只有开启了通知才会手到回调),所以建议连接成功后调用此方法否则可能拿不到数据。
     * Open the Bluetooth notification (only to open the notification will be callback),
     * so it is recommended to connect successfully after the call to this method or you may not get data
     */
    public void openDescriptor(){
        this.sdk.openDescriptor();
    }
}
