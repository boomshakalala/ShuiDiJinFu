package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.StartContract;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;

public class StartModel extends BaseModel implements StartContract.IStartModel {
    public Observable<AppConfigPojo> getAppConfig(String device){
        return mService.getAppConfig(device);
    }
}
