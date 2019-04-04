package tech.shuidikeji.shuidijinfu.http;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tech.shuidikeji.shuidijinfu.constants.UrlConstant;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.pojo.NotificationPojo;

public interface ApiService {
    @GET(UrlConstant.LOGOUT_APPELEMENTS)
    Observable<AppConfigPojo> getAppConfig(@Query("device")String device);

    @FormUrlEncoded
    @POST(UrlConstant.LOGIN_SET_TOKEN_KEY)
    Observable<String> commitTokenKey(@Field("token_key")String tokenKey,@Field("user_id")String userId);

    @FormUrlEncoded
    @POST(UrlConstant.USER_CONTACTS)
    Observable<String> postUserContacts(@Field("data") String data);

    @FormUrlEncoded
    @POST(UrlConstant.USER_CALL_LOG)
    Observable<String> postUserCallLog(@Field("data") String data);

    @FormUrlEncoded
    @POST(UrlConstant.USER_SMS_LOG)
    Observable<String> postUserSmsLog(@Field("data") String data);

    @GET(UrlConstant.HOME_INIT)
    Observable<IndexPojo> getIndexLogin();

    @GET(UrlConstant.NO_LOGIN_INDEX_DEFAULT)
    Observable<IndexPojo> getIndexDefault();

    @GET(UrlConstant.NO_LOGIN_PUT_AWAY_INDEX_DEFAULT)
    Observable<IndexPojo> getIndexUnderReview();

    @GET(UrlConstant.NOTIFICATION)
    Observable<NotificationPojo> getNotification();

}

