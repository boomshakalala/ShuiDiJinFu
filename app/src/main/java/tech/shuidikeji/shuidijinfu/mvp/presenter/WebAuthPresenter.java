package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.WebAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.model.WebAuthModel;
import tech.shuidikeji.shuidijinfu.pojo.WebAuthPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class WebAuthPresenter extends LocationPresenter<WebAuthContract.IWebAuthView, WebAuthContract.IWebAuthModel> {
    public WebAuthPresenter(WebAuthContract.IWebAuthView view) {
        super(view, new WebAuthModel());
    }

    public void getWebAuthUrl(String identification){
        getView().showProgressDialog();
        mModel.getWebAuthUrl(identification).compose(RxUtils.transform(getView())).subscribe(new RespObserver<WebAuthPojo>() {
            @Override
            public void onResult(WebAuthPojo data) {
                getView().dismissProgressDialog();
                String url = data.getAuth_url();
                getView().showAuthPage(url);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }
}
