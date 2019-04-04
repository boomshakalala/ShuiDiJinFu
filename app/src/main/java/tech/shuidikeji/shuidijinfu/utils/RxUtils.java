package tech.shuidikeji.shuidijinfu.utils;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tech.shuidikeji.shuidijinfu.base.IBaseView;

public class RxUtils {

    public static <T> ObservableTransformer<T, T> transform(final IBaseView view) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.bindToLifecycle());

    }


}
