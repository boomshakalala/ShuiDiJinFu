package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.HomeContract;
import tech.shuidikeji.shuidijinfu.mvp.model.HomeModel;
import tech.shuidikeji.shuidijinfu.pojo.HomeDialgPojo;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.pojo.NotificationPojo;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;

public class HomePresenter extends BasePresenter<HomeContract.IHomeView,HomeContract.IHomeModel> {
    private boolean isLogin ;
    private boolean isOverReview;
    public HomePresenter(HomeContract.IHomeView view) {
        super(view, new HomeModel());
        isLogin = CommonUtils.isLogin();
        isOverReview = SPUtils.getInt(PreferenceConstant.CHECK_STATUS,0) == 2;
    }

    public void getIndex(){
        getView().showProgressDialog();
        if (isOverReview){
            if (isLogin){
                mModel.getIndexLogin()
                        .compose(RxUtils.transform(getView()))
                        .subscribe(new RespObserver<IndexPojo>() {
                            @Override
                            public void onResult(IndexPojo data) {
                                getView().showIndex(data);
                                getView().dismissProgressDialog();
                            }

                            @Override
                            public void onError(int errCode, String errMsg) {
                                getView().showError(errMsg);
                                getView().dismissProgressDialog();
                            }
                });
            }else {
                mModel.getIndexDefault()
                        .compose(RxUtils.transform(getView()))
                        .subscribe(new RespObserver<IndexPojo>() {
                            @Override
                            public void onResult(IndexPojo data) {
                                getView().showIndex(data);
                                getView().dismissProgressDialog();
                            }

                            @Override
                            public void onError(int errCode, String errMsg) {
                                getView().showError(errMsg);
                                getView().dismissProgressDialog();
                            }
                        });
            }
        }else {
            mModel.getIndexUnderReview()
                    .compose(RxUtils.transform(getView()))
                    .subscribe(new RespObserver<IndexPojo>() {
                        @Override
                        public void onResult(IndexPojo data) {
                            getView().showIndex(data);
                            getView().dismissProgressDialog();
                        }

                        @Override
                        public void onError(int errCode, String errMsg) {
                            getView().showError(errMsg);
                            getView().dismissProgressDialog();
                        }
                    });
        }
    }

    public void getNotification(){
        mModel.getNotification()
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<NotificationPojo>() {
                    @Override
                    public void onResult(NotificationPojo data) {
                        SPUtils.putString(PreferenceConstant.SERVICE_PHONE,data.getCompany().getService_phone());
                        if (isOverReview){
                            getView().showNotification(data.getNotification());
                            if (!isLogin && SPUtils.getInt(PreferenceConstant.HOME_DIALOG_STATUS) == 0)
                                getView().showIndexDialog(data.getActivity_url());
                        }
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {

                    }
                });
    }

    public void getLoginActivityUrl(int status){
        if (!isOverReview || !isLogin)
            return;
        mModel.getLoginActivityUrl(status)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<HomeDialgPojo>() {
                    @Override
                    public void onResult(HomeDialgPojo data) {
                        if (data.getStatus() == 1){
                            getView().showIndexDialog(data.getActivity_url());
                        }
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().showError(errMsg);
                    }
                });
    }
}
