package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;

public interface IdCardAuthContract {
    interface IdCardAuthView extends LocationContract.ILocationView {
        void showUploadSuccess(IdCardPojo data);
        void showCommitSuccess();
    }

    interface  IdCardAuthModel extends LocationContract.ILocationModel {
        Observable<IdCardPojo> uploadIdCard(String frontImage,String backImage,String faceImage);
        Observable<Object> commitIdCard(String name,String sex,String nation,String birth,String address,String number);
    }
}
