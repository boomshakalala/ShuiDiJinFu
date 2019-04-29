package tech.shuidikeji.shuidijinfu.mvp.presenter;

import android.net.Uri;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.EmergencyContactAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.model.EmergencyContactAuthModel;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class EmergencyContactAuthPresenter extends LocationPresenter<EmergencyContactAuthContract.IEmergencyContactAuthView, EmergencyContactAuthContract.IEmergencyContactAuthModel> {
    public EmergencyContactAuthPresenter(EmergencyContactAuthContract.IEmergencyContactAuthView view) {
        super(view, new EmergencyContactAuthModel());
    }

    public void commitEmergencyContact(String immediateName, String immediatePhone, String immediateRelation, String otherName, String otherPhone, String otherRelation){
        getView().showProgressDialog();
        mModel.commitEmergencyContact(immediateName,immediatePhone,immediateRelation,otherName,otherPhone,otherRelation).compose(RxUtils.transform(getView())).subscribe(new RespObserver<Object>() {
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

    public void getPhoneNumber(Uri uri){
        getView().showProgressDialog();
        mModel.getPhoneNumber(uri).compose(RxUtils.transform(getView())).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                getView().showPhoneNumber(s);
            }

            @Override
            public void onError(Throwable e) {
                getView().dismissProgressDialog();
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }
}
