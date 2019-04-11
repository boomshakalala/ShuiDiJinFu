package tech.shuidikeji.shuidijinfu.utils;

import android.text.TextUtils;

public class DataOptionUtils {

    public static String getEncryptMobile(String mobile) {
        if (mobile == null) {
            return "";
        }
        String encryptMobile;
        if (mobile.length() > 10) {
            encryptMobile = mobile.substring(0, 3) + " **** " + mobile.substring(7, 11);
        } else {
            encryptMobile = "";
        }
        return encryptMobile;
    }

    /**
     * 隐藏中间的位数
     *
     * @param str
     * @return
     */
    public static StringBuilder getHideCenterString(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if (TextUtils.isEmpty(str)){
            return stringBuilder;
        }
        for (int i = 0; i < str.length(); i++) {
            if (i <= 3) {
                stringBuilder.append(str.charAt(i));
            } else if (i < str.length() - 4 && str.length() > 3) {
                stringBuilder.append("*");
            } else {
                stringBuilder.append(str.charAt(i));
            }
        }
        return stringBuilder;
    }
}
