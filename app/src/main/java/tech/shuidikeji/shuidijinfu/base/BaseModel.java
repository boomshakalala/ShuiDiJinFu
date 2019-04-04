package tech.shuidikeji.shuidijinfu.base;

import io.reactivex.Observable;

public class BaseModel implements IBaseModel {
    @Override
    public Observable<String> commitTokenKey(String tokenKey, String userId) {
        return mService.commitTokenKey(tokenKey,userId);
    }
}
