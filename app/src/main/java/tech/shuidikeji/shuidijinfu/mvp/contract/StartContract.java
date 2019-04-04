package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;

public interface StartContract {

    interface IStartView extends IBaseView{
        void showConfigSuccess();
    }

    interface IStartModel extends IBaseModel{
        Observable<AppConfigPojo> getAppConfig(String device);
    }
}
