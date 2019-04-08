package tech.shuidikeji.shuidijinfu.mvp.presenter;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.MainContract;
import tech.shuidikeji.shuidijinfu.mvp.model.MainModel;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;

public class MainPresenter extends LocationPresenter<MainContract.IMainView, MainContract.IMainModel> {
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
                        HashMap<String,Object> data = new HashMap<>();
                        data.put("userId", SPUtils.getString(PreferenceConstant.USER_ID));
                        data.put("data",hashMaps);
                        postContacts(gson.toJson(data));
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
                        HashMap<String,Object> data = new HashMap<>();
                        data.put("data",hashMaps);
                        postSms(gson.toJson(data));
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
                        HashMap<String,Object> data = new HashMap<>();
                        data.put("data",hashMaps);
                        postCallLog(gson.toJson(data));
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
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onResult(Object data) {
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
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onResult(Object data) {
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
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onResult(Object data) {
                        LogUtil.e("上传短信成功！");
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().showError(errMsg);
                    }
                });
    }
}
