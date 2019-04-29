package tech.shuidikeji.shuidijinfu.mvp.contract;


import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.pojo.WebAuthPojo;

public interface WebAuthContract {
    interface IWebAuthView extends LocationContract.ILocationView{
        void showAuthPage(String authUrl);
    }

    interface IWebAuthModel extends LocationContract.ILocationModel{
        Observable<WebAuthPojo> getWebAuthUrl(String identification);
    }
}
