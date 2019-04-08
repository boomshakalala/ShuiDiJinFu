package tech.shuidikeji.shuidijinfu;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import tech.shuidikeji.shuidijinfu.base.ActivityManager;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.utils.AppUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;


public class App extends Application implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        SPUtils.init(PreferenceConstant.PREFERENCE_NAME,this);
        LogUtil.init(BuildConfig.DEBUG);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityManager.getInstance().setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
