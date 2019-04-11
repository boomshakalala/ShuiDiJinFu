package tech.shuidikeji.shuidijinfu.mvp.model;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.AuthListContract;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;

public class AuthListModel extends BaseModel implements AuthListContract.IAuthModel {
    @Override
    public Observable<List<AuthListPojo>> getAuthList() {
        return mService.getAuthList();
    }
}
