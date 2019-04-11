package tech.shuidikeji.shuidijinfu.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URISyntaxException;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseActivity;
import tech.shuidikeji.shuidijinfu.utils.JavaScriptinterface;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.web_view)
    WebView mWebView;
    private String title;
    private String url;


    public static void launcher(Context context,String title,String url){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        if (TextUtils.isEmpty(title))
            getTitleBar().hide();
        else
            getTitleBar().setTitle(title).showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        //设置支持访问文件
        settings.setAllowFileAccess(true);
        // 使用localStorage则必须打开
        settings.setGeolocationEnabled(true);
        //设置webview支持javascript脚本
        settings.setJavaScriptEnabled(true);
        //设置与JS交互
        mWebView.addJavascriptInterface(new JavaScriptinterface(this), "App");
        //设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.loadUrl(url);
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

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (shouldOverrideUrlLoadingByAppInternal(view, url,true)) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

        });


        //检测进度
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 90) {
                    mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar.setProgress(newProgress);//设置进度值
                }

            }
        });

    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
    }

    /**
     * 广告业务的特殊处理
     * 根据url的scheme处理跳转第三方app的业务
     * 如果应用程序可以处理该url,就不要让浏览器处理了,返回true;
     * 如果没有应用程序可以处理该url，先判断浏览器能否处理，如果浏览器也不能处理，直接返回false拦截该url，不要再让浏览器处理。
     * 避免出现当deepLink无法调起目标app时，展示加载失败的界面。
     * <p>
     * 某些含有deepLink链接的网页，当使用deepLink跳转目标app失败时，如果将该deepLinkUrl交给系统处理，系统处理不了，会导致加载失败；
     * 示例：
     * 网页Url：https://m.ctrip.com/webapp/hotel/hoteldetail/687592/checkin-1-7.html?allianceid=288562&sid=964106&sourceid=2504&sepopup=12
     * deepLinkUrl：ctrip://wireless/InlandHotel?checkInDate=20170504&checkOutDate=20170505&hotelId=687592&allianceid=288562&sid=960124&sourceid=2504&ouid=Android_Singapore_687592
     *
     * @param interceptExternalProtocol 是否拦截自定义的外部协议，true：拦截，无论如何都不让浏览器处理；false：不拦截，如何没有成功处理该协议，继续让浏览器处理
     */
    private boolean shouldOverrideUrlLoadingByAppInternal(WebView view, String url, boolean interceptExternalProtocol) {
        if (isAcceptedScheme(url)) {
            //如果这个地址是浏览器可以处理的地址
            return false;
        }
        Intent intent;
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException e) {
            return interceptExternalProtocol;
        }

        intent.setComponent(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            intent.setSelector(null);
        }

        //该Intent无法被设备上的应用程序处理
        if (this.getPackageManager().resolveActivity(intent, 0) == null) {
            return tryHandleByMarket(intent) || interceptExternalProtocol;
        }

        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            return interceptExternalProtocol;
        }
        return true;
    }


    private boolean tryHandleByMarket(Intent intent) {
        String packagename = intent.getPackage();
        if (packagename != null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packagename));
            try {
                this.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * 该url是否属于浏览器能处理的内部协议
     */
    private boolean isAcceptedScheme(String url) {
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            return true;
        } else {
            return false;
        }
    }
}
