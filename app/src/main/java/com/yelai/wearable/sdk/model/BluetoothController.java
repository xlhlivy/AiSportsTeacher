package com.yelai.wearable.sdk.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.yelai.wearable.sdk.global.Config;
import com.yelai.wearable.sdk.helper.NoConnectException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/16.
 */
public class BluetoothController {

    private String TAG = "BluetoothController";
    private Context mContext;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
//    private String mBluetoothDeviceAddress;
    private int mConnectionState = 0;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (status==BluetoothGatt.GATT_SUCCESS){
                if (newState== BluetoothProfile.STATE_CONNECTED){
                    mConnectionState = 2;
                    if (sdkCallBack!=null){
                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_SUCCESS);
                        return;
                    }
                }else if (newState==BluetoothProfile.STATE_DISCONNECTED){
                    mConnectionState = 0;
                    close();
                    if (sdkCallBack!=null){
                        sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_DISCONNECT);
                    }
                }
            }else {
                mConnectionState = 0;
                disconnect();
                if (sdkCallBack!=null){
                    sdkCallBack.onConnectionStateChange(SDKCallBack.STATE_CONNECT_FAIL);
                }
            }

        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (sdkCallBack!=null){
                if (status==0){
                    sdkCallBack.onBluetoothServiceDiscover(true);
                }else {
                    sdkCallBack.onBluetoothServiceDiscover(false);
                }
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {


        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (sdkCallBack!=null){
                byte[] value=characteristic.getValue();
                sdkCallBack.onSelfDeviceResponse(value);
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (sdkCallBack!=null){
                sdkCallBack.onDescriptorWrite(status);
            }
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {

        }
    };

    public BluetoothGatt getmBluetoothGatt() {
        return mBluetoothGatt;
    }

    private SDKCallBack sdkCallBack;

    public BluetoothController(Context context) {
        this.mContext = context;
        this.initialize();
    }


    public int getConnectionState() {
        return this.mConnectionState;
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

    public boolean connect(String address) {
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
//                this.mBluetoothDeviceAddress = address;
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
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }


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


    private void  writeCharacteristic(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_character, byte[] value) {
        BluetoothGattService mBluetoothGattService = this.getBluetoothGattService(mBluetoothGatt, uuid_service);
        BluetoothGattCharacteristic mBluetoothGattCharacteristic = this.getBluetoothGattCharacteristic(mBluetoothGattService, uuid_character);
        if(mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
            mBluetoothGattCharacteristic.setValue(value);
            mBluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
        } else if(mBluetoothGatt == null) {
            Log.d(this.TAG, "mBluetoothGatt is null");
        } else if(mBluetoothGattCharacteristic == null) {
            Log.d(this.TAG, "mBluetoothGattCharacteristic is null");
        }

    }


    private void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if(this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        } else {
            Log.w(this.TAG, "BluetoothAdapter not initialized");
        }
    }

    private void setCharactoristicNotifyAndWriteDescriptor(BluetoothGatt mBluetoothGatt, UUID uuid_service, UUID uuid_characteristic, UUID uuid_descriptor) {
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

    public void scan() {
        if(this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.startLeScan(this.newLeScanCallback);
        } else {
            Log.d(this.TAG, "bluetoothadapter is null");
        }

    }

    public void stopScan(){
        if(this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.stopLeScan(this.newLeScanCallback);
        } else {
            Log.d(this.TAG, "bluetoothadapter is null");
        }
    }

    private String[] scan_device_name;

    public void setScan_device_name(String[] scan_device_name) {
        this.scan_device_name = scan_device_name;
    }

    private BluetoothAdapter.LeScanCallback newLeScanCallback = new BluetoothAdapter.LeScanCallback() {
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
                            sdkCallBack.onDeviceFound(foundDevice, rssi, scanRecord);
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

    public void self_notifyAndWriteDescriptor( UUID uuid_service, UUID uuid_characteristic, UUID uuid_descriptor) throws NoConnectException {
        if (getConnectionState() == STATE_CONNECTED) {
            setCharactoristicNotifyAndWriteDescriptor(this.mBluetoothGatt, uuid_service, uuid_characteristic, uuid_descriptor);
        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public boolean self_notify(UUID uuid_service, UUID uuid_characteristic) throws NoConnectException {
        if (getConnectionState() == STATE_CONNECTED) {
            BluetoothGattService mBluetoothGattService = this.getBluetoothGattService(mBluetoothGatt, uuid_service);
            BluetoothGattCharacteristic mBluetoothGattCharacteristic = this.getBluetoothGattCharacteristic(mBluetoothGattService, uuid_characteristic);
            if(mBluetoothGatt != null && mBluetoothGattCharacteristic != null) {
                return mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic, true);
            } else if(mBluetoothGatt == null) {
                Log.d(this.TAG, "mBluetoothGatt is null");
            } else if(mBluetoothGattCharacteristic == null) {
                Log.d(this.TAG, "mBluetoothGattCharacteristic is null");
            }
            return false;
        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public void self_writeData( UUID uuid_service, UUID uuid_character, byte[] value) throws NoConnectException {
        if (getConnectionState() == STATE_CONNECTED) {
            writeCharacteristic(this.mBluetoothGatt, uuid_service, uuid_character, value);
        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public void findGattService() throws NoConnectException {
        if (getConnectionState() == STATE_CONNECTED) {
            this.mBluetoothGatt.discoverServices();
        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public void set_notify_true() throws NoConnectException {
        if (getConnectionState() == STATE_CONNECTED) {
            this.setCharactoristicNotifyAndWriteDescriptor(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_NOTI, Config.UUID_DESCRIPTOR_CONFIGURATION);

        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public void  writeCharacteristic(byte[] value) throws NoConnectException {
        if (getConnectionState() == STATE_CONNECTED) {
            this.writeCharacteristic(this.mBluetoothGatt, Config.UUID_SERVICE, Config.UUID_CHARACTERISTIC_WRITE_AND_READ, value);
        }else {
            throw  new NoConnectException("no connect, please connect to your band first");
        }
    }

    public void registCallback(SDKCallBack callBack){
        sdkCallBack=callBack;
    }

    public void unRegistCallback(){
        sdkCallBack=null;
    }


}
