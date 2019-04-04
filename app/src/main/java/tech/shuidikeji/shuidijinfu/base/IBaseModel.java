package tech.shuidikeji.shuidijinfu.base;


import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.http.ApiEngine;
import tech.shuidikeji.shuidijinfu.http.ApiService;

public interface IBaseModel {
    ApiService mService = ApiEngine.getInstance().getApiService();

    Observable<String> commitTokenKey(String tokenKey, String userId);


}
