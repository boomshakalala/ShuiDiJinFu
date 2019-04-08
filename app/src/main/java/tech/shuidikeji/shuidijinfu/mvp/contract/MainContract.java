package tech.shuidikeji.shuidijinfu.mvp.contract;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;

public interface MainContract {
    interface IMainView extends LocationContract.ILocationView {

    }

    interface IMainModel extends LocationContract.ILocationModel {
        Observable<Object> postUserContacts(String data);
        Observable<Object> postUserCallLog(String data);
        Observable<Object> postUserSms(String data);

        Observable<List<HashMap<String,String>>> getUserContacts();

        Observable<List<HashMap<String,String>>> getUserCallLog();

        Observable<List<HashMap<String,String>>> getUserSms();

    }
}
