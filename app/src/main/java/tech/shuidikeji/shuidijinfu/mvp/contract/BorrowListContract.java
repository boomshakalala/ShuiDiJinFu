package tech.shuidikeji.shuidijinfu.mvp.contract;

import android.view.ViewOutlineProvider;

import java.util.List;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.base.IBaseModel;
import tech.shuidikeji.shuidijinfu.base.IBaseView;
import tech.shuidikeji.shuidijinfu.pojo.BorrowListPojo;

public interface BorrowListContract {
    interface IBorrowListView extends IBaseView {
        void showBorrowList(List<BorrowListPojo.BorrowPojo> data);
    }

    interface IBorrowListModel extends IBaseModel{
        Observable<BorrowListPojo> getBorrowList(int page);
    }
}
