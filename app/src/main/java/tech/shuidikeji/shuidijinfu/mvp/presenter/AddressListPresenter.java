package tech.shuidikeji.shuidijinfu.mvp.presenter;

import java.util.List;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.AddressListContract;
import tech.shuidikeji.shuidijinfu.mvp.model.AddressListModel;
import tech.shuidikeji.shuidijinfu.pojo.CityListPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class AddressListPresenter extends BasePresenter<AddressListContract.IAddressListView, AddressListContract.IAddressListModel> {
    public AddressListPresenter(AddressListContract.IAddressListView view) {
        super(view, new AddressListModel());
    }

    public void getCityList(){
        getView().showProgressDialog();
        mModel.getCityList().compose(RxUtils.transform(getView())).subscribe(new RespObserver<List<CityListPojo>>() {
            @Override
            public void onResult(List<CityListPojo> data) {
                getView().dismissProgressDialog();
                getView().showAddressList(data);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }
}
