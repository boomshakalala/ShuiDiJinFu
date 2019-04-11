package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.UserContract;
import tech.shuidikeji.shuidijinfu.mvp.model.UserModel;
import tech.shuidikeji.shuidijinfu.pojo.MessageUnReadPojo;
import tech.shuidikeji.shuidijinfu.pojo.VerifyInfoPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class UserPresenter extends BasePresenter<UserContract.IUserView, UserContract.IUserModel> {
    public UserPresenter(UserContract.IUserView view) {
        super(view, new UserModel());
    }

    public void getUserVerifyInfo(){
        mModel.getUserVerifyInfo().compose(RxUtils.transform(getView())).subscribe(new RespObserver<VerifyInfoPojo>() {
            @Override
            public void onResult(VerifyInfoPojo data) {
                getView().showUserVerifyInfo(data);
            }

            @Override
            public void onError(int errCode, String errMsg) {

            }
        });
    }

    public void getUnreadMessage(){
        mModel.getMessageUnRead().compose(RxUtils.transform(getView())).subscribe(new RespObserver<MessageUnReadPojo>() {
            @Override
            public void onResult(MessageUnReadPojo data) {
                getView().showUnReadMessage(data.getUnread());
            }

            @Override
            public void onError(int errCode, String errMsg) {

            }
        });
    }
}
