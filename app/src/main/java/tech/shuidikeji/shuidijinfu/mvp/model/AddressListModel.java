package tech.shuidikeji.shuidijinfu.mvp.model;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.AddressListContract;
import tech.shuidikeji.shuidijinfu.pojo.CityListPojo;

public class AddressListModel  extends BaseModel implements AddressListContract.IAddressListModel {
    @Override
    public Observable<List<CityListPojo>> getCityList() {
        return mService.getCityList();
    }
}
