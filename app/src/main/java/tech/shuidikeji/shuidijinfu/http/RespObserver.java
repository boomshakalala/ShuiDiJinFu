package tech.shuidikeji.shuidijinfu.http;

import android.app.Activity;
import android.view.View;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tech.shuidikeji.shuidijinfu.base.ActivityManager;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;
import tech.shuidikeji.shuidijinfu.widget.dialog.AlertDialog;

public abstract class RespObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T data) {
        onResult(data);
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("HttpError",e.getMessage());
        if (e instanceof ResultException){
            if (((ResultException) e).getCode() == 403100 || ((ResultException) e).getCode() == 403101){
                new AlertDialog.Builder(ActivityManager.getInstance().getCurrentActivity())
                        .setTitle("提示")
                        .setMessage(e.getMessage())
                        .setSingleButton("确定", v -> {

                        }).build().show();
                SPUtils.putString(PreferenceConstant.TOKEN,"");
                SPUtils.putString(PreferenceConstant.USER_ID,"");
            }
            onError(((ResultException) e).getCode(),e.getMessage());
        } else if (e instanceof Exception) {
            HttpThrowable httpThrowable = ThrowableHandler.handleThrowable(e);
            onError(httpThrowable.errorType,httpThrowable.message);
        } else {
            onError(HttpThrowable.UNKNOWN,"未知错误");
        }
        onComplete();
    }

    @Override
    public void onComplete() {

    }

    public abstract void onResult(T data);
    public abstract void onError(int errCode, String errMsg);
}
