package tech.shuidikeji.shuidijinfu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.image.ImageManager;
import tech.shuidikeji.shuidijinfu.mvp.contract.BankCardContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.BankCardPresenter;
import tech.shuidikeji.shuidijinfu.pojo.BankCardPojo;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.DataOptionUtils;

public class BankCardActivity  extends BaseMvpActivity<BankCardPresenter> implements BankCardContract.IBankCardView {


    @BindView(R.id.ll_bank_card)
    LinearLayout mBankCardLayout;
    @BindView(R.id.tv_bank_card_num)
    TextView mBankCardNumTv;
    @BindView(R.id.tv_bank_name)
    TextView mBankNameTv;
    @BindView(R.id.iv_bank_card)
    ImageView mBankCardIv;
    @BindView(R.id.iv_empty)
    ImageView mEmptyIv;

    public static void launcher(Activity context){
        if (!CommonUtils.isLogin()){
            LoginActivity.launcher(context);
            return;
        }
        Intent intent = new Intent(context,BankCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected BankCardPresenter getPresenter() {
        return new BankCardPresenter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bank_card);
        mPresenter.getBankCard();

    }

    @Override
    protected void initData() {

    }

    @Override
    public void showBankCard(BankCardPojo data) {
        if (TextUtils.isEmpty(data.getBankcard_number()) || TextUtils.isEmpty(data.getBankcard_bank()) || TextUtils.isEmpty(data.getBankcard_img())){
            mBankCardLayout.setVisibility(View.GONE);
            mEmptyIv.setVisibility(View.VISIBLE);
        }else {
            mBankNameTv.setText(data.getBankcard_bank());
            ImageManager.getInstance().loadNet(data.getBankcard_img(),mBankCardIv);
            mBankCardNumTv.setText(DataOptionUtils.getHideCenterString(data.getBankcard_number()));
            mBankCardLayout.setVisibility(View.VISIBLE);
            mEmptyIv.setVisibility(View.GONE);
        }

    }
}
