package tech.shuidikeji.shuidijinfu.utils;

import android.text.TextUtils;

import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;

public class CommonUtils {
    public static boolean isLogin(){
        return !TextUtils.isEmpty(SPUtils.getString(PreferenceConstant.TOKEN));
    }
}
