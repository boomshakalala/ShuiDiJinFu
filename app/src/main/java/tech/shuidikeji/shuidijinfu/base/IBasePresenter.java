package tech.shuidikeji.shuidijinfu.base;

public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();

    void commitTokenKey(String tokenKey,String userId);
}
