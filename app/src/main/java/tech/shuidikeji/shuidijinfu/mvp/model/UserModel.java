package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.UserContract;
import tech.shuidikeji.shuidijinfu.pojo.MessageUnReadPojo;
import tech.shuidikeji.shuidijinfu.pojo.VerifyInfoPojo;

public class UserModel extends BaseModel implements UserContract.IUserModel {
    @Override
    public Observable<VerifyInfoPojo> getUserVerifyInfo() {
        return mService.getUserVerifyInfo();
    }

    @Override
    public Observable<MessageUnReadPojo> getMessageUnRead() {
        return mService.getMessageUnRead();
    }
}
