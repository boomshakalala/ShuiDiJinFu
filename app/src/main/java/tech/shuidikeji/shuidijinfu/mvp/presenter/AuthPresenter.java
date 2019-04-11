package tech.shuidikeji.shuidijinfu.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.AuthListContract;
import tech.shuidikeji.shuidijinfu.mvp.model.AuthListModel;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.AuthSection;
import tech.shuidikeji.shuidijinfu.utils.CollectionUtils;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class AuthPresenter extends BasePresenter<AuthListContract.IAuthView, AuthListContract.IAuthModel> {
    public AuthPresenter(AuthListContract.IAuthView view) {
        super(view, new AuthListModel());
    }

    public void getAuthList(){
        getView().showLoading();
        mModel.getAuthList().compose(RxUtils.transform(getView())).subscribe(new RespObserver<List<AuthListPojo>>() {
            @Override
            public void onResult(List<AuthListPojo> data) {
                if (CollectionUtils.isEmpty(data)){
                    getView().showEmpty();
                } else{
                    List<AuthSection> sections = new ArrayList<>();
                    sections.add(new AuthSection(true,"必测项目"));
                    for (AuthListPojo datum : data) {
                        if (datum.getIs_opertional() == 1)
                            sections.add(new AuthSection(datum));
                    }
                    sections.add(new AuthSection(true,"选测项目"));
                    for (AuthListPojo datum : data) {
                        if (datum.getIs_opertional() == 0)
                            sections.add(new AuthSection(datum));
                    }
                    getView().showAuthList(sections);
                }
                getView().hideLoading();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().hideLoading();
                getView().showError(errMsg);
            }
        });
    }
}
