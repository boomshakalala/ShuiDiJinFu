package tech.shuidikeji.shuidijinfu.utils;

import android.app.Activity;
import android.webkit.JavascriptInterface;

public class JavaScriptinterface {
    private Activity activity;

    public JavaScriptinterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
        public void backApp() {
            activity.finish();
        }
    }