package tech.shuidikeji.shuidijinfu.ui.activity;


import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.mvp.contract.StartContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.StartPresenter;
import tech.shuidikeji.shuidijinfu.ui.adapter.GuideAdapter;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.widget.CircleIndicator;
import tech.shuidikeji.shuidijinfu.widget.SuperButton;


public class SplashActivity extends BaseMvpActivity<StartPresenter> implements StartContract.IStartView, ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.btn_launch)
    SuperButton mLaunchBtn;
    GuideAdapter mAdapter;
    List<Integer> mImageList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
        mStatusBar.setVisibility(View.GONE);
        mViewPager.addOnPageChangeListener(this);
        mPresenter.getAppConfig();
    }

    @Override
    protected void initData() {
        mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.q1);
        mImageList.add(R.mipmap.q2);
        mImageList.add(R.mipmap.q3);
        mAdapter = new GuideAdapter(mImageList,this);
    }

    @OnClick(R.id.btn_launch)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_launch:
                SPUtils.putBoolean(PreferenceConstant.FIRST_LAUNCH,false);
                MainActivity.launcher(this);
                finish();
                break;
        }
    }


    @Override
    protected StartPresenter getPresenter() {
        return new StartPresenter(this);
    }

    @Override
    public void showConfigSuccess() {
        if (SPUtils.getBoolean(PreferenceConstant.FIRST_LAUNCH,true)){
            mViewPager.setVisibility(View.VISIBLE);
            mIndicator.setVisibility(View.VISIBLE);
        }else {
            MainActivity.launcher(this);
            finish();
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 2){
            mIndicator.setVisibility(View.GONE);
            mLaunchBtn.setVisibility(View.VISIBLE);
        }else {
            mIndicator.setVisibility(View.VISIBLE);
            mLaunchBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
