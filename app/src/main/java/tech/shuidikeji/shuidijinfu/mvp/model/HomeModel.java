package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.HomeContract;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.pojo.NotificationPojo;

public class HomeModel extends BaseModel implements HomeContract.IHomeModel {

    @Override
    public Observable<IndexPojo> getIndexDefault() {
        return mService.getIndexDefault();
    }

    @Override
    public Observable<IndexPojo> getIndexLogin() {
        return mService.getIndexLogin();
    }

    @Override
    public Observable<IndexPojo> getIndexUnderReview() {
        return mService.getIndexUnderReview();
    }

    @Override
    public Observable<NotificationPojo> getNotification() {
        return mService.getNotification();
    }

    @Override
    public Observable<NotificationPojo> getLoginActivityUrl() {
        return null;
    }
}
