//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yelai.wearable.sdk.helper;

import android.content.Context;
import android.util.Log;

import com.yelai.wearable.sdk.controller.Notification;
import com.yelai.wearable.sdk.global.Config;
import com.yelai.wearable.sdk.model.SDKCallBack;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

public class SDK {
    private BleHelper mBleHelper = null;
    private Context mContext = null;
    private final String tag = "SDK";
    private final byte[] lock=new byte[0];
//    private BroadcastReceiver myBLEBroadcastReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if(!action.equals("com.gzgamut.max.bluetooth.le.ACTION_DEVICE_FOUND") && !action.equals("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED")) {
//                if(action.equals("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL")) {
//                    Log.w("SDK", "connect fail");
//                } else if(action.equals("com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED")) {
//                    Log.d("SDK", "disconnected");
//                } else if(action.equals("com.gzgamut.max.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED")) {
//                    Log.d("SDK", "connect success");
////                    SDK.this.mBleHelper.set_notify_true();
//                }
//            }
//
//        }
//    };
    private Timer time_out;
    private Timer time_out_bound;
    private boolean isrunning = false;

    public SDK(Context context, int deviceType,SDKCallBack sdkCallBack) {
        this.mContext = context;
        this.mBleHelper = new BleHelper.Builder(context,sdkCallBack).setDeviceType(deviceType).build();
    }

//    private void initBroadcastReceiver() {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED");
//        intentFilter.addAction("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL");
//        intentFilter.addAction("com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED");
//        intentFilter.addAction("com.gzgamut.max.bluetooth.le.ACTION_DEVICE_FOUND");
//        intentFilter.addAction("com.gzgamut.max.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
//        this.mContext.registerReceiver(this.myBLEBroadcastReceiver, intentFilter);
//    }

    public JSONObject scanDevice(int deviceName, String mac) {
        JSONObject objectMac = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() != 2) {
                Log.d("SDK", "start scan device,   name: " + Config.DEVICE_NAME[deviceName] + ",    mac: " + mac);
                this.mBleHelper.setDeviceName(deviceName);
                this.mBleHelper.setMacBounded(mac);
                this.mBleHelper.scan(false);
                this.mBleHelper.scan(true);
                if (mac == null) {
                    this.syncTimeOut();

                    while (this.isrunning) {
                        ;
                    }

                    Map mBluetoothDeviceAddress1 = this.mBleHelper.getDeviceMap();
                    if (mBluetoothDeviceAddress1 != null && mBluetoothDeviceAddress1.size() > 0) {
                        this.mBleHelper.connectDevice();
                        this.syncTimeOut();

                        while (this.mBleHelper.getMac() == null && this.isrunning) {
                            ;
                        }

                        objectMac = this.mBleHelper.getMac();
                    }
                } else {
                    this.syncTimeOut();

                    while (this.mBleHelper.getMac() == null && this.isrunning) {
                        ;
                    }

                    objectMac = this.mBleHelper.getMac();
                }

                this.mBleHelper.setMacBounded((String) null);
                this.mBleHelper.setMac((JSONObject) null);
            } else if (this.mBleHelper != null) {
                String mBluetoothDeviceAddress = this.mBleHelper.getDeviceAddress();
                Log.d("SDK", "already connceted a device: " + mBluetoothDeviceAddress);

                try {
                    objectMac.put("mac", mBluetoothDeviceAddress);
                } catch (JSONException var6) {
                    var6.printStackTrace();
                }
            }
            this.cancelTimer(this.time_out);
            this.mBleHelper.scan(false);
            this.mBleHelper.clearDeviceMap();
            return objectMac;
        }
    }

    public Map<String, Integer> scanDevice(int deviceName, int scanSeconds) {
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() != 2) {
                Log.d("SDK", "start scan device,   name: " + Config.DEVICE_NAME[deviceName]);
                this.mBleHelper.setDeviceName(deviceName);
                this.mBleHelper.scan(false);
                this.mBleHelper.scan(true);
                this.syncTimeOut(scanSeconds);

                while (this.isrunning) {
                    ;
                }

                this.mBleHelper.scan(false);
                HashMap mBluetoothDeviceAddress1 = new HashMap();
                mBluetoothDeviceAddress1.putAll(this.mBleHelper.getDeviceMap());
                this.mBleHelper.clearDeviceMap();
                return mBluetoothDeviceAddress1;
            } else if (this.mBleHelper != null) {
                String mBluetoothDeviceAddress = this.mBleHelper.getDeviceAddress();
                Log.d("SDK", "already connceted a device: " + mBluetoothDeviceAddress);
                Map deviceMap = this.mBleHelper.getDeviceMap();
                deviceMap.put(mBluetoothDeviceAddress, Integer.valueOf(0));
                this.mBleHelper.scan(false);
                return deviceMap;
            } else {
                this.cancelTimer(this.time_out);
                this.mBleHelper.scan(false);
                this.mBleHelper.clearDeviceMap();
                return null;
            }
        }
    }

    public void disconnectDevice() throws NoConnectException {
        JSONObject result = new JSONObject();
        if(this.mBleHelper != null) {
            this.mBleHelper.scan(false);
        }
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
//                this.mBleHelper.setObjectDisconnect((JSONObject) null);
                this.mBleHelper.disconnect();
//                this.syncTimeOut();
//
//                boolean round=true;
//                while (round){
//                    result=mBleHelper.getObjectDisconnect();
//                    if (result != null||!this.isrunning){
//                        round=false;
//                        break;
//                    }
//                }
//                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }
//            return result;
        }
    }

    public JSONObject setDateTime(Calendar cal,int hour_begin, int hour_end) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setDateTime((JSONObject) null);
                this.mBleHelper.set_date_time(cal, hour_begin, hour_end);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getDateTime();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }
            return result;
        }
    }

    public JSONObject setCamera(int type) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectCamera((JSONObject) null);
                this.mBleHelper.write_camera(type);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getObjectCamera();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }
            return result;
        }
    }

    public JSONObject setAlarm(int alarm_hour_1, int alarm_minute_1, int alarm_repeat_1, int alarm_hour_2, int alarm_minute_2, int alarm_repeat_2, int alarm_hour_3, int alarm_minute_3, int alarm_repeat_3) throws NoConnectException {

        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setAlarm((JSONObject) null);
                this.mBleHelper.set_alarm(this.mContext, alarm_hour_1, alarm_minute_1, alarm_repeat_1, alarm_hour_2, alarm_minute_2, alarm_repeat_2, alarm_hour_3, alarm_minute_3, alarm_repeat_3);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getAlarm();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }
            return result;
        }
    }



    public JSONObject setDayMode(int hour_begin, int hour_end) throws NoConnectException {

        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setDateMode((JSONObject) null);
                this.mBleHelper.set_day_mode(this.mContext, hour_begin, hour_end);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getDateMode();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }
            return result;
        }
    }

    public JSONObject setProfile(double height, double weight, int gender ,int age) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setProfile((JSONObject) null);
                this.mBleHelper.set_profile(this.mContext, height, weight, gender, age);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getProfile();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject setTarget(int goalStep) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setGoal((JSONObject) null);
                this.mBleHelper.set_target(this.mContext, goalStep);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getGoal();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }

                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject writeCallReminder() throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setCall((JSONObject) null);
                this.mBleHelper.write_call_reminder(this.mContext);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getObjectResult();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }

                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject setFinishSync() throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setFinishSync((JSONObject) null);
                this.mBleHelper.finish_sync();
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getFinishSync();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject setBoundStateNoConfirm() throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setBoundSetNoConfirm((JSONObject) null);
                this.mBleHelper.set_bound_state_no_confirm();
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getBoundSetNoConfirm();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject setBoundState(int type) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setBoundSet((JSONObject) null);
                this.mBleHelper.set_bound_state(type);
                this.syncTimeOutBound();

                boolean round=true;
                while (round){
                    result=mBleHelper.getBoundSet();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out_bound);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject getBoundInfo() throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setBoundInfo((JSONObject) null);
                this.mBleHelper.get_bound_state();
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getBoundInfo();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject getBattery() throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setBattery((JSONObject) null);
                this.mBleHelper.get_battery_level();
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getBattery();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject getVersion() throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setVersion((JSONObject) null);
                this.mBleHelper.get_version_code();
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getVersion();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject getActivityCount() throws NoConnectException {
        JSONObject activityCount = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setActivitCount(null);
                this.mBleHelper.get_activity_count(this.mContext);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    activityCount=mBleHelper.getActivityCount();
                    if (activityCount != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return activityCount;
        }
    }

    public JSONObject getActivityBySn(int sn, int hour) throws NoConnectException {
        JSONObject activityDataList = new JSONObject();
        try {
            synchronized (lock) {
                if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                    this.mBleHelper.setActivityData((JSONObject) null);
                    this.mBleHelper.get_activity_by_sn(this.mContext, sn, hour);

                    this.syncTimeOut();

                    boolean round=true;
                    while (round){
                        activityDataList=mBleHelper.getActivityData();
                        if (activityDataList != null||!this.isrunning){
                            round=false;
                            break;
                        }
                    }
                    this.cancelTimer(this.time_out);
                } else {
                    throwsNoConnectException();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return activityDataList;
    }

    public JSONObject getSleepBySn(int sn, int hour) throws NoConnectException {
        JSONObject sleepDataList = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setSleepData((JSONObject) null);
                this.mBleHelper.get_sleep_by_sn(this.mContext, sn, hour);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    sleepDataList=mBleHelper.getSleepData();
                    if (sleepDataList != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return sleepDataList;
        }
    }

    public JSONObject getID() throws NoConnectException {
        JSONObject objectResult = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectId((JSONObject) null);
                this.mBleHelper.get_id();
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectId();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    public JSONObject setTouchVibration(boolean isOpen, int time) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectTouchVibration((JSONObject) null);
                this.mBleHelper.setTouchVibration(isOpen, time);
                this.syncTimeOut();
                boolean round=true;
                while (round){
                    result=mBleHelper.getObjectTouchVibration();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject setUnit(int unitType) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectUnit((JSONObject) null);
                this.mBleHelper.set_unit(unitType);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getObjectUnit();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject setTimeForm(int timeType) throws NoConnectException {
        JSONObject result = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectTimeForm((JSONObject) null);
                this.mBleHelper.set_time_format(timeType);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    result=mBleHelper.getObjectTimeForm();
                    if (result != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return result;
        }
    }

    public JSONObject write_blood_pressure(int SBP,int DBP) throws NoConnectException{
        JSONObject objectResult= new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectResult((JSONObject) null);
                this.mBleHelper.set_blood_pressure_calibration(SBP, DBP);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectResult();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    public JSONObject write_sms_reminder_old(String number) throws NoConnectException{
        JSONObject objectResult= new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectResult((JSONObject) null);
                this.mBleHelper.write_sms_reminder_old(mContext, number);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectResult();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    public JSONObject write_message_notify(String content,int type,int id) throws NoConnectException{
        JSONObject objectResult= new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectResult((JSONObject) null);
                Notification notification = new Notification(mBleHelper, type, content);
                notification.sendNotifi(id);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectResult();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    public JSONObject writeHeart(byte heartType) throws NoConnectException {
        JSONObject objectResult = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectHeart((JSONObject) null);
                this.mBleHelper.set_heart(heartType);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectHeart();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
//                if (heartType == 2) {
//                    objectResult = this.mBleHelper.getObjectHeart();
//                }
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    public JSONObject writeHeartWarn(boolean isOpen,int high,int low,boolean isAlwaysLight) throws NoConnectException {
        JSONObject objectResult = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectResult((JSONObject) null);
                this.mBleHelper.set_heartWarn(isOpen, high, low, isAlwaysLight);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectResult();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
//                if (heartType == 2) {
//                    objectResult = this.mBleHelper.getObjectHeart();
//                }
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    public JSONObject setVibrationSettings(int type ,byte[] vibration) throws NoConnectException {
        JSONObject objectResult = new JSONObject();
        synchronized (lock) {
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2) {
                this.mBleHelper.setObjectResult((JSONObject) null);
                this.mBleHelper.setVibrationSettings(type,vibration);
                this.syncTimeOut();

                boolean round=true;
                while (round){
                    objectResult=mBleHelper.getObjectResult();
                    if (objectResult != null||!this.isrunning){
                        round=false;
                        break;
                    }
                }
                this.cancelTimer(this.time_out);
//                if (heartType == 2) {
//                    objectResult = this.mBleHelper.getObjectHeart();
//                }
            } else {
                throwsNoConnectException();
            }

            return objectResult;
        }
    }

    private void syncTimeOut() {
        this.cancelTimer(this.time_out);
        this.isrunning = true;
        this.time_out = new Timer();
        this.time_out.schedule(new TimerTask() {
            public void run() {
                SDK.this.isrunning = false;
            }
        }, 2000L);
    }

    private void syncTimeOut(int seconds) {
        this.cancelTimer(this.time_out);
        this.isrunning = true;
        this.time_out = new Timer();
        this.time_out.schedule(new TimerTask() {
            public void run() {
                SDK.this.isrunning = false;
            }
        }, (long)(seconds * 1000));
    }

    private void syncTimeOutBound() {
        this.cancelTimer(this.time_out_bound);
        this.isrunning = true;
        this.time_out_bound = new Timer();
        this.time_out_bound.schedule(new TimerTask() {
            public void run() {
                SDK.this.isrunning = false;
            }
        }, 10000L);
    }

    private void cancelTimer(Timer timer) {
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

    }

    public static void throwsNoConnectException() throws NoConnectException {
        throw new NoConnectException("no connect, please connect to your band first");
    }

    public void scan(String names[]){
        this.mBleHelper.setScan_device_name(names);
        this.mBleHelper.scan(BleHelper.SCAN_START);
    }

    public void stopScan(){
        this.mBleHelper.scan(BleHelper.SCAN_CLOSE);
    }


    public void connect(String mac){
        this.mBleHelper.connect(mac, false);
    }

    public void openDescriptor(){
        this.mBleHelper.findGattService();
    }

    public void self_writeData(byte[] value) throws NoConnectException {
        synchronized (lock){
            if (this.mBleHelper != null && this.mBleHelper.getConnectionState() == 2){
                this.mBleHelper.self_writeData(value);
            } else {
                throwsNoConnectException();
            }
        }
    }




}
