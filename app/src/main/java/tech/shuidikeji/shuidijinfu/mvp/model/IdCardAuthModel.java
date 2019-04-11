package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.mvp.contract.IdCardAuthContract;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;

public class IdCardAuthModel extends LocationModel implements IdCardAuthContract.IdCardAuthModel {
    @Override
    public Observable<IdCardPojo> uploadIdCard(String frontImage, String backImage, String faceImage) {
        return mService.uploadIdCard(frontImage,backImage,faceImage);
    }

    @Override
    public Observable<Object> commitIdCard(String name, String sex, String nation, String birth, String address, String number) {
        return mService.commitIdCard(name,sex,nation,birth,address,number);
    }
}
