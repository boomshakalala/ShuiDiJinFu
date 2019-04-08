package tech.shuidikeji.shuidijinfu.http;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tech.shuidikeji.shuidijinfu.constants.UrlConstant;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.HomeDialgPojo;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.pojo.LoginPojo;
import tech.shuidikeji.shuidijinfu.pojo.NotificationPojo;

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

    @FormUrlEncoded
    @POST(UrlConstant.LOGIN_SAVE)
    Observable<LoginPojo> login(@Field("phone") String phone,@Field("captcha") String captcha,
                                @Field("token_key") String tokenKey,@Field("register_lng") double lng,
                                @Field("register_lat") double lat,@Field("register_type") String registerType,
                                @Field("device") String device,@Field("device_sn") String deviceSn,
                                @Field("market_id") String marketId,@Field("brand") String brand,
                                @Field("ram") String ram);
//    @GET(UrlConstant.USER_VERIFY_INFO)
//    Observable<>


}

