//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yelai.wearable.sdk.helper;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.yelai.wearable.sdk.global.Config;
import com.yelai.wearable.sdk.global.Global;
import com.yelai.wearable.sdk.model.SDKCallBack;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public  class BleHelper {
    private String TAG = "BleHelper";
    private Context mContext;
    private int deviceType;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private String mBluetoothDeviceAddress;
    private int mConnectionState = 0;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    public static final String ACTION_GATT_CONNECTED = "com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_CONNECTED_FAIL = "com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL";
    public static final String ACTION_GATT_DISCONNECTED = "com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_DEVICE_FOUND = "com.gzgamut.max.bluetooth.le.ACTION_DEVICE_FOUND";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.gzgamut.max.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String ACTION_DATA_AVAILABLE = "com.gzgamut.max.bluetooth.le.ACTION_DATA_AVAILABLE";
    public static final String ACTION_READ_REMOVE_RSSI = "com.gzgamut.max.bluetooth.le.READ_REMOVE_RSSI";
    public static final String ACTION_WRITE_DESCRIPTOR = "com.gzgamut.max.bluetooth.le.WRITE_DESCRIPTOR";
    public static final String ACTION_RECEIVE_DATA = "com.gzgamut.energi.bluetooth.le.ACTION_RECEIVE_DATA";
    public static final String ACTION_WRITE_DATE_TIME_SUCCESS = "ACTION_WRITE_DATE_TIME_SUCCESS";
    public static final String ACTION_GATT_RECEIVE_ACTIVITY_COUNT = "ACTION_GATT_RECEIVE_ACTIVITY_COUNT";
    public static final String ACTION_GATT_RECEIVE_ACTIVITY = "ACTION_GATT_RECEIVE_ACTIVITY";
    public static final String ACTION_GATT_RECEIVE_PRESS_KEY = "ACTION_GATT_RECEIVE_PRESS_KEY";
    public static final String ACTION_GATT_RECEIVE_BEACON_ZOON = "ACTION_GATT_RECEIVE_BEACON_ZOON";
    public static final String ACTION_GATT_RECEIVE_SCREEN = "ACTION_GATT_RECEIVE_SCREEN";
    public static final String KEY_RECEIVE_DATA = "com.gzgamut.max.le.UPADTEDATA";
    public static final String KEY_RSSI_VALUE = "KEY_RSSI_VALUE";
    private Map<String, Integer> deviceMap = new HashMap();
    private int deviceName = -1;
    private String macBounded = null;
    private JSONObject objectMac = null;
    private JSONObject objectZoon = null;
    private JSONObject objectCount = null;
    private JSONObject objectActivity = null;
    private JSONObject objectSleep = null;
    private JSONObject objectDateTime = null;
    private JSONObject objectDateMode = null;
    private JSONObject objectProfile = null;
    private JSONObject objectAlarm = null;
    private JSONObject objectGoal = null;
    private JSONObject objectCall = null;
    private JSONObject objectVersion = null;
    private JSONObject objectBattery = null;
    private JSONObject objectDoubleClick = null;
    private JSONObject objectVibrationTime = null;
    private JSONObject objectFinishSycn = null;
    private JSONObject objectBoundInfo = null;
    private JSONObject objectBoundSet = null;
    private JSONObject objectBoundSetNoConfirm = null;
    private JSONObject objectTouchVibration = null;
    private JSONObject objectUnit = null;
    private JSONObject objectTimeForm = null;
    private JSONObject objectId = null;
    private JSONObject objectCamera = null;
    private JSONObject objectHeart = null;
    private JSONObject objectDisconnect = null;

    private JSONObject objectResult=null;

//    private int sent_num,get_num;
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.e("connectStatue","status:"+status+"    newState:"+newState);
            if (newState==BluetoothGatt.STATE_CONNECTED){
                BleHelper.this.mConnectionState = 2;
                BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED");
                if (sdkCallBack!=null){
                    sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_SUCCESS);
                    return;
                }
                Log.d(BleHelper.this.TAG, "Attempting to start service discovery:" + BleHelper.this.mBluetoothGatt.discoverServices());
            }else if (newState == BluetoothGatt.STATE_DISCONNECTED){
                if (mConnectionState==STATE_CONNECTED){
                    BleHelper.this.mConnectionState = 0;
                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED");
                    JSONObject object = ParserHelper.parseDisconnectResult();
                    BleHelper.this.setObjectDisconnect(object);
                    BleHelper.this.close();
                    if (sdkCallBack!=null){
                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_DISCONNECT);
                    }
                }else {
                    BleHelper.this.mConnectionState = 0;
                    BleHelper.this.disconnect();
                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL");
                    if (sdkCallBack!=null){
                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_FAIL);
                    }
                }
//                if (status==BluetoothGatt.GATT_SUCCESS){
//                    BleHelper.this.mConnectionState = 0;
//                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED");
//                    JSONObject object = ParserHelper.parseDisconnectResult();
//                    BleHelper.this.setObjectDisconnect(object);
//                    BleHelper.this.close();
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_DISCONNECT);
//                    }
//                }else {
//                    BleHelper.this.mConnectionState = 0;
//                    BleHelper.this.disconnect();
//                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL");
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_FAIL);
//                    }
//                }
            }
//            if (status==BluetoothGatt.GATT_SUCCESS){
//                if (newState== BluetoothProfile.STATE_CONNECTED){
//                    BleHelper.this.mConnectionState = 2;
//                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED");
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_SUCCESS);
//                        return;
//                    }
//                    Log.d(BleHelper.this.TAG, "Attempting to start service discovery:" + BleHelper.this.mBluetoothGatt.discoverServices());
//                }else if (newState==BluetoothProfile.STATE_DISCONNECTED){
//                    BleHelper.this.mConnectionState = 0;
//                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED");
//                    JSONObject object = ParserHelper.parseDisconnectResult();
//                    BleHelper.this.setObjectDisconnect(object);
//                    BleHelper.this.close();
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_DISCONNECT);
//                    }
//                }
//            }else {
//                BleHelper.this.mConnectionState = 0;
//                BleHelper.this.disconnect();
//                BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL");
//                if (sdkCallBack!=null){
//                    sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_FAIL);
//                }
//            }
//            if(newState == 2) {
//                if(status == 0) {
//                    BleHelper.this.mConnectionState = 2;
//                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED");
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_SUCCESS);
//                        return;
//                    }
//                    Log.d(BleHelper.this.TAG, "Attempting to start service discovery:" + BleHelper.this.mBluetoothGatt.discoverServices());
//                } else {
//                    BleHelper.this.mConnectionState = 0;
//                    BleHelper.this.disconnect();
//                    BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_CONNECTED_FAIL");
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_FAIL);
//                    }
//                }
//            } else if(newState == 0) {
//                BleHelper.this.mConnectionState = 0;
//                BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_DISCONNECTED");
//                JSONObject object = ParserHelper.parseDisconnectResult();
//                BleHelper.this.setObjectDisconnect(object);
//                BleHelper.this.close();
//                if (sdkCallBack!=null){
//                    sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_DISCONNECT);
//                }
//            }
        }

    @SuppressLint("NewApi") @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if(status == 0) {
                BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
                BleHelper.this.set_notify_true();
            } else {
                Log.w(BleHelper.this.TAG, "onServicesDiscovered received: " + status);
            }

        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            byte[] value1 = characteristic.getValue();
//            Log.e(TAG,"send_times:"+(++sent_num)+"  first send byte:"+value1[0]);
            if(status == 0) {
                byte[] value = characteristic.getValue();
                int title = ParserHelper.getByteValue(value[0]);
                if(title == 1) {
                    Log.d(BleHelper.this.TAG, "set date time success");
                } else if(title == 2) {
                    Log.d(BleHelper.this.TAG, "set day mode success");
                } else if(title == 3) {
                    Log.d(BleHelper.this.TAG, "set alarm success");
                } else if(title == 11) {
                    Log.d(BleHelper.this.TAG, "set target success");
                } else if (title==0x57) {
                    Log.d(BleHelper.this.TAG, "set bloodPressure success");
                } else if (title==0x20) {
                    Log.d(BleHelper.this.TAG, "set message notify success");
                }else if(title != 13) {
                    if(title == 12) {
                        Log.d(BleHelper.this.TAG, "set body success");
                    } else if(title == 19) {
                        Log.d(BleHelper.this.TAG, "write activity success");
                    } else if(title == 16) {
                        Log.d(BleHelper.this.TAG, "write activity_count success");
                    } else if(title == 80) {
                        Log.d(BleHelper.this.TAG, "write camera success");
                    } else if(title == 112) {
                        Log.d(BleHelper.this.TAG, "write version success");
                    }
                }
            }

        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//            byte[] value1 = characteristic.getValue();
//            Log.e(TAG,"get_times:"+(++get_num)+"  first get byte:"+value1[0]);
            byte[] value = characteristic.getValue();
            int title = ParserHelper.getByteValue(value[0]);
            if (sdkCallBack!=null){
                sdkCallBack.onSelfDeviceResponse(value);
            }
            JSONObject title_1;
            if(title == 144) {
                title_1 = ParserHelper.parseActivityCount(value);
                BleHelper.this.setActivitCount(title_1);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_ACTIVITY_COUNT);
//                }
                Log.d("SDK", "*** get activity count success ***");
            } else if(title == 147) {
                title_1 = ParserHelper.parseActivity(value, BleHelper.this.deviceType);
                BleHelper.this.setActivityData(title_1);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_ACTIVITY_VALUE);
//                }
                Log.d("SDK", "*** get activity value success ***");
            }  else if (title==0x9e){
                title_1 = ParserHelper.parseCurrentHourActivity(value);
                if (sdkCallBack!=null){
                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_CURRENT_HOUR_ACTIVITY);
                }
            }else if(title == 145) {
                title_1 = ParserHelper.parseSleep(value);
                BleHelper.this.setSleepData(title_1);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_SLEEP_VALUE);
//                }
                Log.d("SDK", "*** get sleep value success ***");
            } else if(title == 129) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_DATE_TIME);
//                }
                BleHelper.this.setDateTime(title_1);

                Log.d("SDK", "*** set date time success ***");
            } else if(title == 130) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_DAY_MODE);
//                }
                BleHelper.this.setDateMode(title_1);

                Log.d("SDK", "*** set day mode success ***");
            } else if(title == 131) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_ALARM);
//                }
                BleHelper.this.setAlarm(title_1);

                Log.d("SDK", "*** set alarm success ***");
            } else if(title == 140) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_PROFILE);
//                }
                BleHelper.this.setProfile(title_1);

                Log.d("SDK", "*** set profile success ***");
            } else if(title == 139) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_TARGET);
//                }
                BleHelper.this.setGoal(title_1);

                Log.d("SDK", "*** set target success ***");
            }
//            else if(title == 160) {
//                title_1 = ParserHelper.parseResult(value);
//                BleHelper.this.setCall(title_1);
//                Log.d("SDK", "*** write call reminder success ***");
//            }
            else if(title == 240) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_VERSION);
//                }
                BleHelper.this.setVersion(title_1);

                Log.d("SDK", "*** get version success ***");
            } else if(title == 135) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_DOUBLE_CLICK);
//                }
                BleHelper.this.setDoubleClick(title_1);

                Log.d("SDK", "*** set double click success ***");
            } else if(title == 138) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_VIBRATION_TIME);
//                }
                BleHelper.this.setVibrationTime(title_1);

                Log.d("SDK", "*** set vibration time success ***");
            } else if(title == 176) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_BATTERY);
//                }
                BleHelper.this.setBattery(title_1);

                Log.d("SDK", "*** set battery success ***");
            } else if(title == 241) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_FINISH_SYNC);
//                }
                BleHelper.this.setFinishSync(title_1);

                Log.d("SDK", "*** set finish sync success ***");
            } else if(title == 212) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_TOUCH_VIBRATION);
//                }
                BleHelper.this.setObjectTouchVibration(title_1);

                Log.d("SDK", "*** set touch vibration success ***");
            } else if(title == 213) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_UNIT);
//                }
                BleHelper.this.setObjectUnit(title_1);

                Log.d("SDK", "*** set unit success ***");
            } else if(title == 141) {
                title_1 = ParserHelper.parseResult(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_TIME_FORMAT);
//                }
                BleHelper.this.setObjectTimeForm(title_1);

                Log.d("SDK", "*** set time format success ***");
            } else if(title == 193) {
                title_1 = ParserHelper.parseZoon(value);
//                if (sdkCallBack!=null){
//                    sdkCallBack.onSDKDeviceResponse(title_1,SDKCallBack.RESPONSE_TYPE_ID);
//                }
                BleHelper.this.setObjectId(title_1);

                Log.d("SDK", "*** get id success ***");
            } else if(title == 208) {
                byte secondValue=value[1];
                if (secondValue==0x01){
                    int result=ParserHelper.getByteValue(value[2]);
                    JSONObject object = new JSONObject();
                    try {
                        object.put("result", result);
//                        if (sdkCallBack!=null){
//                            sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_CAMERA);
//                        }
                        setObjectCamera(object);

                        Log.d("SDK", "*** get camera result ***");
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if(title == 214) {
                byte secondValue=value[1];
                if (secondValue==0x05){
                    int result=ParserHelper.getByteValue(value[2]);
                    if (sdkCallBack!=null){
                        if (result==0){
                            sdkCallBack.onSDKDeviceResponse(null,SDKCallBack.RESPONSE_TYPE_BRACELET_HEART_OFF);
                            return;
                        }else if (result==1) {
                            sdkCallBack.onSDKDeviceResponse(null,SDKCallBack.RESPONSE_TYPE_BRACELET_HEART_ON);
                            return;
                        }
                    }
                }else if (secondValue==0x04){
                    title_1 = ParserHelper.parseHeart(value);
                    BleHelper.this.setObjectResult(title_1);
                }else if (secondValue==0x06){
                    title_1 = ParserHelper.parseHeart(value);
                    if (sdkCallBack!=null){
                        sdkCallBack.onSDKDeviceResponse(title_1, SDKCallBack.RESPONSE_TYPE_HEART_WARN);
                    }
//                    BleHelper.this.setObjectHeart(title_1);
                }else if (secondValue==0x00){
                    title_1 = ParserHelper.parseHeart(value);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(title_1, SDKCallBack.RESPONSE_TYPE_HEART_OPEN);
//                    }
                    BleHelper.this.setObjectHeart(title_1);

                }else if (secondValue==0x01){
                    title_1 = ParserHelper.parseHeart(value);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(title_1, SDKCallBack.RESPONSE_TYPE_HEART_CLOSE);
//                    }
                    BleHelper.this.setObjectHeart(title_1);

                }else if (secondValue==0x02){
                    title_1 = ParserHelper.parseHeart(value);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(title_1, SDKCallBack.RESPONSE_TYPE_HEART_GET);
//                    }
                    BleHelper.this.setObjectHeart(title_1);

                }
                Log.d("SDK", "*** get heart success ***");
            } else if(title == 143) {
                int title_11 = ParserHelper.getByteValue(value[1]);
                int result = ParserHelper.getByteValue(value[2]);
                JSONObject object = new JSONObject();

                try {
                    if(title_11 == 0) {
                        object.put("result", result);
//                        if (sdkCallBack!=null){
//                            sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_BOUND);
//                        }
                        BleHelper.this.setBoundInfo(object);
                        Log.d("SDK", "*** get bound info success ***");
                    } else if(title_11 == 1) {
                        if(result == 0) {
                            object.put("result", result);
//                            if (sdkCallBack!=null){
//                                sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_BOUND);
//                            }
                            BleHelper.this.setBoundSet(object);
                            Log.d("SDK", "*** set bound state success ***");
                        }
                    } else if(title_11 == 2) {
                        object.put("result", result);
//                        if (sdkCallBack!=null){
//                            sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_BOUND);
//                        }
                        BleHelper.this.setBoundSetNoConfirm(object);
                        Log.d("SDK", "*** set bound state no confirm success ***");
                    }

                } catch (JSONException var9) {
                    var9.printStackTrace();
                }
            }else if (title==0xAD){
                int result=ParserHelper.getByteValue(value[3]);
                JSONObject object = new JSONObject();
                try {
                    object.put("result", result);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_BLOOD_PRESSURE_CALIBRATION);
//                    }
                    setObjectResult(object);

                    Log.d("SDK", "*** get bloodPressureCalibration result ***");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (title==0xD7){
                int result=ParserHelper.getByteValue(value[2]);     //0为成功其余失败
                JSONObject object = new JSONObject();
                try {
                    object.put("result", result);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_BLOOD_PRESSURE_CALIBRATION);
//                    }
                    setObjectResult(object);

                    Log.d("SDK", "*** get bloodPressureCalibration result ***");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(title==0xD8){
                int sbp=ParserHelper.getByteValue(value[1]);
                int dbp=ParserHelper.getByteValue(value[2]);
                JSONObject object = new JSONObject();
                try {
                    object.put("SBP", sbp);
                    object.put("DBP", dbp);
                    if (sdkCallBack!=null){
                        sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_BLOOD_PRESSURE);
                    }
                    Log.d("SDK", "*** get bloodPressure result ***");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else if (title==0xA0){
                int result=ParserHelper.getByteValue(value[2]);     //0为成功其余失败
                JSONObject object = new JSONObject();
                try {
                    object.put("result", result);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_MESSAGE_NOTIFY);
//                    }
                    setObjectResult(object);

                    Log.d("SDK", "*** get message notify result ***");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (title==0xA1){
                int result=ParserHelper.getByteValue(value[1]);     //0为成功其余失败
                JSONObject object = new JSONObject();
                try {
                    object.put("result", result);
//                    if (sdkCallBack!=null){
//                        sdkCallBack.onSDKDeviceResponse(object,SDKCallBack.RESPONSE_TYPE_SMS);
//                    }
                    setObjectResult(object);

                    Log.d("SDK", "*** get SMS result ***");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (title==0xD1){
                if (value[2]==0x01){
                    if (sdkCallBack!=null){
                        sdkCallBack.onSDKDeviceResponse(null,SDKCallBack.RESPONSE_TYPE_BUTTON_SHORT_CLICK);
                    }
                }else if (value[2]==0x11){
                    if (sdkCallBack!=null){
                        sdkCallBack.onSDKDeviceResponse(null,SDKCallBack.RESPONSE_TYPE_BUTTON_LONG_CLICK);
                    }
                }
            }

        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (sdkCallBack!=null){
                sdkCallBack.onDescriptorWrite(status);
            }
            if(status == 0) {
                BleHelper.this.broadcastUpdate("com.gzgamut.max.bluetooth.le.WRITE_DESCRIPTOR");
                JSONObject object = new JSONObject();

                try {
                    object.put("mac", BleHelper.this.mBluetoothDeviceAddress);
                    BleHelper.this.setMac(object);
                } catch (JSONException var6) {
                    var6.printStackTrace();
                }
//                startMessageTimer();
            }

        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Log.d(BleHelper.this.TAG, "rssi value: " + rssi);
            BleHelper.this.broadcastRSSI(rssi);
        }
    };

    public void findGattService(){
        BleHelper.this.mBluetoothGatt.discoverServices();
    }

    private LeScanCallback mLeScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if(device != null) {
                String mac;
                if(BleHelper.this.macBounded == null) {
                    mac = device.getName();
                    String mac1 = device.getAddress();
                    if(mac != null && mac.startsWith(Config.DEVICE_NAME[BleHelper.this.deviceName]) && mac1 != null) {
                        BleHelper.this.deviceMap.put(mac1, Integer.valueOf(rssi));
                    }
                } else {
                    mac = device.getAddress();
                    if(mac != null && mac.startsWith(BleHelper.this.macBounded)) {
                        BleHelper.this.scan(false);
                        BleHelper.this.connect(mac, false);
                    }
                }
            }
        }
    };


    private SDKCallBack sdkCallBack;

    private BleHelper(Context context, int deviceType,SDKCallBack sdkCallBack) {
        this.mContext = context;
        this.deviceType = deviceType;
        this.sdkCallBack=sdkCallBack;
        this.initialize();
    }

    public JSONObject getObjectResult() {
        if (objectResult!=null) {
            Log.w("CHECK", "*** get getObjectResult ***"+objectResult);
        }
        return objectResult;
    }

    public void setObjectResult(JSONObject objectResult) {
        this.objectResult = objectResult;
    }

    public String getDeviceAddress() {
        return this.mBluetoothDeviceAddress;
    }

    public int getConnectionState() {
        return this.mConnectionState;
    }

    public void setZoon(JSONObject object) {
        this.objectZoon = object;
    }

    public JSONObject getZoon() {
        if (objectZoon!=null) {
            Log.w("CHECK", "*** get getZoon ***"+objectZoon);
        }
        return this.objectZoon;
    }

    public void setActivitCount(JSONObject object) {
        this.objectCount = object;
    }

    public JSONObject getActivityCount() {
        if (objectCount!=null) {
            Log.w("CHECK", "*** get getActivityCount ***"+objectCount);
        }
        return this.objectCount;
    }

    public void setActivityData(JSONObject object) {
        this.objectActivity = object;
    }

    public JSONObject getActivityData() {
        if (objectActivity!=null) {
            Log.w("CHECK", "*** get getActivityData ***"+objectActivity);
        }
        return this.objectActivity;
    }

    public void setSleepData(JSONObject object) {
        this.objectSleep = object;
    }

    public JSONObject getSleepData() {
        if (objectSleep!=null) {
            Log.w("CHECK", "*** get getSleepData ***"+objectSleep);
        }
        return this.objectSleep;
    }

    public void setDateTime(JSONObject object) {
        this.objectDateTime = object;
    }

    public JSONObject getDateTime() {
        if (objectDateTime!=null) {
            Log.w("CHECK", "*** get getDateTime ***"+objectDateTime);
        }
        return this.objectDateTime;
    }

    public void setDateMode(JSONObject object) {
        this.objectDateMode = object;
    }

    public JSONObject getDateMode() {
        if (objectDateMode!=null) {
            Log.w("CHECK", "*** get getDateMode ***"+objectDateMode);
        }
        return this.objectDateMode;
    }

    public void setProfile(JSONObject object) {
        this.objectProfile = object;
    }

    public JSONObject getProfile() {
        if (objectProfile!=null) {
            Log.w("CHECK", "*** get getProfile ***"+objectProfile);
        }
        return this.objectProfile;
    }

    public void setAlarm(JSONObject object) {
        this.objectAlarm = object;
    }

    public JSONObject getAlarm() {
        if (objectAlarm!=null) {
            Log.w("CHECK", "*** get getAlarm ***"+objectAlarm);
        }
        return this.objectAlarm;
    }

    public void setGoal(JSONObject object) {
        this.objectGoal = object;
    }

    public JSONObject getGoal() {
        if (objectGoal!=null) {
            Log.w("CHECK", "*** get getGoal ***"+objectGoal);
        }
        return this.objectGoal;
    }

    public void setCall(JSONObject object) {
        this.objectCall = object;
    }

    public JSONObject getCall() {
        if (objectCall!=null) {
            Log.w("CHECK", "*** get getCall ***"+objectCall);
        }
        return this.objectCall;
    }

    public void setVersion(JSONObject object) {
        this.objectVersion = object;
    }

    public JSONObject getVersion() {
        if (objectVersion!=null) {
            Log.w("CHECK", "*** get getVersion ***"+objectVersion);
        }
        return this.objectVersion;
    }

    public void setBattery(JSONObject object) {
        this.objectBattery = object;
    }

    public JSONObject getBattery() {
        if (objectBattery!=null) {
            Log.w("CHECK", "*** get getBattery ***"+objectBattery);
        }
        return this.objectBattery;
    }

    public void setDoubleClick(JSONObject object) {
        this.objectDoubleClick = object;
    }

    public JSONObject getDoubleClick() {
        if (objectDoubleClick!=null) {
            Log.w("CHECK", "*** get getDoubleClick ***"+objectDoubleClick);
        }
        return this.objectDoubleClick;
    }

    public void setVibrationTime(JSONObject object) {
        this.objectVibrationTime = object;
    }

    public JSONObject getVibrationTime() {
        if (objectVibrationTime!=null) {
            Log.w("CHECK", "*** get getVibrationTime ***"+objectVibrationTime);
        }
        return this.objectVibrationTime;
    }

    public void setFinishSync(JSONObject object) {
        this.objectFinishSycn = object;
    }

    public JSONObject getFinishSync() {
        if (objectFinishSycn!=null) {
            Log.w("CHECK", "*** get getFinishSync ***"+objectFinishSycn);
        }
        return this.objectFinishSycn;
    }

    public void setBoundInfo(JSONObject object) {
        this.objectBoundInfo = object;
    }

    public JSONObject getBoundInfo() {
        if (objectBoundInfo!=null) {
            Log.w("CHECK", "*** get getBoundInfo ***"+objectBoundInfo);
        }
        return this.objectBoundInfo;
    }

    public void setBoundSet(JSONObject object) {
        this.objectBoundSet = object;
    }

    public JSONObject getBoundSet() {
        if (objectBoundSet!=null) {
            Log.w("CHECK", "*** get getBoundSet ***"+objectBoundSet);
        }
        return this.objectBoundSet;
    }

    public void setBoundSetNoConfirm(JSONObject object) {
        this.objectBoundSetNoConfirm = object;
    }

    public JSONObject getBoundSetNoConfirm() {
        if (objectBoundSetNoConfirm!=null) {
            Log.w("CHECK", "*** get getBoundSetNoConfirm ***"+objectBoundSetNoConfirm);
        }
        return this.objectBoundSetNoConfirm;
    }

    public void setDeviceName(int deviceName) {
        this.deviceName = deviceName;
    }

    public void setMac(JSONObject object) {
        this.objectMac = object;
    }

    public JSONObject getMac() {
        return this.objectMac;
    }

    public void setMacBounded(String mac) {
        this.macBounded = mac;
    }

    public Map<String, Integer> getDeviceMap() {
        return this.deviceMap;
    }

    public void clearDeviceMap() {
        if(this.deviceMap != null) {
            this.deviceMap.clear();
        }

    }

    public JSONObject getObjectTouchVibration() {
        if (objectTouchVibration!=null) {
            Log.w("CHECK", "*** get getObjectTouchVibration ***"+objectTouchVibration);
        }
        return this.objectTouchVibration;
    }

    public void setObjectTouchVibration(JSONObject objectTouchVibration) {
        this.objectTouchVibration = objectTouchVibration;
    }

    public JSONObject getObjectUnit() {
        if (objectUnit!=null) {
            Log.w("CHECK", "*** get getObjectUnit ***"+objectUnit);
        }
        return this.objectUnit;
    }

    public void setObjectUnit(JSONObject objectUnit) {
        this.objectUnit = objectUnit;
    }

    public JSONObject getObjectTimeForm() {
        if (objectTimeForm!=null) {
            Log.w("CHECK", "*** get getObjectTimeForm ***"+objectTimeForm);
        }
        return this.objectTimeForm;
    }

    public void setObjectTimeForm(JSONObject objectTimeForm) {
        this.objectTimeForm = objectTimeForm;
    }

    public JSONObject getObjectId() {
        if (objectId!=null) {
            Log.w("CHECK", "*** get getObjectId ***"+objectId);
        }
        return this.objectId;
    }

    public void setObjectId(JSONObject objectId) {
        this.objectId = objectId;
    }

    public JSONObject getObjectCamera() {
        if (objectCamera!=null) {
            Log.w("CHECK", "*** get getObjectCamera ***"+objectCamera);
        }
        return this.objectCamera;
    }

    public void setObjectCamera(JSONObject objectCamera) {
        this.objectCamera = objectCamera;
    }

    public JSONObject getObjectHeart() {
        if (objectHeart!=null) {
            Log.w("CHECK", "*** get getObjectHeart ***"+objectHeart);
        }
        return this.objectHeart;
    }

    public void setObjectHeart(JSONObject objectHeart) {
        this.objectHeart = objectHeart;
    }

    public JSONObject getObjectDisconnect() {
        if (objectDisconnect!=null) {
            Log.w("CHECK", "*** get getObjectDisconnect ***"+objectDisconnect);
        }
        return this.objectDisconnect;
    }

    public void setObjectDisconnect(JSONObject objectDisconnect) {
        this.objectDisconnect = objectDisconnect;
    }

    public void connectDevice() {
        String nearestDeviceAddress = ParserHelper.getNearestDeviceAddress(this.deviceMap);
        if(nearestDeviceAddress != null) {
            this.scan(false);
            this.connect(nearestDeviceAddress, false);
        }

        this.deviceMap.clear();
    }

    public void set_date_time( Calendar calendar,int hour_begin, int hour_end) {
        byte[] value = ParserHelper.getDateTimeValue(calendar,hour_begin,hour_end);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void set_target(Context context, int goalStep) {
        if (!(goalStep>0&&goalStep<=60000)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value = ParserHelper.getGoalValue(context, goalStep);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void set_day_mode(Context context, int hour_begin, int hour_end) {
        byte[] value = ParserHelper.getDayModeValue(context, hour_begin, hour_end);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void set_alarm(Context context, int alarm_hour_1, int alarm_minute_1, int alarm_repeat_1, int alarm_hour_2, int alarm_minute_2, int alarm_repeat_2, int alarm_hour_3, int alarm_minute_3, int alarm_repeat_3) {
        if (judge_hour(alarm_hour_1)||judge_hour(alarm_hour_2)||judge_hour(alarm_hour_3)||judge_minute(alarm_minute_1)
                ||judge_minute(alarm_minute_2)||judge_minute(alarm_minute_3)||judge_repeat(alarm_repeat_1)||judge_repeat(alarm_repeat_2)
                ||judge_repeat(alarm_repeat_3)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value = ParserHelper.getAlarmValue(context, alarm_hour_1, alarm_minute_1, alarm_repeat_1, alarm_hour_2, alarm_minute_2, alarm_repeat_2, alarm_hour_3, alarm_minute_3, alarm_repeat_3);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    private boolean judge_hour(int hour){
        if (hour>=0&&hour<=23){
            return false;
        }else {
            return true;
        }
    }

    private boolean judge_minute(int minute){
        if (minute>=0&&minute<=59){
            return false;
        }else {
            return true;
        }
    }

    private boolean judge_repeat(int repeat){
        if (repeat>=0&&repeat<=0x7f){
            return false;
        }else {
            return true;
        }
    }

    /**
     * nordic+玉成血压版可用
     * @param SBP
     * @param DBP
     */
    public void set_blood_pressure_calibration(int SBP,int DBP){
        if (!(SBP>0&&SBP<=255)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        if (!(DBP>0&&DBP<=255)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value=ParserHelper.getBloodPressureValue(SBP,DBP);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }


    /**
     * nordic带字库可用
     * @param value
     */
    public void set_message_notify(byte[] value){
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

//    public void set_language(Context context, int languageType) {
//        byte[] value = ParserHelper.getLanguageValue(context, languageType);
//        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
//    }

    public void set_profile(Context context, double height, double weight, int gender,int age) {
        if (!(gender==0||gender==1)){
            throw new IllegalArgumentException("gender anomaly.");
        }
        if (!(height>=60&&height<=250)){
            throw new IllegalArgumentException("height anomaly.");
        }
        if (!(weight>=10&&weight<=250)){
            throw new IllegalArgumentException("weight anomaly.");
        }

        if (!(age>=5&&age<=120)){
            throw new IllegalArgumentException("age anomaly.");
        }
        byte[] value = ParserHelper.getProfileValue(context, height, weight, gender);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void write_camera(int type) {
        if (!(type==Global.TYPE_CAMERA_START||type==Global.TYPE_CAMERA_END)){
            throw new IllegalArgumentException("type anomaly.");
        }
        byte[] value = ParserHelper.getCameraValue(type);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public  void get_activity_count(Context context) {
        byte[] value = ParserHelper.getActivityCountValue(context);
//        addTask(value);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

//    private synchronized void addTask(byte[] value){
//        if (messageLists.size()>0) {
//            messageLists.add(value);
//        }else {
//            messageLists.add(value);
//            startMessageTimer();
//        }
//    }

    public void get_activity_by_sn(Context context, int sn, int hour) {
        byte[] value = ParserHelper.getActivityValue(context, sn, hour);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void get_sleep_by_sn(Context context, int sn, int hour) {
        byte[] value = ParserHelper.getSleepValue(sn, hour);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void write_call_reminder(Context context) {
        byte[] value = ParserHelper.getCallValue(context);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void write_call_termination(Context context) {
        byte[] value = ParserHelper.getCallTerminationValue(context);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void write_sms_reminder_old(Context context, String number) {
        byte[] value = ParserHelper.getSMSValue(context, number);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }
    }

    public void get_id() {
        byte[] value = ParserHelper.getZoonValue();
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void set_lost_mode(Context context, int type) {
        byte[] value = ParserHelper.getLostModeValue(context, type);
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
    }

    public void set_bound_state(int type) {
        byte[] value = ParserHelper.getBoundValue(type);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void set_bound_state_no_confirm() {
        byte[] value = new byte[20];
        value[0] = 15;
        value[1] = 2;
        value[2] = 1;
        this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);


    }

    public void get_bound_state() {
        byte[] value = ParserHelper.getBoundValue();
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void get_battery_level() {
        byte[] value = ParserHelper.getBatteryValue();
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public  void get_version_code() {
        byte[] value = ParserHelper.getVersionValue();
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
//            addTask(value);
        }

    }

    public void finish_sync() {
        byte[] value = new byte[20];
        value[0] = 113;
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void setTouchVibration(boolean isTouchVibration, int time) {
        if (!(time>0&&time<=255)){
            throw new IllegalArgumentException("time out of range.");
        }
        byte[] value = ParserHelper.getTouchVibrationvalue(isTouchVibration, time);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void set_unit(int type) {
        if (!(type== Global.TYPE_UNIT_METRIC||type==Global.TYPE_UNIT_IMPERIAL)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value = ParserHelper.getUnitValue(type);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void set_time_format(int timeFormType) {
        if (!(timeFormType== Global.TYPE_TIME_FORM_12||timeFormType==Global.TYPE_TIME_FORM_24)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value = ParserHelper.getTimeFormatValue(timeFormType);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void set_heart(byte type) {
        if (!(type== Global.TYPE_HEART_START||type==Global.TYPE_HEART_STOP||type==Global.TYPE_HEART_GET)){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value = ParserHelper.getHeartValue(type);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void set_heartWarn(boolean isOpen,int high,int low,boolean isAlwaysLight) {
        byte[] value = ParserHelper.getHeartWarnValue(isOpen, high, low, isAlwaysLight);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void setVibrationSettings(int type ,byte[] vibration) {
        if (vibration.length!=15){
            throw new IllegalArgumentException("Parameter anomaly.");
        }
        byte[] value = ParserHelper.getVibrationSettings(type,vibration);
        if(value != null) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }

    }

    public void set_notify_true() {
        this.setCharactoristicNotifyAndWriteDescriptor(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_NOTI, Config.UUID_DESCRIPTOR_CONFIGURATION);
    }

    private void broadcastUpdate(String action) {
        Intent intent = new Intent(action);
        this.mContext.sendBroadcast(intent);
    }

    private void broadcastRSSI(int rssi) {
        Intent intent = new Intent("com.gzgamut.max.bluetooth.le.READ_REMOVE_RSSI");
        intent.putExtra("KEY_RSSI_VALUE", rssi);
        this.mContext.sendBroadcast(intent);
    }

    public boolean initialize() {
        if(this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager)this.mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            if(this.mBluetoothManager == null) {
                Log.e(this.TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if(this.mBluetoothAdapter == null) {
            Log.e(this.TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        } else {
            return true;
        }
    }

    public boolean connect(String address, boolean is) {
        if (address==null){
            throw new IllegalArgumentException("Mac can not be null.");
        }
        if (address.split(":").length!=6){
            throw new IllegalArgumentException("Mac format error.");
        }
        if(this.mBluetoothAdapter != null && address != null) {
            if (getConnectionState()==STATE_CONNECTED&&sdkCallBack!=null){
                sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_ALREADY_CONNECT);
                return true;
            }
            BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
            if(device == null) {
                Log.w(this.TAG, "Device not found.  Unable to connect.");
                return false;
            } else {
                this.mBluetoothGatt = device.connectGatt(this.mContext, false, this.mGattCallback);

                Log.d(this.TAG, "Trying to create a new connection.");
                this.mBluetoothDeviceAddress = address;
                this.mConnectionState = 1;
                return true;
            }
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
    }

    public void disconnect() {
        if(this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.disconnect();
//            cancleTimer(messageTimer);
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }

//    private void cancleTimer(Timer timer){
//        if (timer!=null){
//            timer.cancel();
//            timer = null;
//        }
//    }

    public void close() {
        if(this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        return this.mBluetoothGatt == null?null:this.mBluetoothGatt.getServices();
    }

    public boolean getRssiVal() {
        return this.mBluetoothGatt == null?false:this.mBluetoothGatt.readRemoteRssi();
    }

    public void wirteCharacteristic(BluetoothGattCharacteristic characteristic) {
        if(this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.writeCharacteristic(characteristic);
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }

    public synchronized void readCharacteristic(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_character) {
        BluetoothGattService mBluetoothGattService = this.getBluetoothGattService(mBluetoothGatt, uuid_service);
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = this.getBluetoothGattCharacteristic(mBluetoothGattService, uuid_character);
        if(mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            mBluetoothGatt.readCharacteristic(mBluetoothGattCharacteristic);
        } else if(mBluetoothGatt == null) {
            Log.d(this.TAG, "mBluetoothGatt is null");
        } else if(mBluetoothGattCharacteristic == null) {
            Log.d(this.TAG, "mBluetoothGattCharacteristic is null");
        }

    }



    public void  writeCharacteristic(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_character, byte[] value) {
        BluetoothGattService mBluetoothGattService = this.getBluetoothGattService(mBluetoothGatt, uuid_service);
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = this.getBluetoothGattCharacteristic(mBluetoothGattService, uuid_character);
        if(mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            mBluetoothGattCharacteristic.setValue(value);
            mBluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
//            messageLists.add(mBluetoothGattCharacteristic);
        } else if(mBluetoothGatt == null) {
            Log.d(this.TAG, "mBluetoothGatt is null");
        } else if(mBluetoothGattCharacteristic == null) {
            Log.d(this.TAG, "mBluetoothGattCharacteristic is null");
        }

    }

//    private Timer messageTimer;
//    private List<byte[]> messageLists=new ArrayList<byte[]>();
//    private int list_send_num;
//
//    private void startMessageTimer(){
//        if (messageTimer==null) {
//            messageTimer = new Timer();
//            messageTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if (getConnectionState()==STATE_CONNECTED) {
//                        if (mBluetoothGatt != null) {
//                            if (messageLists != null && messageLists.size() > 0) {
////							byte[] value=messageLists.get(0).getValue();
//                                writeCharacteristic(mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ,messageLists.get(0));
//                                Log.e(TAG,"队列发送次数:"+(++list_send_num));
//                                messageLists.remove(0);
//                            }else {
//                                cancleTimer(messageTimer);
//                            }
//                        }
//                    }
//                }
//            }, 0, 50);
//        }
//    }

    public void writeDescriptor(BluetoothGatt mBluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor) {
        if(this.mBluetoothAdapter != null && mBluetoothGatt != null) {
            mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }

    public void readCharacteristic(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic characteristic) {
        if(this.mBluetoothAdapter != null && mBluetoothGatt != null) {
            mBluetoothGatt.readCharacteristic(characteristic);
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if(this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }

    public void setCharactoristicNotifyAndWriteDescriptor(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_characteristic, UUID uuid_descriptor) {
        BluetoothGattService mBluetoothGattService = this.getBluetoothGattService(mBluetoothGatt, uuid_service);
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = this.getBluetoothGattCharacteristic(mBluetoothGattService, uuid_characteristic);
        if(mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic, true);
            BluetoothGattDescriptor bluetoothGattDescriptor = mBluetoothGattCharacteristic.getDescriptor(uuid_descriptor);
            if(bluetoothGattDescriptor != null) {
                bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
            }
        } else if(mBluetoothGatt == null) {
            Log.d(this.TAG, "mBluetoothGatt is null");
        } else if(mBluetoothGattCharacteristic == null) {
            Log.d(this.TAG, "mBluetoothGattCharacteristic is null");
        }

    }

    public void setCharactoristicNotify(UUID uuid_service, UUID uuid_characteristic) {
        BluetoothGattService mBluetoothGattService = this.getBluetoothGattService(this.mBluetoothGatt, uuid_service);
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = this.getBluetoothGattCharacteristic(mBluetoothGattService, uuid_characteristic);
        if(this.mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            this.mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic, true);
        } else if(this.mBluetoothGatt == null) {
            Log.d(this.TAG, "mBluetoothGatt is null");
        } else if(mBluetoothGattCharacteristic == null) {
            Log.d(this.TAG, "mBluetoothGattCharacteristic is null");
        }

    }

    private BluetoothGattService getBluetoothGattService(BluetoothGatt mBluetoothGatt, UUID UUID_SERVICE) {
        if(mBluetoothGatt != null) {
            BluetoothGattService mBluetoothGattServer = mBluetoothGatt.getService(UUID_SERVICE);
            if(mBluetoothGattServer != null) {
                return mBluetoothGattServer;
            }

            Log.d(this.TAG, "getBluetoothGattService, bluetoothgatt get service uuid:" + UUID_SERVICE + " is null");
        } else {
            Log.d(this.TAG, "mBluetoothGatt is null");
        }

        return null;
    }

    private BluetoothGattCharacteristic getBluetoothGattCharacteristic(BluetoothGattService mBluetoothGattService, UUID UUID_CHARACTERISTIC) {
        if(mBluetoothGattService != null) {
            BluetoothGattCharacteristic mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(UUID_CHARACTERISTIC);
            if(mBluetoothGattCharacteristic != null) {
                return mBluetoothGattCharacteristic;
            }

            Log.d(this.TAG, "getBluetoothGattCharacteristic, bluetoothGattServer get characteristic uuid:" + UUID_CHARACTERISTIC + " is null");
        } else {
            Log.d(this.TAG, "mBluetoothGattServer is null");
        }

        return null;
    }

    public void scan(boolean start) {
        if(this.mBluetoothAdapter != null) {
            if(start) {
                this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
            } else {
                this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
            }
        } else {
            Log.d(this.TAG, "bluetoothadapter is null");
        }

    }

    public static final int SCAN_START=0;
    public static final int SCAN_CLOSE=1;

    public void scan(int  start) {
        if(this.mBluetoothAdapter != null) {
            if(start==SCAN_START) {
                this.mBluetoothAdapter.startLeScan(this.newLeScanCallback);
            } else {
                this.mBluetoothAdapter.stopLeScan(this.newLeScanCallback);
            }
        } else {
            Log.d(this.TAG, "bluetoothadapter is null");
        }

    }

    private String[] scan_device_name;

    public void setScan_device_name(String[] scan_device_name) {
        this.scan_device_name = scan_device_name;
    }

    private LeScanCallback newLeScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            checkIsNeed(device,rssi,scanRecord);
        }
    };

    private void checkIsNeed(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (sdkCallBack!=null){
            String name=device.getName();
            sdkCallBack.onDeviceFound(device,rssi,scanRecord);
            if (name!=null) {
                if (scan_device_name != null && scan_device_name.length > 0) {
                    for (int i=0;i<scan_device_name.length;i++){
                        if (name.equals(scan_device_name[i])){
                            SDKCallBack.FoundDevice foundDevice = sdkCallBack.new FoundDevice();
                            foundDevice.setName(name);
                            foundDevice.setMac(device.getAddress());
                            sdkCallBack.onDeviceFound(foundDevice,rssi,scanRecord);
                            return;
                        }
                    }
                } else {
                    SDKCallBack.FoundDevice foundDevice = sdkCallBack.new FoundDevice();
                    foundDevice.setName(name);
                    foundDevice.setMac(device.getAddress());
                    sdkCallBack.onDeviceFound(foundDevice,rssi,scanRecord);
                }
            }
        }
    }

//    public void self_notifyAndWriteDescriptor( UUID uuid_service, UUID uuid_characteristic, UUID uuid_descriptor) throws NoConnectException {
//        if (getConnectionState() == STATE_CONNECTED) {
//            setCharactoristicNotifyAndWriteDescriptor(this.mBluetoothGatt, uuid_service, uuid_characteristic, uuid_descriptor);
//        }else {
//            throw  new NoConnectException("no connect, please connect to your band first");
//        }
//    }
//
    public void self_writeData(byte[] value) throws NoConnectException {
        if (value==null){
            throw new IllegalAccessError("parameter should not be null");
        }
        if (value.length>20){
            throw new IllegalAccessError("parameter's length should not exceed 20");
        }
        if (getConnectionState() == STATE_CONNECTED) {
            writeCharacteristic(this.mBluetoothGatt,Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public static final class Builder{
        private Context context;
        private int deviceType=1;
        private SDKCallBack callBack;

        public Builder(Context context,SDKCallBack callBack) {
            this.context = context;
            this.callBack = callBack;
        }

        public Builder setDeviceType(int deviceType) {
            if (!(deviceType==1||deviceType==2)){
                throw new IllegalArgumentException("Not a valid device type.");
            }
            this.deviceType = deviceType;
            return this;
        }


        public BleHelper build(){
            return new BleHelper(context,deviceType,callBack);
        }
    }





}
