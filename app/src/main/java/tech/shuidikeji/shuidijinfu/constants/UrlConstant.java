package tech.shuidikeji.shuidijinfu.constants;

import tech.shuidikeji.shuidijinfu.BuildConfig;

public class UrlConstant {
    public static String API_HOST = BuildConfig.API_HOST;
    public static String H5_HOST = BuildConfig.H5_HOST;

    //获取APP配置信息
    public static final String LOGOUT_APPELEMENTS = "logout/appelements";
    //提交白骑士设备指纹
    public static final String LOGIN_SET_TOKEN_KEY = "login/set_tokenkey";
    //上传通讯录
    public static final String USER_CONTACTS = "user/contacts";
    //上传通话记录
    public static final String USER_CALL_LOG = "user/calllog";
    //上传用户位置信息
    public static final String USER_GPS_TRACK = "user/gps_track";
    //上传短信记录
    public static final String USER_SMS_LOG = "user/smslog";
    //登录状态下首页数据(审核通过)
    public static final String HOME_INIT = "home/init";
    //未登录状态下首页数据(审核通过)
    public static final String NO_LOGIN_INDEX_DEFAULT = "application/no_login_index_default";
    //审核中首页数据
    public static final String NO_LOGIN_PUT_AWAY_INDEX_DEFAULT = "application/no_login_put_away_index_default";
    //首页公告
    public static final String NOTIFICATION = "notification";
    //首页登录状态弹窗(审核通过)
    public static final String HOME_ACTIVITY = "home/activity";
    //登录获取验证码
    public static final String LOGIN_CAPTCHA = "login/captcha";
    //用户登录
    public static final String LOGIN_SAVE =  "login/save";
    //用户认证状态
    public static final String USER_VERIFY_INFO = "user/verifyInfo";

}
