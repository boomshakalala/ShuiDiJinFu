package tech.shuidikeji.shuidijinfu.mvp.model;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.mvp.contract.AddBankCardContract;
import tech.shuidikeji.shuidijinfu.pojo.BankCodePojo;
import tech.shuidikeji.shuidijinfu.pojo.BankListPojo;
import tech.shuidikeji.shuidijinfu.pojo.BankNamePojo;

public class AddBankCardModel extends LocationModel implements AddBankCardContract.IAddBankCardModel {


    @Override
    public Observable<List<BankListPojo>> getBankList() {
        return mService.getBankList();
    }

    @Override
    public Observable<BankNamePojo> getBankName(String cardNumber) {
        return mService.getBankName(cardNumber);
    }

    @Override
    public Observable<BankCodePojo> getBankCode(String phone, String cardNumber, String cityCode) {
        return mService.getBankCode(phone,cardNumber,cityCode);
    }

    @Override
    public Observable<Object> addBankCard(String code, String requestNo) {
        return mService.addBankCard(code,requestNo);
    }
}
