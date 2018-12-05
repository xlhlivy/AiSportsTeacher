package com.yelai.wearable.sdk.model;

import android.bluetooth.BluetoothDevice;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/13.
 */
public abstract class SDKCallBack {

    /** 连接成功*/
    public static final int STATE_CONNECT_SUCCESS=0;
    /** 连接失败*/
    public static final int STATE_CONNECT_FAIL=1;
    /** 连接断开*/
    public static final int STATE_DISCONNECT=2;
    /**已有连接*/
    public static final int STATE_ALREADY_CONNECT=3;

    /**写入Descriptor成功*/
    public static final int STATUS_SUCCESS=0;

//    public static final int RESPONSE_TYPE_ACTIVITY_COUNT=111;      //活动记录
//    public static final int RESPONSE_TYPE_ACTIVITY_VALUE=112;      //步数
//    public static final int RESPONSE_TYPE_SLEEP_VALUE=113;         //睡眠
//    public static final int RESPONSE_TYPE_DATE_TIME=114;           //设置时间
//    public static final int RESPONSE_TYPE_DAY_MODE=115;            //设置白天模式时间
//    public static final int RESPONSE_TYPE_ALARM=116;               //闹钟
//    public static final int RESPONSE_TYPE_PROFILE=117;             //个人信息
//    public static final int RESPONSE_TYPE_TARGET=118;              //目标
//    public static final int RESPONSE_TYPE_VERSION=119;             //版本
//    public static final int RESPONSE_TYPE_DOUBLE_CLICK=120;        //双击
//    public static final int RESPONSE_TYPE_VIBRATION_TIME=121;      //震动时间
//    public static final int RESPONSE_TYPE_BATTERY=122;             //电池
//    public static final int RESPONSE_TYPE_FINISH_SYNC=123;         //结束同步
//    public static final int RESPONSE_TYPE_TOUCH_VIBRATION=124;     //触摸震动
//    public static final int RESPONSE_TYPE_UNIT=125;                //单位
//    public static final int RESPONSE_TYPE_TIME_FORMAT=126;         //时间格式
//    public static final int RESPONSE_TYPE_ID=127;                  //手环ID
//    public static final int RESPONSE_TYPE_HEART_OPEN=128;               //开启终端持续心率模式
//    public static final int RESPONSE_TYPE_HEART_CLOSE=137;               //关闭终端持续心率模式
//    public static final int RESPONSE_TYPE_HEART_GET=138;               //读取心率
//    public static final int RESPONSE_TYPE_BOUND=129;               //绑定
//    public static final int RESPONSE_TYPE_BLOOD_PRESSURE_CALIBRATION=130;      //血压校准
//    public static final int RESPONSE_TYPE_MESSAGE_NOTIFY=131;      //消息提醒
//    public static final int RESPONSE_TYPE_SMS=132;                 //短信
    public static final int RESPONSE_TYPE_BUTTON_SHORT_CLICK=133;                 //手表按键短按
    public static final int RESPONSE_TYPE_BUTTON_LONG_CLICK=134;                 //手表按键长按
    public static final int RESPONSE_TYPE_BRACELET_HEART_ON=135;                 //手环开启心率测量
    public static final int RESPONSE_TYPE_BRACELET_HEART_OFF=136;                 //手环关闭心率测量
//    public static final int RESPONSE_TYPE_CAMERA=139;                 //进入或退出遥控拍照
    public static final int RESPONSE_TYPE_BLOOD_PRESSURE=140;      //血压
    public static final int RESPONSE_TYPE_HEART_WARN=141;               //手环发送心率预警
    public static final int RESPONSE_TYPE_CURRENT_HOUR_ACTIVITY=142;               //手环发送当前步数



    /**
     * 当调用scan()方法后,扫描到蓝牙设备后回调此方法。
     * If you call scan() method ,this will callback reporting an LE device found.
     * @param device    该对象包含设备名称和MAC地址
     *                  It contains LE device‘s name and address
     * @param rssi      信号强度    signal intensity
     * @param scanRecord    蓝牙广播数据    Bluetooth broadcast data
     */
    public void onDeviceFound(FoundDevice device, int rssi, byte[] scanRecord){

    }

    /**
     * 当调用scan()方法后,扫描到蓝牙设备后回调此方法。本方法返回系统包装的蓝牙对象，根据项目需求选择回调。
     * If you call scan() method ,this will callback reporting an LE device found.BluetoothDevice contains more content.
     * @param device    系统包装的蓝牙对象
     *                  BluetoothDevice
     * @param rssi      信号强度    signal intensity
     * @param scanRecord    蓝牙广播数据    Bluetooth broadcast data
     */
    public void onDeviceFound(BluetoothDevice device, int rssi, byte[] scanRecord){

    }


    /**
     * 当调用connect()方法后，连接成功与否会回调此方法，连接断开时也会回调此方法。
     * When the call connect () method, the connection is successful or not, and the connection is broken,will call this method.
     * @param state     连接状态。{@link SDKCallBack#STATE_CONNECT_SUCCESS}
     *                  or {@link SDKCallBack#STATE_CONNECT_FAIL}
     *                  or {@link SDKCallBack#STATE_DISCONNECT}
     *                  or {@link SDKCallBack#STATE_ALREADY_CONNECT}
     */
    public void onConnectionStateChange(int state){

    }

    /**
     * 写入descriptor后回调此方法
     * Callback indicating the result of a descriptor write operation.
     * @param status    写入descriptor的结果
     *                  The result of the write operation
     *                  {@link SDKCallBack#STATUS_SUCCESS}
     */
    public void onDescriptorWrite(int status){

    }

    /**
     * 异步接收设备主动发送数据。
     * Asynchronous return Bluetooth data.
     * @param value     返回数据
     *                  response value
     * @param type      返回数据类型
     *                  response type
     *                  {@link SDKCallBack#RESPONSE_TYPE_BLOOD_PRESSURE}...
     */
    public void onSDKDeviceResponse(JSONObject value,int type){}

    /**
     * 所有发送数据通过本接口异步返回蓝牙数据,但是返回的数据是未包装的，如果同一时间发送多条并不能按照发送顺序返回。
     * All data sent through this interface is returned asynchronously to Bluetooth data, but the returned data is not packaged.
     * @param value     未包装的蓝牙返回数据，按照蓝牙协议自行解析
     *                  Unpackaged Bluetooth return data
     */
    public void onSelfDeviceResponse(byte[] value){}

    /**
     * 调用发现服务后的回调
     * Callback when you called findGattService()
     * @param status   发现结果
     *                 result
     */
    public void onBluetoothServiceDiscover(boolean status){}

    public class FoundDevice{
        private String mac;
        private String name;

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
