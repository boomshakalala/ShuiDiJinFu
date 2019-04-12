package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;

public interface FaceLiveAuthContract {
    interface IFaceLiveAuthView extends LocationContract.ILocationView {
        void showUploadSuccess();
    }

    interface  IFaceLiveAuthModel extends LocationContract.ILocationModel {
        Observable<Object> uploadFaceImage( String faceImage);
    }
}
