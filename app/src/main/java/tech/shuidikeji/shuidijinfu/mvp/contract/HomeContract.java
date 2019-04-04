package tech.shuidikeji.shuidijinfu.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.pojo.NotificationPojo;

public interface HomeContract {
    interface IHomeView extends IBaseView{
        void showIndex(IndexPojo data);
        void showNotification(ArrayList<String> data);
        void showIndexDialog(String url);
    }

    interface IHomeModel extends IBaseModel {
        Observable<IndexPojo> getIndexDefault();
        Observable<IndexPojo> getIndexLogin();
        Observable<IndexPojo> getIndexUnderReview();
        Observable<NotificationPojo> getNotification();
        Observable<NotificationPojo> getLoginActivityUrl();

    }

}
