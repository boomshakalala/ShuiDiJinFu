package tech.shuidikeji.shuidijinfu.mvp.contract;


import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.AuthSection;

public interface AuthListContract {
    interface IAuthView extends IBaseView {
        void showAuthList(List<AuthSection> authSectionList);
    }

    interface IAuthModel extends IBaseModel{
        Observable<List<AuthListPojo>> getAuthList();
    }
}
