package tech.shuidikeji.shuidijinfu.utils;

import android.text.TextUtils;

import tech.shuidikeji.shuidijinfu.base.ActivityManager;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.ui.activity.LoginActivity;

public class CommonUtils {
    public static boolean isLogin(){
        return !TextUtils.isEmpty(SPUtils.getString(PreferenceConstant.TOKEN));
    }

    public static void clearLoginInfo(){
        SPUtils.remove(PreferenceConstant.USER_PHONE);
        SPUtils.remove(PreferenceConstant.TOKEN);
        SPUtils.remove(PreferenceConstant.USER_ID);
    }


}
