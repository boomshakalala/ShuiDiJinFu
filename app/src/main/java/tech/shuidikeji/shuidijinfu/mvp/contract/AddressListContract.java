package tech.shuidikeji.shuidijinfu.mvp.contract;


import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.CityListPojo;

public interface AddressListContract {
    interface IAddressListView extends IBaseView {
        void showAddressList(List<CityListPojo> data);
    }

    interface IAddressListModel extends IBaseModel {
        Observable<List<CityListPojo>> getCityList();
    }
}
