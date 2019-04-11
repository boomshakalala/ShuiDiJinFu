package tech.shuidikeji.shuidijinfu.mvp.contract;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.BankCardPojo;

public interface BankCardContract {
    interface IBankCardView extends IBaseView {
        void showBankCard(BankCardPojo data);
    }

    interface IBankCardModel extends IBaseModel{
        Observable<BankCardPojo> getBankCard();
    }
}
