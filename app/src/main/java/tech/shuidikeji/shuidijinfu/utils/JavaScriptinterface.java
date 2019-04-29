package tech.shuidikeji.shuidijinfu.utils;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.just.agentweb.AgentWeb;

public class JavaScriptinterface {
    private Activity activity;

    private AgentWeb agent;

    public JavaScriptinterface(Activity activity, AgentWeb agent) {
        this.activity = activity;
        this.agent = agent;
    }

    public JavaScriptinterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
        public void backApp() {
            activity.finish();
        }
    }