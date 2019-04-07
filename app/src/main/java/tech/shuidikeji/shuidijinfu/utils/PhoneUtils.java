package tech.shuidikeji.shuidijinfu.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhoneUtils {
    /**
     * 获取库Phon表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 判断是否有SIM卡
     */
    public static boolean hasSIMCard() {
        TelephonyManager telMgr = (TelephonyManager)
                AppUtils.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        return result;
    }

    /**
     * 获取SIM卡联系人
     */
    public static List<HashMap<String,String>> getSIMContacts(){
        List<HashMap<String,String>> result = new ArrayList<>();
        ContentResolver resolver = AppUtils.getAppContext().getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor cursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = cursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = cursor
                        .getString(PHONES_DISPLAY_NAME_INDEX);

                phoneNumber = phoneNumber.trim();
                if (phoneNumber.length() < 10){
                    HashMap<String,String> contact = new HashMap<>();
                    contact.put("mobile",phoneNumber);
                    contact.put("name",contactName);
                    result.add(contact);
                }
            }

            cursor.close();
        }

        return result;
    }

    /**
     * 获取手机联系人
     */
    public static List<HashMap<String,String>> getPhoneContacts(){
        List<HashMap<String,String>> result = new ArrayList<>();
        ContentResolver resolver = AppUtils.getAppContext().getContentResolver();
        // 获取手机联系人
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = cursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = cursor.getString(PHONES_DISPLAY_NAME_INDEX);

                phoneNumber = phoneNumber.trim();
                if (phoneNumber.length() >= 10) {
                    HashMap<String,String> contact = new HashMap<>();
                    contact.put("mobile",phoneNumber);
                    contact.put("name",contactName);
                    result.add(contact);
                }


            }
            cursor.close();
        }

        return result;
    }

    /**
     * 获取通话记录
     */
    public static List<HashMap<String,String>> getPhoneCallLog(){
        List<HashMap<String,String>> result = new ArrayList<>();
        ContentResolver cr = AppUtils.getAppContext().getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection;// 通话类型
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            projection = new String[]{
                    CallLog.Calls.CACHED_NAME// 通话记录的联系人
                    , CallLog.Calls.NUMBER// 通话记录的电话号码
                    , CallLog.Calls.DATE// 通话记录的日期
                    , CallLog.Calls.GEOCODED_LOCATION // 地理编码定位
                    , CallLog.Calls.DURATION// 通话时长
                    , CallLog.Calls.TYPE};
        } else {
            projection = new String[]{
                    CallLog.Calls.CACHED_NAME// 通话记录的联系人
                    , CallLog.Calls.NUMBER// 通话记录的电话号码
                    , CallLog.Calls.DATE// 通话记录的日期
                    , CallLog.Calls.DURATION// 通话时长
                    , CallLog.Calls.TYPE};
        }
        if (ActivityCompat.checkSelfPermission(AppUtils.getAppContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //如果没有权限
            return result;
        }
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor == null) {
            //没有获取到通话记录
            return result;
        }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            //通话时间
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            //通话时长
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            String call_location = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                // 地理编码定位
                call_location = cursor.getString(cursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION));
            }
            //通话类型
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            String typeString = "";
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeString = "打入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeString = "打出";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeString = "未接";
                    break;
                default:
                    break;
            }

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("call_location",call_location);
            hashMap.put("name",name);
            hashMap.put("mobile",number);
            hashMap.put("call_time",dateLong+"");
            hashMap.put("duration_time",duration+"");
            hashMap.put("call_type",typeString);
            result.add(hashMap);
        }
        cursor.close();
        return result;
    }

    /**
     * 获取短信
     */
    public static List<HashMap<String,String>> getSMS(){
        List<HashMap<String,String>> result = new ArrayList<>();
        ContentResolver cr = AppUtils.getAppContext().getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(Uri.parse("content://sms/"), projection, null, null, "date desc");
        if (null == cur) {
            return result;
        }
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            String type = cur.getString(cur.getColumnIndex("type"));//短信类型1是接收到的，2是已发出
            long date = cur.getLong(cur.getColumnIndex("date"));//long型，如1256539465022，可以对日期显示格式进行设置
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("name",name);
            hashMap.put("sms_body",body);
            hashMap.put("mobile",number);
            hashMap.put("sms_type",type);
            hashMap.put("sms_time", DateUtils.formatDateTime(AppUtils.getAppContext(),date,DateUtils.FORMAT_SHOW_YEAR|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_TIME));
            result.add(hashMap);
        }
        cur.close();
        return result;
    }


}
