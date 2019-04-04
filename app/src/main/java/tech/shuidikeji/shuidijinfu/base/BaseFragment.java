package tech.shuidikeji.shuidijinfu.base;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import tech.shuidikeji.shuidijinfu.widget.TitleBar;


public abstract class BaseFragment extends RxFragment {

    protected BaseActivity mActivity;
    //根布局
    private ViewGroup mRoot;
    /**
     * 显示圆形进度对话框
     */
    public void showProgressDialog() {
        mActivity.showProgressDialog();
    }

    /**
     * 关闭进度对话框
     */
    public void dismissProgressDialog() {
        mActivity.dismissProgressDialog();
    }

    public TitleBar getTitleBar(){
        return mActivity.getTitleBar();
    }

    public View getStatusBar(){
        return mActivity.getStatusbar();
    }

    public int getResourceColor(@ColorRes int colorId) {
        return mActivity.getResourceColor(colorId);
    }

    public String getResourceString(@StringRes int stringId) {
        return mActivity.getResourceString(stringId);
    }

    public String getResourceString(@StringRes int id, Object... formatArgs) {
        return mActivity.getResourceString(id,formatArgs);
    }

    public Drawable getResourceDrawable(@DrawableRes int id) {
        return mActivity.getResourceDrawable(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = genRootView();
        View contentView = createView(inflater,mRoot);
        if (contentView != null) {
            mRoot.addView(contentView);
        }
        ButterKnife.bind(this,mRoot);
        return mRoot;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initTitle();
        initView();
    }



    protected void initTitle(){

    }

    protected abstract View createView(LayoutInflater inflater,ViewGroup parent);

    protected abstract void initData();

    protected abstract void initView();

    private ViewGroup genRootView() {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }

}
