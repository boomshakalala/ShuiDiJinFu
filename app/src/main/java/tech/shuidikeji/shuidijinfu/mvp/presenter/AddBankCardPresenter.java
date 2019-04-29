package tech.shuidikeji.shuidijinfu.mvp.presenter;


import java.util.List;

import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.AddBankCardContract;
import tech.shuidikeji.shuidijinfu.mvp.model.AddBankCardModel;
import tech.shuidikeji.shuidijinfu.pojo.BankCodePojo;
import tech.shuidikeji.shuidijinfu.pojo.BankListPojo;
import tech.shuidikeji.shuidijinfu.pojo.BankNamePojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class AddBankCardPresenter extends LocationPresenter<AddBankCardContract.IAddBankCardView,AddBankCardContract.IAddBankCardModel> {
    public AddBankCardPresenter(AddBankCardContract.IAddBankCardView view) {
        super(view, new AddBankCardModel());
    }

    public void getBankList(){
        getView().showProgressDialog();
        mModel.getBankList().compose(RxUtils.transform(getView())).subscribe(new RespObserver<List<BankListPojo>>() {
            @Override
            public void onResult(List<BankListPojo> data) {
                getView().showBankList(data);
                getView().dismissProgressDialog();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    public void getBankName(String cardNumber){
        getView().showProgressDialog();
        mModel.getBankName(cardNumber).compose(RxUtils.transform(getView())).subscribe(new RespObserver<BankNamePojo>() {
            @Override
            public void onResult(BankNamePojo data) {
                getView().dismissProgressDialog();
                getView().showBankName(data.getBank_name());
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    public void getBankCode(String phone,String cardNumber,String cityCode){
        getView().showProgressDialog();
        mModel.getBankCode(phone,cardNumber,cityCode).compose(RxUtils.transform(getView())).subscribe(new RespObserver<BankCodePojo>() {
            @Override
            public void onResult(BankCodePojo data) {
                getView().dismissProgressDialog();
                getView().showGetCodeSuccess(data.getRequestno());
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    public void addBankCard(String code,String requestNo){
        getView().showProgressDialog();
        mModel.addBankCard(code,requestNo).compose(RxUtils.transform(getView())).subscribe(new RespObserver<Object>() {
            @Override
            public void onResult(Object data) {
                getView().showCommitSuccess();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }
}
