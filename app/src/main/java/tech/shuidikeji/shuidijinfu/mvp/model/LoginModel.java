package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.LoginContract;
import tech.shuidikeji.shuidijinfu.pojo.LoginPojo;

public class LoginModel extends BaseModel implements LoginContract.ILoginModel {
    @Override
    public Observable<String> getCaptcha(String phone) {
        return mService.getCaptcha(phone);
    }

    @Override
    public Observable<LoginPojo> login(String phone, String captcha, String tokenKey, double lng,
                                       double lat, String registerType, String device, String deviceSn,
                                       String marketId, String brand, String ram) {
        return mService.login(phone,captcha,tokenKey,lng,lat,registerType,device,deviceSn,marketId,brand,ram);
    }
}
