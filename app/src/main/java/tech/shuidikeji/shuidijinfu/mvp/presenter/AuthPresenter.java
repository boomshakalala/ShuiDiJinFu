package tech.shuidikeji.shuidijinfu.mvp.presenter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.AuthListContract;
import tech.shuidikeji.shuidijinfu.mvp.model.AuthListModel;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.AuthSection;
import tech.shuidikeji.shuidijinfu.pojo.SubmitCheckPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitPojo;
import tech.shuidikeji.shuidijinfu.utils.CollectionUtils;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class AuthPresenter extends LocationPresenter<AuthListContract.IAuthView, AuthListContract.IAuthModel> {
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
                    List<AuthSection> sections1 = new ArrayList<>();
                    sections.add(new AuthSection(true,"必测项目"));
                    for (AuthListPojo datum : data) {
                        if (datum.getIs_opertional() == 1)
                            sections.add(new AuthSection(datum));
                    }
                    for (AuthListPojo datum : data) {
                        if (datum.getIs_opertional() == 0)
                            sections1.add(new AuthSection(datum));
                    }
                    if (!CollectionUtils.isEmpty(sections1)){
                        sections.add(new AuthSection(true,"选测项目"));
                        sections.addAll(sections1);
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

    public void submitCheck(){
        getView().showProgressDialog();
        mModel.submitCheck().compose(RxUtils.transform(getView())).subscribe(new RespObserver<SubmitCheckPojo>() {
            @Override
            public void onResult(SubmitCheckPojo data) {
                getView().dismissProgressDialog();
                getView().showSubmitCheck(data);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    public void submitApply(String blackBox){
        getView().showProgressDialog();
        mModel.submitApply(blackBox).compose(RxUtils.transform(getView())).subscribe(new RespObserver<SubmitPojo>() {
            @Override
            public void onResult(SubmitPojo data) {
                getView().dismissProgressDialog();
                getView().showSubmitSuccess(data);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
            }

            @Override
            public void onErrorData(int errCode, String errMsg, String errorData) {
                SubmitPojo data = new Gson().fromJson(errorData,SubmitPojo.class);
                if (errCode == 2001){
                    getView().showRefuse(data);
                }else if (errCode == 2002){
                    getView().showPendingDialog(data);
                }else {
                    getView().showError(errMsg);
                }
            }
        });
    }
}
