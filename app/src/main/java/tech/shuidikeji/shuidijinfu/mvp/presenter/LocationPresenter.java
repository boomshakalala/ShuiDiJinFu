package tech.shuidikeji.shuidijinfu.mvp.presenter;

import com.amap.api.location.LocationManagerBase;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.LocationContract;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;

public  class LocationPresenter<V extends LocationContract.ILocationView,M extends LocationContract.ILocationModel> extends BasePresenter<V, M> {


    public LocationPresenter(V view, M model) {
        super(view, model);
    }

    public void postLocation(double lng, double lat, String marking, String device){
        mModel.postLocation(lng,lat,marking,device)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onResult(Object data) {
                        LogUtil.e("上传位置信息成功！");
                        getView().showPostLocationSuccess();
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        getView().showPostLocationFailure();
                    }
                });
    }
}
