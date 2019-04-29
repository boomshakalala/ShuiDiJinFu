package tech.shuidikeji.shuidijinfu.mvp.model;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.AuthListContract;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitCheckPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitPojo;

public class AuthListModel extends LocationModel implements AuthListContract.IAuthModel {
    @Override
    public Observable<List<AuthListPojo>> getAuthList() {
        return mService.getAuthList();
    }

    @Override
    public Observable<SubmitCheckPojo> submitCheck() {
        return mService.submitCheck();
    }

    @Override
    public Observable<SubmitPojo> submitApply(String blackBox) {
        return mService.submitApply(blackBox);
    }
}
