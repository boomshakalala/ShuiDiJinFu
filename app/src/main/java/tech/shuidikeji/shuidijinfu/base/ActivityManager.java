package tech.shuidikeji.shuidijinfu.base;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class ActivityManager {

    private static ActivityManager mInstance = new ActivityManager();

    private WeakReference<Activity> mCurrentActivityWeakRef;

    private ActivityManager(){

    }

    public static ActivityManager getInstance(){
        return mInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (mCurrentActivityWeakRef != null) {
            currentActivity = mCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivityWeakRef = new WeakReference<>(activity);
    }


}
