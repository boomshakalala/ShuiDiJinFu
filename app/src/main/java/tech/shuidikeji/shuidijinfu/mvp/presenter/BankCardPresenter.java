package tech.shuidikeji.shuidijinfu.mvp.presenter;

import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.BankCardContract;
import tech.shuidikeji.shuidijinfu.mvp.model.BankCardModel;
import tech.shuidikeji.shuidijinfu.pojo.BankCardPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class BankCardPresenter extends BasePresenter<BankCardContract.IBankCardView, BankCardContract.IBankCardModel> {
    public BankCardPresenter(BankCardContract.IBankCardView view) {
        super(view, new BankCardModel());
    }

    public void getBankCard(){
        getView().showProgressDialog();
        mModel.getBankCard().compose(RxUtils.transform(getView())).subscribe(new RespObserver<BankCardPojo>() {
            @Override
            public void onResult(BankCardPojo data) {
                getView().dismissProgressDialog();
                getView().showBankCard(data);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }
}
