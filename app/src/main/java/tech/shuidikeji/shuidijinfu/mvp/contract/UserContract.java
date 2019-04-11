package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.MessageUnReadPojo;
import tech.shuidikeji.shuidijinfu.pojo.VerifyInfoPojo;

public interface UserContract {
    interface IUserView extends IBaseView{
        void showUserVerifyInfo(VerifyInfoPojo info);
        void showUnReadMessage(int unread);
    }

    interface IUserModel extends IBaseModel{
        Observable<VerifyInfoPojo> getUserVerifyInfo();
        Observable<MessageUnReadPojo> getMessageUnRead();
    }
}
