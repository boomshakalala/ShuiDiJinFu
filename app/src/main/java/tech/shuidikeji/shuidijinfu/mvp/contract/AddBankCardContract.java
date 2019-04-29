package tech.shuidikeji.shuidijinfu.mvp.contract;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.pojo.BankCodePojo;
import tech.shuidikeji.shuidijinfu.pojo.BankListPojo;
import tech.shuidikeji.shuidijinfu.pojo.BankNamePojo;

public interface AddBankCardContract {

    interface IAddBankCardView extends LocationContract.ILocationView {
        void showBankList(List<BankListPojo> data);
        void showGetCodeSuccess(String data);
        void showBankName(String data);
        void showCommitSuccess();
    }

    interface IAddBankCardModel extends LocationContract.ILocationModel {
        Observable<List<BankListPojo>> getBankList();
        Observable<BankNamePojo> getBankName(String cardNumber);
        Observable<BankCodePojo> getBankCode(String phone,String cardNumber,String cityCode);
        Observable<Object> addBankCard(String code,String requestNo);

    }
}
