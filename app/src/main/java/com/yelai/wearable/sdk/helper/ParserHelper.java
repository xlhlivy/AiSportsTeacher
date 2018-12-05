//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yelai.wearable.sdk.helper;

import android.content.Context;
import android.util.Log;
import com.yelai.wearable.sdk.global.Config;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserHelper {
    private static final int STEP_SLEEP_USELESS = 65535;
    private static final int STEP_SLEEP_BOUNDARY = 65280;
    public static final int DEFAULT_GOAL_STEP = 2000;
    public static final int DEFAULT_GOAL_BURN = 100;
    public static final double DEFAULT_GOAL_SLEEP = 8.0D;
    public static final int DEFAULT_GOAL = 10000;
    public static final int DEFAULT_DAY_TIME_BEGIN = 9;
    public static final int DEFAULT_DAY_TIME_END = 21;
    public static final int DEFAULT_ALARM_HOUR = 0;
    public static final int DEFAULT_ALARM_MINUTE = 0;
    public static final int DEFAULT_ALARM_REPEAT = 0;
    public static final double DEFAULT_HEIGHT = 175.0D;
    public static final double DEFAULT_WEIGHT = 70.0D;
    public static final int DEFAULT_GENDER = 1;
    public static final double DEFAULT_STEP_DISTANCE = 78.75D;

    public ParserHelper() {
    }

    public static JSONObject parseResult(byte[] value) {
        JSONObject object = new JSONObject();
        int title = getByteValue(value[0]);
        int e;
        if(title == 240) {
            String dat = "";

            for(e = 1; e < 20; ++e) {
                if(value[e] != 0) {
                    dat = dat + (char)value[e];
                }
            }

            try {
                object.put("result", dat);
            } catch (JSONException var6) {
                var6.printStackTrace();
            }
        } else {
            int[] var7 = new int[20];

            for(e = 0; e < 20; ++e) {
                if(value[e] >= 0) {
                    var7[e] = value[e];
                } else {
                    var7[e] = 256 + value[e];
                }
            }

            try {
                if(title == 139) {
                    object.put("result", var7[2]);
                } else {
                    object.put("result", var7[1]);
                }
            } catch (JSONException var5) {
                var5.printStackTrace();
            }
        }

        return object;
    }

    public static JSONObject parseNullResult() {
        JSONObject object = new JSONObject();

        try {
            object.put("result", "1");
        } catch (JSONException var2) {
            var2.printStackTrace();
        }

        return object;
    }

    public static JSONObject parseDisconnectResult() {
        JSONObject object = new JSONObject();

        try {
            object.put("result", "0");
        } catch (JSONException var2) {
            var2.printStackTrace();
        }

        return object;
    }

    public static JSONObject parseActivity(byte[] value, int deviceType) {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        int[] dat = new int[20];

        int year;
        for(year = 0; year < 20; ++year) {
            if(value[year] >= 0) {
                dat[year] = value[year];
            } else {
                dat[year] = 256 + value[year];
            }
        }

        if(dat[1] != 255 && dat[2] != 255) {
            year = dat[1] * 100 + dat[2];
            int month = dat[3];
            int day = dat[4];
            int beginHour = dat[5];

            for(int e = 0; e < 6; ++e) {
                int indexBegin = e * 2 + 6;
                int indexEnd = e * 2 + 7;
                String valueStr = byteToBinaryString(dat[indexBegin]) + byteToBinaryString(dat[indexEnd]);
                int value_ = Integer.parseInt(valueStr, 2);
                if(value_ != '\uffff') {
                    JSONObject historyHourObject;
                    if(deviceType == 2) {
                        historyHourObject = new JSONObject();

                        try {
                            historyHourObject.put("year", year);
                            historyHourObject.put("month", month);
                            historyHourObject.put("day", day);
                            historyHourObject.put("hour", beginHour + e);
                            if(value_ < '\uff00') {
                                historyHourObject.put("value", value_);
                                historyHourObject.put("type", "step");
                            } else if(value_ > '\uff00') {
                                int e1 = value_ - '\uff00';
                                historyHourObject.put("value", e1);
                                historyHourObject.put("type", "sleep");
                            }
                        } catch (JSONException var18) {
                            var18.printStackTrace();
                        }

                        array.put(historyHourObject);
                    } else if(deviceType == 1) {
                        historyHourObject = new JSONObject();

                        try {
                            historyHourObject.put("year", year);
                            historyHourObject.put("month", month);
                            historyHourObject.put("day", day);
                            historyHourObject.put("hour", beginHour + e);
                            historyHourObject.put("value", value_);
                            historyHourObject.put("type", "step");
                        } catch (JSONException var17) {
                            var17.printStackTrace();
                        }

                        array.put(historyHourObject);
                    }
                }
            }

            try {
                object.put("ActivityData", array);
            } catch (JSONException var16) {
                var16.printStackTrace();
            }
        }

        return object;
    }
    
    public static JSONObject parseCurrentHourActivity(byte[] value) {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        int[] dat = new int[20];

        int year;
        for(year = 0; year < 20; ++year) {
            if(value[year] >= 0) {
                dat[year] = value[year];
            } else {
                dat[year] = 256 + value[year];
            }
        }

        if(dat[1] != 255 && dat[2] != 255) {
            year = dat[1] * 100 + dat[2];
            int month = dat[3];
            int day = dat[4];
            int beginHour = dat[5];


                int indexBegin =   6;
                int indexEnd =  7;
                String valueStr = byteToBinaryString(dat[indexBegin]) + byteToBinaryString(dat[indexEnd]);
                int value_ = Integer.parseInt(valueStr, 2);
                if(value_ != '\uffff') {
                    JSONObject historyHourObject;
                        historyHourObject = new JSONObject();
                        try {
                            historyHourObject.put("year", year);
                            historyHourObject.put("month", month);
                            historyHourObject.put("day", day);
                            historyHourObject.put("hour", beginHour);
                            historyHourObject.put("value", value_);
                            historyHourObject.put("type", "step");
                        } catch (JSONException var17) {
                            var17.printStackTrace();
                        }
                        array.put(historyHourObject);
                }


            try {
                object.put("ActivityData", array);
            } catch (JSONException var16) {
                var16.printStackTrace();
            }
        }

        return object;
    }

    public static JSONObject parseSleep(byte[] value) {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        int[] dat = new int[20];

        int year;
        for(year = 0; year < 20; ++year) {
            if(value[year] >= 0) {
                dat[year] = value[year];
            } else {
                dat[year] = 256 + value[year];
            }
        }

        if(dat[1] != 255 && dat[2] != 255) {
            year = dat[1] * 100 + dat[2];
            int month = dat[3];
            int day = dat[4];
            int beginHour = dat[5];

            for(int e = 0; e < 6; ++e) {
                int indexBegin = e * 2 + 6;
                int indexEnd = e * 2 + 7;
                String beginStr = String.format("%x", new Object[]{Integer.valueOf(dat[indexBegin])});
                String endStr = String.format("%x", new Object[]{Integer.valueOf(dat[indexEnd])});
                Log.i("test", "*** dat begin = " + dat[indexBegin] + ",   end = " + dat[indexEnd] + ", " + beginStr + ", " + endStr);
                if(dat[indexBegin] < 16) {
                    beginStr = "0" + beginStr;
                }

                if(dat[indexEnd] < 16) {
                    endStr = "0" + endStr;
                }

                String valueStr = beginStr + endStr;
                if(!valueStr.equals("ffff")) {
                    JSONObject historyHourObject = new JSONObject();

                    try {
                        historyHourObject.put("year", year);
                        historyHourObject.put("month", month);
                        historyHourObject.put("day", day);
                        historyHourObject.put("hour", beginHour + e);
                        if(valueStr.length() == 4) {
                            String e1 = "";

                            for(int ii = 0; ii < 4; ++ii) {
                                String sleep_str = valueStr.substring(3 - ii, 3 - ii + 1);
                                if(sleep_str.equals("f")) {
                                    sleep_str = "-1";
                                }

                                e1 = e1 + Integer.parseInt(sleep_str, 16) + ":";
                            }

                            e1 = e1.substring(0, e1.length() - 1);
                            historyHourObject.put("value", e1);
                        }

                        historyHourObject.put("type", "sleep");
                    } catch (JSONException var19) {
                        var19.printStackTrace();
                    }

                    array.put(historyHourObject);
                }
            }

            try {
                object.put("SleepData", array);
            } catch (JSONException var18) {
                var18.printStackTrace();
            }
        }

        return object;
    }

    public static String parseActivityDate(byte[] value) {
        System.out.println(Arrays.toString(value));
        String datetime = null;
        int[] dat = new int[20];

        int year;
        for(year = 0; year < 20; ++year) {
            if(value[year] >= 0) {
                dat[year] = value[year];
            } else {
                dat[year] = 256 + value[year];
            }
        }

        if(dat[1] != 255 && dat[2] != 255) {
            year = dat[1] * 100 + dat[2];
            int month = dat[3];
            int day = dat[4];
            int beginHour = dat[5];
            datetime = year + "-" + Config.df_1.format((long)month) + "-" + Config.df_1.format((long)day) + " " + beginHour;
        }

        return datetime;
    }

    public static JSONObject parseActivityCount(byte[] value) {
        JSONObject object = new JSONObject();
        int[] dat = new int[20];

        for(int e = 0; e < 20; ++e) {
            if(value[e] >= 0) {
                dat[e] = value[e];
            } else {
                dat[e] = 256 + value[e];
            }
        }

        try {
            object.put("sn", dat[1]);
            object.put("count", dat[2]);
            object.put("year", dat[3] * 100 + dat[4]);
            object.put("month", dat[5]);
            object.put("day", dat[6]);
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

        return object;
    }

    public static int parseActivitySn(byte[] value) {
        int[] dat = new int[20];

        int sn;
        for(sn = 0; sn < 20; ++sn) {
            if(value[sn] >= 0) {
                dat[sn] = value[sn];
            } else {
                dat[sn] = 256 + value[sn];
            }
        }

        sn = dat[1];
        int year = dat[3] * 100 + dat[4];
        int month = dat[5];
        int day = dat[6];
        System.out.println("sn:" + sn + "\n" + "date:" + year + "-" + month + "-" + day);
        return sn;
    }

    public static int getByteValue(byte title) {
        int value = title;
        if(title < 0) {
            value = title + 256;
        }

        return value;
    }

    public static JSONObject parseZoon(byte[] value) {
        JSONObject object = new JSONObject();
        int[] dat = new int[20];

        for(int sb = 0; sb < 20; ++sb) {
            if(value[sb] >= 0) {
                dat[sb] = value[sb];
            } else {
                dat[sb] = 256 + value[sb];
            }
        }

        StringBuffer var7 = new StringBuffer();

        for(int zoon = 2; zoon < 6; ++zoon) {
            String e = Integer.toHexString(dat[zoon]);
            if(e.length() == 1) {
                e = "0" + e;
            }

            var7.append(e);
        }

        String var8 = var7.toString().toUpperCase(Locale.ENGLISH);

        try {
            object.put("zoon", var8);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        return object;
    }

    public static JSONObject parseHeart(byte[] value) {
        JSONObject object = new JSONObject();
        int[] dat = new int[20];

        for(int e = 0; e < 20; ++e) {
            if(value[e] >= 0) {
                dat[e] = value[e];
            } else {
                dat[e] = 256 + value[e];
            }
        }

        try {
            object.put("result", dat[2]);
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

        return object;
    }

    public static byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];

        for(int i = 7; i >= 0; --i) {
            array[i] = (byte)(b & 1);
            b = (byte)(b >> 1);
        }

        return array;
    }

    public static byte[] getDateTimeValue(Calendar calendar,int hour_begin, int hour_end) {
        byte[] value = new byte[20];
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        value[0] = 1;
        value[1] = (byte)(year / 100);
        value[2] = (byte)(year % 100);
        value[3] = (byte)month;
        value[4] = (byte)day;
        value[5] = (byte)hour;
        value[6] = (byte)minute;
        value[7] = (byte)second;
        value[8] = (byte) hour_begin;
        value[9] = (byte) hour_end;
        return value;
    }

    public static byte[] getGoalValue(Context context, int goalStep) {
        byte[] value = new byte[20];
        String stepGoalBinary = Integer.toBinaryString(goalStep);
        if(stepGoalBinary != null) {
            int stepHigh = stepGoalBinary.length();
            if(stepHigh < 16) {
                for(int stepLow = 0; stepLow < 16 - stepHigh; ++stepLow) {
                    stepGoalBinary = "0" + stepGoalBinary;
                }
            }
        }

        String var6 = stepGoalBinary.substring(0, 8);
        String var7 = stepGoalBinary.substring(8, 16);
        value[0] = 11;
        value[1] = 1;
        value[2] = (byte)Integer.parseInt(var6, 2);
        value[3] = (byte)Integer.parseInt(var7, 2);
        return value;
    }

    public static byte[] getActivityCountValue(Context context) {
        byte[] value = new byte[20];
        value[0] = 16;
        return value;
    }

    public static byte[] getActivityValue(Context context, int sn, int hour) {
        byte[] value = new byte[20];
        value[0] = 19;
        value[1] = (byte)sn;
        value[2] = (byte)hour;
        return value;
    }

    public static byte[] getSleepValue(int sn, int hour) {
        byte[] value = new byte[20];
        value[0] = 17;
        value[1] = (byte)sn;
        value[2] = (byte)hour;
        return value;
    }

    public static byte[] getDayModeValue(Context context, int hour_begin, int hour_end) {
        byte[] value = new byte[20];
        value[0] = 2;
        value[1] = (byte)hour_begin;
        value[2] = (byte)hour_end;
        return value;
    }

    public static byte[] getAlarmValue(Context context, int alarm_hour_1, int alarm_minute_1, int alarm_repeat_1, int alarm_hour_2, int alarm_minute_2, int alarm_repeat_2, int alarm_hour_3, int alarm_minute_3, int alarm_repeat_3) {
        byte[] value = new byte[20];
        value[0] = 3;
        value[1] = (byte)alarm_hour_1;
        value[2] = (byte)alarm_minute_1;
        value[3] = (byte)alarm_repeat_1;
        value[4] = (byte)alarm_hour_2;
        value[5] = (byte)alarm_minute_2;
        value[6] = (byte)alarm_repeat_2;
        value[7] = (byte)alarm_hour_3;
        value[8] = (byte)alarm_minute_3;
        value[9] = (byte)alarm_repeat_3;
        return value;
    }

    public static byte[] getLanguageValue(Context context, int languageType) {
        byte[] value = new byte[20];
        value[0] = 10;
        value[1] = 1;
        value[2] = (byte)languageType;
        return value;
    }

    public static byte[] getProfileValue(Context context, double height, double weight, int gender) {
        byte[] value = new byte[20];
        value[0] = 12;
        value[1] = (byte)((int)height);
        value[2] = (byte)((int)weight);
        value[3] = (byte)gender;
        return value;
    }

    public static byte[] getBloodPressureValue(int SBP,int DBP){
        byte[] value = new byte[20];
        value[0]=0x57;
        value[1]=0;
        value[2]= (byte) SBP;
        value[3]= (byte) DBP;
        return value;
    }

    public static byte[] getCameraValue(int type) {
        byte[] value = new byte[20];
        value[0] = 80;
        value[1] = 1;
        if(type == 0) {
            value[2] = 81;
        } else if(type == 1) {
            value[2] = 80;
        }

        return value;
    }

    public static byte[] getCallValue(Context context) {
        byte[] value = new byte[20];
        value[0] = 32;
        value[1] = 0;
        return value;
    }

    public static byte[] getCallTerminationValue(Context context) {
        byte[] value = new byte[20];
        value[0] = 32;
        value[1] = 1;
        return value;
    }

    public static byte[] getSMSValue(Context context, String number) {
        number = phoneNumberFilter(number);
        if(number == null) {
            return null;
        } else {
            byte[] value = new byte[20];
            value[0] = 33;
            value[1] = (byte)number.length();
            char[] num = number.toCharArray();

            for(int i = 0; i < num.length; ++i) {
                value[i + 2] = getASCIIValue(Integer.parseInt(number.substring(i, i + 1)));
            }

            return value;
        }
    }

    public static byte[] getZoonValue() {
        byte[] value = new byte[20];
        value[0] = 65;
        value[1] = 0;
        return value;
    }

    public static byte[] getLostModeValue(Context context, int type) {
        byte[] value = new byte[20];
        value[0] = 67;
        if(type == 0) {
            value[1] = 1;
        } else if(type == 1) {
            value[1] = 0;
        }

        return value;
    }

    public static byte[] getSensorType(Context context) {
        byte[] value = new byte[20];
        value[0] = 96;
        return value;
    }

    public static byte[] getSensorValue(Context context, byte type) {
        byte[] value = new byte[20];
        value[0] = 97;
        value[1] = type;
        return value;
    }

    public static byte[] getBoundValue(int type) {
        byte[] value = new byte[20];
        value[0] = 15;
        value[1] = 1;
        value[2] = (byte)type;
        return value;
    }

    public static byte[] getBoundValue() {
        byte[] value = new byte[20];
        value[0] = 15;
        value[1] = 0;
        return value;
    }

    public static byte[] getBatteryValue() {
        byte[] value = new byte[20];
        value[0] = 48;
        return value;
    }

    public static byte[] getVersionValue() {
        byte[] value = new byte[20];
        value[0] = 112;
        return value;
    }

    public static byte[] getTouchVibrationvalue(boolean isTouchVibration, int time) {
        byte[] value = new byte[20];
        value[0] = 84;
        value[1] = 1;
        if(isTouchVibration) {
            value[2] = (byte)time;
        } else {
            value[2] = 0;
        }

        return value;
    }

    public static byte[] getUnitValue(int type) {
        byte[] value = new byte[20];
        value[0] = 85;
        value[1] = 1;
        value[2] = (byte)type;
        return value;
    }

    public static byte[] getTimeFormatValue(int type) {
        byte[] value = new byte[20];
        value[0] = 13;
        value[1] = (byte)type;
        return value;
    }

    public static byte[] getHeartValue(byte type) {
        byte[] value = new byte[20];
        value[0] = 0x56;
        value[1] = type;
        return value;
    }

    public static byte[] getVibrationSettings(int type,byte[] vibrations){
        byte[] value = new byte[20];
        value[0]=0x2d;
        value[1]=0x01;
        value[2]= (byte) type;
        for (int i=0;i<vibrations.length;i++){
            value[i+3]=vibrations[i];
        }
        return value;
    }

    public static byte[] getHeartWarnValue(boolean isOpen,int high,int low,boolean isAlwaysLight) {
        byte[] value = new byte[20];
        value[0] = 0x56;
        value[1] = 0x04;
        if (isOpen){
            value[2] = 1;
        }else {
            value[2] = 0;
        }
        value[3] = (byte) high;
        value[4] = (byte) low;
        if (isAlwaysLight){
            value[5]=1;
        }else {
            value[5]=0;
        }
        return value;
    }

    private static String byteToBinaryString(int b) {
        String binary = Integer.toBinaryString(b);
        if(binary != null) {
            int size = binary.length();
            if(size < 8) {
                for(int i = 0; i < 8 - size; ++i) {
                    binary = "0" + binary;
                }
            }
        }

        return binary;
    }

    public static String phoneNumberFilter(String number) {
        if(number != null && !number.equals("")) {
            StringBuffer sb = new StringBuffer();
            String str = null;
            char[] num = number.toCharArray();
            char[] var7 = num;
            int var6 = num.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                char c = var7[var5];
                if(Character.isDigit(c)) {
                    sb.append(c);
                }
            }

            if(sb.substring(0, 2).equalsIgnoreCase("86")) {
                str = sb.toString().substring(2);
            } else {
                str = sb.toString();
            }

            return str;
        } else {
            return null;
        }
    }

    public static byte getASCIIValue(int number) {
        byte returnValue = 48;
        switch(number) {
            case 1:
                returnValue = 49;
                break;
            case 2:
                returnValue = 50;
                break;
            case 3:
                returnValue = 51;
                break;
            case 4:
                returnValue = 52;
                break;
            case 5:
                returnValue = 53;
                break;
            case 6:
                returnValue = 54;
                break;
            case 7:
                returnValue = 55;
                break;
            case 8:
                returnValue = 56;
                break;
            case 9:
                returnValue = 57;
        }

        return returnValue;
    }

    public static String getNearestDeviceAddress(Map<String, Integer> deviceMap) {
        if(deviceMap != null) {
            Set keySet = deviceMap.keySet();
            int rssiMax = -9999;
            String nearestDevice = null;
            Iterator var5 = keySet.iterator();

            while(var5.hasNext()) {
                String address = (String)var5.next();
                Integer rssiValue = (Integer)deviceMap.get(address);
                if(rssiValue != null && rssiValue.intValue() > rssiMax) {
                    rssiMax = rssiValue.intValue();
                    nearestDevice = address;
                }
            }

            Log.i("SettingsFragment", "nearest address: " + nearestDevice);
            return nearestDevice;
        } else {
            return null;
        }
    }
}
