package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.mvp.contract.FaceLiveAuthContract;

public class FaceLiveAuthModel extends LocationModel implements FaceLiveAuthContract.IFaceLiveAuthModel {
    @Override
    public Observable<Object> uploadFaceImage(String faceImage) {
        return mService.uploadFaceImage(faceImage);
    }
}
