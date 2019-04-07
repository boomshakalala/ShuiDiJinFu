package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.LoginPojo;

public interface LoginContract {
    interface ILoginView extends IBaseView {
        void showGetCaptchaSuccess();

        void showLoginSuccess();
    }

    interface ILoginModel extends IBaseModel {
        Observable<String> getCaptcha(String phone);
        Observable<LoginPojo> login(String phone,String captcha,String tokenKey,double lng,double lat
                ,String registerType,String device,String deviceSn,String marketId,String brand,String ram);
    }
}
