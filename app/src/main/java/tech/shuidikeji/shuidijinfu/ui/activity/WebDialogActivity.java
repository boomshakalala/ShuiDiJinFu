package tech.shuidikeji.shuidijinfu.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;


import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.BuildConfig;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseActivity;
import tech.shuidikeji.shuidijinfu.utils.JavaScriptinterface;

/**
 * 首页弹窗
 */
public class WebDialogActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

    private String url;

    public static void launcher(Context context,String url){
        Intent intent = new Intent(context,WebDialogActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        setContentView(R.layout.activity_web_dialog);
        mStatusBar.setBackgroundColor(Color.parseColor("#88000000"));
        showProgressDialog();
        mWebView.setBackgroundColor(getResourceColor(android.R.color.transparent));
        mWebView.getBackground().setAlpha(0);
        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        //设置支持访问文件
        settings.setAllowFileAccess(true);
        // 使用localStorage则必须打开
        settings.setGeolocationEnabled(true);
        //设置webview支持javascript脚本
        settings.setJavaScriptEnabled(true);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        //设置与JS交互
        mWebView.addJavascriptInterface(new JavaScriptinterface(this), "App");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                try {
                    //如果是普通地址
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    } else {
                        view.loadUrl(request.toString());
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        });

        //检测进度
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 90) {
                    dismissProgressDialog();
                } else {

                }
            }
        });

        mWebView.loadUrl(url);

    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
    }


}
