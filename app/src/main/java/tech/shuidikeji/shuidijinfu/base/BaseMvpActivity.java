package tech.shuidikeji.shuidijinfu.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.bqs.risk.df.android.OnBqsDFContactsListener;
import com.bqs.risk.df.android.OnBqsDFListener;

import tech.shuidikeji.shuidijinfu.BuildConfig;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.widget.dialog.AlertDialog;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {

    protected P mPresenter;

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errMsg) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(errMsg)
                .setSingleButton("确定", v -> {

                }).build().show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        super.onCreate(savedInstanceState);
        if (BqsDF.getInstance().canInitBqsSDK() && SPUtils.getBoolean(PreferenceConstant.AUTH_PERMISSION))
            initBqsDFSdk();

    }

    protected abstract P getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    protected void initBqsDFSdk(){

        BqsParams params = new BqsParams();
        params.setPartnerId(BuildConfig.BAIQISHI_PARTNER_CODE);//商户编号
        params.setTestingEnv(false);//false是生产环境 true是测试环境
        params.setGatherCallRecord(true);
        params.setGatherContact(true);
        params.setGatherGps(true);
        params.setGatherInstalledApp(true);
        params.setGatherSMSCount(true);

        //3、执行初始化
        BqsDF.getInstance().initialize(this, params);
        ////采集通讯录 通过SDK采集并上传,第一个参数：是否采集通讯录，第二个参数：是否采集通话记录
        BqsDF.getInstance().commitContactsAndCallRecords(true, true);

        //采集经纬度 通过SDK采集并上传
        BqsDF.getInstance().commitLocation();
        if (CommonUtils.isLogin())
            new Handler().postDelayed(() ->
                    mPresenter.commitTokenKey(BqsDF.getInstance().getTokenKey(),
                            SPUtils.getString(PreferenceConstant.USER_ID)),
                    500);

    }

}
