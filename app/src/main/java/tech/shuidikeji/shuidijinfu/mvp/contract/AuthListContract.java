package tech.shuidikeji.shuidijinfu.mvp.contract;


import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.AuthSection;
import tech.shuidikeji.shuidijinfu.pojo.SubmitCheckPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitPojo;

public interface AuthListContract {
    interface IAuthView extends LocationContract.ILocationView {
        void showAuthList(List<AuthSection> authSectionList);
        void showSubmitCheck(SubmitCheckPojo data);
        void showSubmitSuccess(SubmitPojo data);
        void showPendingDialog(SubmitPojo data);
        void showRefuse(SubmitPojo data);
    }

    interface IAuthModel extends LocationContract.ILocationModel {
        Observable<List<AuthListPojo>> getAuthList();
        Observable<SubmitCheckPojo> submitCheck();
        Observable<SubmitPojo> submitApply(String blackBox);
    }
}
