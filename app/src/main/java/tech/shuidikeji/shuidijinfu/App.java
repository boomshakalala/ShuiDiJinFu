package tech.shuidikeji.shuidijinfu;

import android.app.Application;

import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.utils.AppUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        SPUtils.init(PreferenceConstant.PREFERENCE_NAME,this);
        LogUtil.init(BuildConfig.DEBUG);
    }
}
