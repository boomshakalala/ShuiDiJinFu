package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.IdCardAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.model.IdCardAuthModel;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class IdCardAuthPresenter extends LocationPresenter<IdCardAuthContract.IdCardAuthView, IdCardAuthContract.IdCardAuthModel> {

    public IdCardAuthPresenter(IdCardAuthContract.IdCardAuthView view) {
        super(view, new IdCardAuthModel());
    }

    public void uploadIdCard(String frontImage,String backImage,String faceImage){
        getView().showProgressDialog();
        mModel.uploadIdCard(frontImage,backImage,faceImage).compose(RxUtils.transform(getView())).subscribe(new RespObserver<IdCardPojo>() {
            @Override
            public void onResult(IdCardPojo data) {
                getView().showUploadSuccess(data);
                getView().dismissProgressDialog();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    public void commitIdCard(String name, String sex, String nation, String birth, String address, String number){
        getView().showProgressDialog();
        mModel.commitIdCard(name,sex,nation,birth,address,number).compose(RxUtils.transform(getView())).subscribe(new RespObserver<Object>() {
            @Override
            public void onResult(Object data) {
                getView().showCommitSuccess();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }
}
