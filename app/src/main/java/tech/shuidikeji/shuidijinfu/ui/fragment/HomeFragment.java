package tech.shuidikeji.shuidijinfu.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpFragment;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.mvp.contract.HomeContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.HomePresenter;
import tech.shuidikeji.shuidijinfu.pojo.IndexPojo;
import tech.shuidikeji.shuidijinfu.ui.activity.WebDialogActivity;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.widget.DialView;
import tech.shuidikeji.shuidijinfu.widget.VerticalTextview;

/**
 * 首页
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.IHomeView {

    @BindView(R.id.tv_total)
    TextView mTotalTv;
    @BindView(R.id.tv_final)
    TextView mFinalTv;
    @BindView(R.id.tv_top_text)
    TextView mTopTextTv;
    @BindView(R.id.tv_foot_text)
    TextView mFootTextTv;
    @BindView(R.id.tv_dial_center)
    TextView mDialCenterTv;
    @BindView(R.id.dial_view)
    DialView mDialView;

    @BindView(R.id.tv_final1)
    TextView mFinalTv1;
    @BindView(R.id.tv_top_text1)
    TextView mTopTextTv1;
    @BindView(R.id.tv_foot_text1)
    TextView mFootTextTv1;

    @BindView(R.id.dial_style_view)
    LinearLayout mDialStyleView;
    @BindView(R.id.square_style_view)
    RelativeLayout mSquareStyleView;

    @BindView(R.id.tv_vertical)
    VerticalTextview mVerticalTv;

    @BindView(R.id.btn_loan)
    ImageView mLoanBtn;

    private String theme;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.fragment_home,parent,false);
    }

    @Override
    protected void initData() {
        theme = SPUtils.getString(PreferenceConstant.HOME_THEME);
    }

    @Override
    protected void initView() {
        mVerticalTv.setText(16, 5, getResourceColor(R.color.text_color_title));//设置属性
        mVerticalTv.setTextStillTime(3000);//设置停留时长间隔
        mVerticalTv.setAnimTime(300);//设置进入和退出的时间间隔
        if (SPUtils.getInt(PreferenceConstant.CHECK_STATUS) == 2){
            mLoanBtn.setImageResource(R.mipmap.buttom_loan);
        }else {
            mLoanBtn.setImageResource(R.mipmap.buttom_check);
        }
    }

    @Override
    public void showIndex(IndexPojo data) {
        boolean isSquareStyle = theme.equals("1");
        if (isSquareStyle){
            mSquareStyleView.setVisibility(View.VISIBLE);
            mDialStyleView.setVisibility(View.GONE);
            mTopTextTv1.setText(data.getTop_text());
            mFootTextTv1.setText(data.getFoot_text());
            mFinalTv1.setText(String.valueOf(data.getFinnal()));

        }else {
            mSquareStyleView.setVisibility(View.GONE);
            mDialStyleView.setVisibility(View.VISIBLE);
            mTopTextTv.setText(data.getTop_text());
            mFootTextTv.setText(data.getFoot_text());
            mFinalTv.setText(String.valueOf(data.getFinnal()));
            mTotalTv.setText(String.valueOf(data.getTotal()));
            mDialCenterTv.setText(String.valueOf(data.getTotal()/2));
            mDialView.setNum(data.getFinnal()/(data.getTotal()/180));
        }

    }

    @Override
    public void showNotification(ArrayList<String> data) {
        mVerticalTv.setTextList(data);
    }

    @Override
    public void showIndexDialog(String url) {
        WebDialogActivity.launcher(getContext(),url);
        SPUtils.putInt(PreferenceConstant.HOME_DIALOG_STATUS,1);
    }

    @Override
    public void onResume() {
        super.onResume();
        mVerticalTv.startAutoScroll();
        refresh();

    }

    @Override
    public void onPause() {
        super.onPause();
        mVerticalTv.stopAutoScroll();
    }

    private void refresh(){
        mVerticalTv.startAutoScroll();
        mPresenter.getNotification();
        mPresenter.getIndex();
        mPresenter.getLoginActivityUrl(SPUtils.getInt(PreferenceConstant.HOME_DIALOG_STATUS));
    }
}
