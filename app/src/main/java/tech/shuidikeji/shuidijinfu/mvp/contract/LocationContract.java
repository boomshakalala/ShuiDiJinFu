package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;

public interface LocationContract {
    interface ILocationView extends IBaseView {
        void showPostLocationSuccess();
    }

    interface ILocationModel extends IBaseModel {
        Observable<String> postLocation(double lng,double lat,String marking,String device);
    }
}
