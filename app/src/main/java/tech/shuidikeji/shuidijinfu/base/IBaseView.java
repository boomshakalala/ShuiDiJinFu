package tech.shuidikeji.shuidijinfu.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface IBaseView {

    /**
     * RxJava生命周期绑定
     */
    <T> LifecycleTransformer<T> bindToLifecycle();

    /**
     * 显示加载中视图
     */
    void showLoading();

    /**
     * 隐藏加载中视图
     */
    void hideLoading();

    /**
     * 显示空视图
     */
    void showEmpty();

    /**
     * 显示错误视图
     * @param errMsg 错误信息
     */
    void showError(String errMsg);

    /**
     * 显示加载中对话框
     */
    void showProgressDialog();

    /**
     * 隐藏加载中对话框
     */
    void dismissProgressDialog();
}
