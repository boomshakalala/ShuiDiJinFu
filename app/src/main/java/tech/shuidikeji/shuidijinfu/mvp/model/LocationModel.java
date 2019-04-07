package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.LocationContract;

public  class LocationModel extends BaseModel implements LocationContract.ILocationModel {
    @Override
    public Observable<String> postLocation(double lng, double lat, String marking, String device) {
        return mService.postUserLocation(lng,lat,marking,device);
    }
}
