package tech.shuidikeji.shuidijinfu.mvp.model;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.BaseModel;
import tech.shuidikeji.shuidijinfu.mvp.contract.BorrowListContract;
import tech.shuidikeji.shuidijinfu.pojo.BorrowListPojo;

public class BorrowListModel extends BaseModel implements BorrowListContract.IBorrowListModel {
    @Override
    public Observable<BorrowListPojo> getBorrowList(int page) {
        return mService.getBorrowList(page);
    }
}
