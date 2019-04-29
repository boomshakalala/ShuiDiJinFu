package tech.shuidikeji.shuidijinfu.mvp.model;

import android.net.Uri;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.mvp.contract.EmergencyContactAuthContract;
import tech.shuidikeji.shuidijinfu.utils.PhoneUtils;

public class EmergencyContactAuthModel extends LocationModel implements EmergencyContactAuthContract.IEmergencyContactAuthModel {
    @Override
    public Observable<Object> commitEmergencyContact(String immediateName, String immediatePhone, String immediateRelation, String otherName, String otherPhone, String otherRelation) {
        return mService.commitEmergencyContact(immediateName,immediatePhone,immediateRelation,otherName,otherPhone,otherRelation);
    }

    @Override
    public Observable<String> getPhoneNumber(Uri uri) {
        return Observable.create(emitter -> {
            try {
                String phoneNumber = PhoneUtils.getPhoneNumber(uri);
                emitter.onNext(phoneNumber);
            }catch (Exception e){
                emitter.onNext("");
            }
            emitter.onComplete();

        });
    }
}
