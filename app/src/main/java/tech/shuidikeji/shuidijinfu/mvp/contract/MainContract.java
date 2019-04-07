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
        Observable<String> postUserContacts(String data);
        Observable<String> postUserCallLog(String data);
        Observable<String> postUserSms(String data);

        Observable<List<HashMap<String,String>>> getUserContacts();

        Observable<List<HashMap<String,String>>> getUserCallLog();

        Observable<List<HashMap<String,String>>> getUserSms();

    }
}
