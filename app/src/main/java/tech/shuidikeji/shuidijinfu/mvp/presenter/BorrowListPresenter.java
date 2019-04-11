package tech.shuidikeji.shuidijinfu.mvp.presenter;

import java.util.List;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.BorrowListContract;
import tech.shuidikeji.shuidijinfu.mvp.model.BorrowListModel;
import tech.shuidikeji.shuidijinfu.pojo.BorrowListPojo;
import tech.shuidikeji.shuidijinfu.utils.CollectionUtils;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class BorrowListPresenter extends BasePresenter<BorrowListContract.IBorrowListView, BorrowListContract.IBorrowListModel> {
    public BorrowListPresenter(BorrowListContract.IBorrowListView view) {
        super(view, new BorrowListModel());
    }

    public void getBorrowList(int page){
        getView().showLoading();
        mModel.getBorrowList(page).compose(RxUtils.transform(getView())).subscribe(new RespObserver<BorrowListPojo>() {
            @Override
            public void onResult(BorrowListPojo data) {
                if (CollectionUtils.isEmpty(data.getData()))
                    getView().showEmpty();
                else
                    getView().showBorrowList(data.getData());
                getView().hideLoading();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().hideLoading();
                getView().showError(errMsg);
            }
        });
    }
}
