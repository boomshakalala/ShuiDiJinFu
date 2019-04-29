package tech.shuidikeji.shuidijinfu.constants;

import tech.shuidikeji.shuidijinfu.BuildConfig;

public class UrlConstant {
    public static String API_HOST = BuildConfig.API_HOST;
    public static String H5_HOST = BuildConfig.H5_HOST;

    //帮助中心H5
    public static final String HELP_QA = H5_HOST + "/help/qa";
    //关于我们
    public static final String HELP_ABOUT = H5_HOST + "/help/about";
    //联系我们
    public static final String HELP_CONCAT = H5_HOST + "/help/concat";
    //用户协议
    public static final String HELP_USER_PROTOCOL = H5_HOST + "/help/user_protocol";

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
    //登录获取图形验证码
    public static final String LOGIN_VERICODE = "login/vericode";
    //用户登录
    public static final String LOGIN_SAVE =  "login/save";
    //用户认证状态
    public static final String USER_VERIFY_INFO = "user/verifyInfo";
    //用户未读消息数
    public static final String MESSAGE_UNREAD = "message/unread";
    //认证项目
    public static final String VERIFY_CHOICE = "verify/verifychoice";
    //上传身份证图片
    public static final String VERIFY_ORC_IDCARD = "verifyocr/idcard";
    public static final String VERIFY_ORC_LIVE_CHECK = "verifyocr/livecheck";
    //提交身份认证
    public static final String VERIFY_ORC_IDCARD_SAVE = "verifyocr/idcard_save";
    //借款记录
    public static final String USER_RECORD = "user/record";
    //银行卡
    public static final String USER_BANK_INFO = "user/bank_info";
    //支持银行列表
    public static final String VERIFY_BANK_CARD_ALLOW_BANK = "verifybankcard/allow_bank";
    //卡bin校验
    public static final String VERIFY_BANK_CARD_CAR_BIN = "verifybankcard/carbin";
    //添加银行卡获取验证码
    public static final String VERIFY_BANK_CARD_SET_BANK_CARD = "verifybankcard/setBankcard";
    //添加银行卡
    public static final String VERIFY_BANK_CARD_SET_BANK_CARD_CONFIRM = "verifybankcard/setBankcardConfirm";
    //开户地址列表
    public static final String VERIFY_BANK_CARD_CITY_LIST = "verifybankcard/fuiou_area";
    //基本信息项
    public static final String APPLICATION_JOB = "application/job";
    //提交用户基本信息
    public static final String VERIFY_BASIC_INFO = "verify/setbasicinfo";
    //提交紧急联系人
    public static final String VERIFY_SET_EMERGENCY_CONTACT = "verify/setemergencycontact";
    //获取H5认证地址
    public static final String VERIFY_FORCE_VERIFY_ROUTER = "verify/forceverifyrouter";
    //提交检测核对
    public static final String VERIFY_SUBMIT_CHECK = "verify/submit_check";
    //提交检测
    public static final String VERIFY_SUBMIT_APPLY = "verify/submit_check";


}
