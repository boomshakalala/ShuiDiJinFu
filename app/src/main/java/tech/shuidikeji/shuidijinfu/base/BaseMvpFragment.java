package tech.shuidikeji.shuidijinfu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseView {

    protected P mPresenter;

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    protected abstract P getPresenter();


}
