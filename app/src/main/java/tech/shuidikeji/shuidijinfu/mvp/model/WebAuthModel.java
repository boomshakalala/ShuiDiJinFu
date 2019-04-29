package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.mvp.contract.WebAuthContract;
import tech.shuidikeji.shuidijinfu.pojo.WebAuthPojo;

public class WebAuthModel extends LocationModel implements WebAuthContract.IWebAuthModel {
    @Override
    public Observable<WebAuthPojo> getWebAuthUrl(String identification) {
        return mService.getWebAuthUrl(identification);
    }
}
