package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.FaceLiveAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.contract.IdCardAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.model.FaceLiveAuthModel;
import tech.shuidikeji.shuidijinfu.mvp.model.IdCardAuthModel;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class FaceLiveAuthPresenter extends LocationPresenter<FaceLiveAuthContract.IFaceLiveAuthView, FaceLiveAuthContract.IFaceLiveAuthModel> {

    public FaceLiveAuthPresenter(FaceLiveAuthContract.IFaceLiveAuthView view) {
        super(view, new FaceLiveAuthModel());
    }

    public void uploadFaceImage(String faceImage){
        getView().showProgressDialog();
        mModel.uploadFaceImage(faceImage).compose(RxUtils.transform(getView())).subscribe(new RespObserver<Object>() {
            @Override
            public void onResult(Object data) {
                getView().showUploadSuccess();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }


}
