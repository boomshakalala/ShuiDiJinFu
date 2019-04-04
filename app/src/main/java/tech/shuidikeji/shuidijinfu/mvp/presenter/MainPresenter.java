package tech.shuidikeji.shuidijinfu.mvp.presenter;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.MainContract;
import tech.shuidikeji.shuidijinfu.mvp.model.MainModel;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;

public class MainPresenter extends BasePresenter<MainContract.IMainView, MainContract.IMainModel> {
    private Gson gson;
    public MainPresenter(MainContract.IMainView view) {
        super(view, new MainModel());
        gson = new Gson();
    }

    public void getContactsAndUpload(){
        mModel.getUserContacts()
                .compose(RxUtils.transform(getView()))
                .subscribe(new Observer<List<HashMap<String, String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<HashMap<String, String>> hashMaps) {
                        postContacts(gson.toJson(hashMaps));
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getSmsAndUpload(){
        mModel.getUserSms()
                .compose(RxUtils.transform(getView()))
                .subscribe(new Observer<List<HashMap<String, String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<HashMap<String, String>> hashMaps) {
                        postSms(gson.toJson(hashMaps));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getCallLogAndUpload(){
        mModel.getUserCallLog()
                .compose(RxUtils.transform(getView()))
                .subscribe(new Observer<List<HashMap<String, String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<HashMap<String, String>> hashMaps) {
                        postCallLog(gson.toJson(hashMaps));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void postContacts(String data){
        mModel.postUserContacts(data)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<String>() {
                    @Override
                    public void onResult(String data) {
                        LogUtil.e("上传通讯录成功！");
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().showError(errMsg);
                    }
                });
    }

    private void postCallLog(String data){
        mModel.postUserCallLog(data)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<String>() {
                    @Override
                    public void onResult(String data) {
                        LogUtil.e("上传通话记录成功！");
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().showError(errMsg);
                    }
                });
    }

    private void postSms(String data){
        mModel.postUserSms(data)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<String>() {
                    @Override
                    public void onResult(String data) {
                        LogUtil.e("上传短信成功！");
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().showError(errMsg);
                    }
                });
    }
}
