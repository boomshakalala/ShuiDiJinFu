package tech.shuidikeji.shuidijinfu.mvp.contract;

import android.net.Uri;

import io.reactivex.Observable;

public interface EmergencyContactAuthContract {
    interface IEmergencyContactAuthView extends LocationContract.ILocationView{
        void showCommitSuccess();
        void showPhoneNumber(String phone);
    }

    interface IEmergencyContactAuthModel extends LocationContract.ILocationModel{
        Observable<Object> commitEmergencyContact(String immediateName,String immediatePhone,String immediateRelation,String otherName,String otherPhone,String otherRelation);
        Observable<String> getPhoneNumber(Uri uri);
    }
}
