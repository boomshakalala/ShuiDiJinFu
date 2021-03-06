package tech.shuidikeji.shuidijinfu.http;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tech.shuidikeji.shuidijinfu.constants.UrlConstant;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.BankCardPojo;
import tech.shuidikeji.shuidijinfu.pojo.BankCodePojo;
import tech.shuidikeji.shuidijinfu.pojo.BankListPojo;
import tech.shuidikeji.shuidijinfu.pojo.BankNamePojo;
import tech.shuidikeji.shuidijinfu.pojo.BorrowListPojo;
import tech.shuidikeji.shuidijinfu.pojo.CityListPojo;
import tech.shuidikeji.shuidijinfu.pojo.HomeDialgPojo;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;
import tech.shuidikeji.shuidijinfu.pojo.ImageCodePojo;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.pojo.LoginPojo;
import tech.shuidikeji.shuidijinfu.pojo.MessageUnReadPojo;
import tech.shuidikeji.shuidijinfu.pojo.NotificationPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitCheckPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitPojo;
import tech.shuidikeji.shuidijinfu.pojo.VerifyInfoPojo;
import tech.shuidikeji.shuidijinfu.pojo.WebAuthPojo;

public interface ApiService {

    @GET(UrlConstant.LOGOUT_APPELEMENTS)
    Observable<AppConfigPojo> getAppConfig(@Query("device")String device);

    @FormUrlEncoded
    @POST(UrlConstant.LOGIN_SET_TOKEN_KEY)
    Observable<Object> commitTokenKey(@Field("token_key")String tokenKey,@Field("user_id")String userId);

    @FormUrlEncoded
    @POST(UrlConstant.USER_CONTACTS)
    Observable<Object> postUserContacts(@Field("data") String data);

    @FormUrlEncoded
    @POST(UrlConstant.USER_CALL_LOG)
    Observable<Object> postUserCallLog(@Field("data") String data);

    @FormUrlEncoded
    @POST(UrlConstant.USER_SMS_LOG)
    Observable<Object> postUserSmsLog(@Field("data") String data);

    @FormUrlEncoded
    @POST(UrlConstant.USER_GPS_TRACK)
    Observable<Object> postUserLocation(@Field("lng") double lng,@Field("lat") double lat,@Field("marking") String marking,@Field("device") String device);

    @GET(UrlConstant.HOME_INIT)
    Observable<IndexPojo> getIndexLogin();

    @GET(UrlConstant.NO_LOGIN_INDEX_DEFAULT)
    Observable<IndexPojo> getIndexDefault();

    @GET(UrlConstant.NO_LOGIN_PUT_AWAY_INDEX_DEFAULT)
    Observable<IndexPojo> getIndexUnderReview();

    @GET(UrlConstant.NOTIFICATION)
    Observable<NotificationPojo> getNotification();

    @GET(UrlConstant.HOME_ACTIVITY)
    Observable<HomeDialgPojo> getHomeDialog(@Query("app_state") int status);

    @GET(UrlConstant.LOGIN_CAPTCHA)
    Observable<String> getCaptcha(@Query("phone") String phone);

    @GET(UrlConstant.LOGIN_VERICODE)
    Observable<ImageCodePojo> getImageCode(@Query("uuid")String uuid);

    @FormUrlEncoded
    @POST(UrlConstant.LOGIN_SAVE)
    Observable<LoginPojo> login(@Field("phone") String phone,@Field("captcha") String captcha,
                                @Field("token_key") String tokenKey,@Field("register_lng") double lng,
                                @Field("register_lat") double lat,@Field("register_type") String registerType,
                                @Field("device") String device,@Field("device_sn") String deviceSn,
                                @Field("market_id") String marketId,@Field("brand") String brand,
                                @Field("ram") String ram);

    @GET(UrlConstant.USER_VERIFY_INFO)
    Observable<VerifyInfoPojo> getUserVerifyInfo();

    @GET(UrlConstant.MESSAGE_UNREAD)
    Observable<MessageUnReadPojo> getMessageUnRead();

    @GET(UrlConstant.VERIFY_CHOICE)
    Observable<List<AuthListPojo>> getAuthList();

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_ORC_IDCARD)
    Observable<IdCardPojo> uploadIdCard(@Field("idcard_picture_1") String frontImage,@Field("idcard_picture_2") String backImage,@Field("idcard_picture_3") String faceImage);

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_ORC_LIVE_CHECK)
    Observable<Object> uploadFaceImage(@Field("livecheck_picture_1") String faceImage);

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_ORC_IDCARD_SAVE)
    Observable<Object> commitIdCard(@Field("name") String name,@Field("sex") String sex,@Field("nation") String nation,@Field("birth") String birth,@Field("address") String address,@Field("number") String number);

    @GET(UrlConstant.USER_RECORD)
    Observable<BorrowListPojo> getBorrowList(@Query("page")int page);

    @GET(UrlConstant.USER_BANK_INFO)
    Observable<BankCardPojo> getBankCard();

    @GET(UrlConstant.VERIFY_BANK_CARD_ALLOW_BANK)
    Observable<List<BankListPojo>> getBankList();

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_BANK_CARD_CAR_BIN)
    Observable<BankNamePojo> getBankName(@Field("card_number")String cardNumber);

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_BANK_CARD_SET_BANK_CARD)
    Observable<BankCodePojo> getBankCode(@Field("phone")String phone,@Field("card_number")String cardNumber,@Field("city_code")String cityCode);

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_BANK_CARD_SET_BANK_CARD_CONFIRM)
    Observable<Object> addBankCard(@Field("code")String code,@Field("requestno")String requestNo);

    @GET(UrlConstant.VERIFY_BANK_CARD_CITY_LIST)
    Observable<List<CityListPojo>> getCityList();

    @GET(UrlConstant.APPLICATION_JOB)
    Observable<Map<String, Map<String,Integer>>> getBasicAuthConfig();

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_BASIC_INFO)
    Observable<Object> commitBasicInfo(@Field("job_company")String jobCompany,@Field("job_address") String jobAddress,
                                       @Field("job_province") String jobProvince,@Field("job_city") String jobCity,
                                       @Field("job_distric") String jobDist,@Field("qq") String qq,
                                       @Field("marital_status") int maritalStatus,@Field("social") int social,
                                       @Field("job") int job,@Field("job_type") int jobType);

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_SET_EMERGENCY_CONTACT)
    Observable<Object> commitEmergencyContact(@Field("emergency_contact_name")String immediateName, @Field("emergency_contact_phone")String immediatePhone,
                                              @Field("emergency_contact_relation")String immediateRelation, @Field("other_contact_name")String otherName,
                                              @Field("other_contact_phone")String otherPhone, @Field("other_contact_relation")String otherRelation);

    @GET(UrlConstant.VERIFY_FORCE_VERIFY_ROUTER)
    Observable<WebAuthPojo> getWebAuthUrl(@Query("identification") String identification);

    @GET(UrlConstant.VERIFY_SUBMIT_CHECK)
    Observable<SubmitCheckPojo> submitCheck();

    @FormUrlEncoded
    @POST(UrlConstant.VERIFY_SUBMIT_APPLY)
    Observable<SubmitPojo> submitApply(@Field("black_box")String blackBox);

}

