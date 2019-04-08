package tech.shuidikeji.shuidijinfu.base;



import com.trello.rxlifecycle2.internal.Preconditions;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;


public class BasePresenter<V extends IBaseView,M extends IBaseModel> implements IBasePresenter<V> {
    protected Reference<V> mView;
    protected M mModel;

    public BasePresenter(V view,M model) {
        Preconditions.checkNotNull(model, String.format("%s cannot be null", IBaseModel.class.getName()));
        Preconditions.checkNotNull(view, String.format("%s cannot be null", IBaseView.class.getName()));
        this.mModel = model;
        attachView(view);
    }

    @Override
    public void attachView(V view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }

    }

    @Override
    public void commitTokenKey(String tokenKey,String userId) {
        mModel.commitTokenKey(tokenKey,userId)
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onResult(Object data) {
                        LogUtil.e("提交Tokenkey成功");
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {

                    }
                });
    }

    protected V getView(){
        return mView.get();
    }

}
