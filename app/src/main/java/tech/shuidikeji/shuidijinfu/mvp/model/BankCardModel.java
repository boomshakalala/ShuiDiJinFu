package tech.shuidikeji.shuidijinfu.mvp.model;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.BankCardContract;
import tech.shuidikeji.shuidijinfu.pojo.BankCardPojo;

public class BankCardModel extends BaseModel implements BankCardContract.IBankCardModel {
    @Override
    public Observable<BankCardPojo> getBankCard() {
        return mService.getBankCard();
    }
}
