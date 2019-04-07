package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.LoginContract;
import tech.shuidikeji.shuidijinfu.mvp.model.LoginModel;
import tech.shuidikeji.shuidijinfu.pojo.LoginPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView, LoginContract.ILoginModel> {
    public LoginPresenter(LoginContract.ILoginView view) {
        super(view, new LoginModel());
    }

    public void getCaptcha(String phone){
        getView().showProgressDialog();
        mModel.getCaptcha(phone)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<String>() {
                    @Override
                    public void onResult(String data) {
                        getView().dismissProgressDialog();
                        getView().showGetCaptchaSuccess();
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().dismissProgressDialog();
                        getView().showError(errMsg);
                    }
                });
    }

    public void login(String phone, String captcha, String tokenKey, double lng,
                      double lat, String registerType, String device, String deviceSn,
                      String marketId, String brand, String ram){
        getView().showProgressDialog();
        mModel.login(phone,captcha,tokenKey,lng,lat,registerType,device,deviceSn,marketId,brand,ram)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<LoginPojo>() {
                    @Override
                    public void onResult(LoginPojo data) {
                        SPUtils.putString(PreferenceConstant.TOKEN,data.getToken());
                        SPUtils.putString(PreferenceConstant.USER_ID,data.getUser_id());
                        getView().dismissProgressDialog();
                        getView().showLoginSuccess();
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().dismissProgressDialog();
                        getView().showError(errMsg);
                    }
                });
    }
}
