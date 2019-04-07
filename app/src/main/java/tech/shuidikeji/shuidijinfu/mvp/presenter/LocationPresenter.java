package tech.shuidikeji.shuidijinfu.mvp.presenter;

import com.amap.api.location.LocationManagerBase;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.LocationContract;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public  class LocationPresenter<V extends LocationContract.ILocationView,M extends LocationContract.ILocationModel> extends BasePresenter<V, M> {


    public LocationPresenter(V view, M model) {
        super(view, model);
    }

    public void postLocation(double lng, double lat, String marking, String device){
        mModel.postLocation(lng,lat,marking,device)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<String>() {
                    @Override
                    public void onResult(String data) {
                        getView().showPostLocationSuccess();
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {

                    }
                });
    }
}
