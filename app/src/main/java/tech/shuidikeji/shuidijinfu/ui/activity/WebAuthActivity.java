package tech.shuidikeji.shuidijinfu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.WebAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.WebAuthPresenter;
import tech.shuidikeji.shuidijinfu.utils.JavaScriptinterface;

public class WebAuthActivity extends BaseMvpActivity<WebAuthPresenter> implements WebAuthContract.IWebAuthView, AMapLocationListener {

    @BindView(R.id.ll_container)
    LinearLayout mContainerLayout;

    private String title;
    private String code;
    private AgentWeb mAgentWeb;
    private AMapLocationClient mLocationClient;
    private double lat,lng;

    public static void launcher(Context context,String title,String code){
        Intent intent = new Intent(context,WebAuthActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("code",code);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle(title).showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web_auth);
        mPresenter.getWebAuthUrl(code);
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        code = getIntent().getStringExtra("code");
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setInterval(30000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    @Override
    protected WebAuthPresenter getPresenter() {
        return new WebAuthPresenter(this);
    }

    @Override
    public void showAuthPage(String authUrl) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mContainerLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()//设置默认进度条
                .createAgentWeb()
                .ready()
                .go(authUrl);

        mAgentWeb.getJsInterfaceHolder().addJavaObject("App", new JavaScriptinterface(this, mAgentWeb));
        mPresenter.postLocation(lng,lat,code,"android");
    }

    @Override
    public void showPostLocationSuccess() {

    }

    @Override
    public void showPostLocationFailure() {

    }
    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        mLocationClient.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        mLocationClient.stopLocation();
    }
}
