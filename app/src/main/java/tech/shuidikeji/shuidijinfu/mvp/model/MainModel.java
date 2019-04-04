package tech.shuidikeji.shuidijinfu.mvp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.MainContract;
import tech.shuidikeji.shuidijinfu.utils.PhoneUtils;

public class MainModel extends BaseModel implements MainContract.IMainModel {
    @Override
    public Observable<String> postUserContacts(String data) {
        return mService.postUserContacts(data);
    }

    @Override
    public Observable<String> postUserCallLog(String data) {
        return mService.postUserCallLog(data);
    }

    @Override
    public Observable<String> postUserSms(String data) {
        return mService.postUserSmsLog(data);
    }

    @Override
    public Observable<List<HashMap<String,String>>> getUserContacts() {

        return Observable.create(emitter -> {
            List<HashMap<String,String>> result = new ArrayList<>();
            List<HashMap<String,String>> simContacts = new ArrayList<>();
            List<HashMap<String,String>> phoneContacts;
            if (PhoneUtils.hasSIMCard())
                simContacts = PhoneUtils.getSIMContacts();
            phoneContacts = PhoneUtils.getPhoneContacts();
            result.addAll(simContacts);
            result.addAll(phoneContacts);
            if (result.size()==0){
                emitter.onError(new Throwable("获取通讯录失败，请检查您的手机权限是否开启"));
            }else {
                emitter.onNext(result);
            }
            emitter.onComplete();

        });
    }

    @Override
    public Observable<List<HashMap<String, String>>> getUserCallLog() {
        return Observable.create(emitter -> {
            List<HashMap<String,String>> phoneCallLog = PhoneUtils.getPhoneCallLog();
            if (phoneCallLog.size()==0){
                emitter.onError(new Throwable("通话记录获取失败"));
            }else {
                emitter.onNext(phoneCallLog);
            }
            emitter.onComplete();

        });
    }

    @Override
    public Observable<List<HashMap<String, String>>> getUserSms() {
        return Observable.create(emitter -> {
            List<HashMap<String,String>> phoneSms = PhoneUtils.getSMS();
            if (phoneSms.size()==0){
                emitter.onError(new Throwable("短信获取失败"));
            }else {
                emitter.onNext(phoneSms);
            }
            emitter.onComplete();
        });
    }


}
