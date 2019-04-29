package tech.shuidikeji.shuidijinfu.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import tech.shuidikeji.shuidijinfu.BuildConfig;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.utils.BarUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.widget.ProgressDialog;
import tech.shuidikeji.shuidijinfu.widget.TitleBar;

public abstract class BaseActivity extends RxAppCompatActivity  {
    protected static final int REQUEST_LOGIN = 102;
    protected static final int REQUEST_ADDRESS = 103;
    protected static final int REQUEST_IMMEDIATE_PHONE = 104;
    protected static final int REQUEST_OTHER_PHONE = 105;
    protected static final int REQUEST_CODE_SETTING = 106;


    private ProgressDialog mProgressDialog;
    private OnKeyClickListener mOnKeyClickListener;
    protected ViewGroup mRoot;
    private TitleBar mTitleBar;
    protected View mStatusBar;

    /**
     * 按键的监听，供页面设置自定义的按键行为
     */
    public interface OnKeyClickListener {
        /**
         * 点击了返回键
         */
        void clickBack();
    }


    public Context getContext() {
        return this;
    }
    public int getResourceColor(@ColorRes int colorId) {
        return ResourcesCompat.getColor(getResources(), colorId, null);

    }
    public String getResourceString(@StringRes int stringId) {
        return getResources().getString(stringId);
    }
    public String getResourceString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }
    public Drawable getResourceDrawable(@DrawableRes int id) {
        return ResourcesCompat.getDrawable(getResources(), id, null);
    }
    public void setOnKeyClickListener(OnKeyClickListener onkeyClickListener){
        mOnKeyClickListener = onkeyClickListener;
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    public View getStatusbar(){
        return mStatusBar;
    }

    public void showProgressDialog(){
        if (mProgressDialog!=null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }
    public void dismissProgressDialog(){
        if (mProgressDialog == null)
            return;
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mOnKeyClickListener != null) {
                    mOnKeyClickListener.clickBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        final View content = getLayoutInflater().inflate(layoutResID,null);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(params);
        setContentView(content);
        overridePendingTransition(R.anim.right_in,R.anim.alpha_out);
    }

    @Override
    public void setContentView(View contentView){
        mRoot = genRootView();
        mStatusBar = new View(this);
        mStatusBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight(this)));
        mStatusBar.setBackgroundColor(getResourceColor(R.color.bg_status_bar));
        mTitleBar = new TitleBar(this);
        mTitleBar.hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mRoot.addView(mStatusBar);
        }
        mRoot.addView(mTitleBar);
        mRoot.addView(contentView);
        super.setContentView(mRoot);
        ButterKnife.bind(this);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_in,R.anim.right_out);
    }






    protected void initTitle(){

    }

    protected abstract void initView();

    protected abstract void initData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog.Builder(this)
                .setMessage("拼命加载中")
                .build();
        initData();
        initView();
        initTitle();
    }

    private ViewGroup genRootView() {
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }







}
