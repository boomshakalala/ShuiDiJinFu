package tech.shuidikeji.shuidijinfu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.image.ImageManager;
import tech.shuidikeji.shuidijinfu.mvp.contract.AddBankCardContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.AddBankCardPresenter;
import tech.shuidikeji.shuidijinfu.pojo.BankListPojo;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.KeyboardUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;
import tech.shuidikeji.shuidijinfu.widget.SuperButton;

public class AddBankCardActivity extends BaseMvpActivity<AddBankCardPresenter> implements AddBankCardContract.IAddBankCardView, View.OnFocusChangeListener, AMapLocationListener, TextWatcher, KeyboardUtils.OnKeyboardVisibilityListener {

    @BindView(R.id.ll_bank_list)
    LinearLayout mBankListLayout;
    @BindView(R.id.tv_bank_name)
    TextView mBankNameTv;
    @BindView(R.id.et_bank_card_num)
    EditText mBankCardEt;
    @BindView(R.id.et_code)
    EditText mCodeEt;
    @BindView(R.id.et_phone)
    EditText mPhoneEt;
    @BindView(R.id.tv_bank_address)
    TextView mBankAddressTv;
    @BindView(R.id.btn_code)
    SuperButton mCodeBtn;


    AMapLocationClient mLocationClient;
    String requestNo;

    CountDownTimer mTimer;
    boolean isTimerRunning;
    private double lat;
    private double lng;
    private String cityCode;

    public static void launcher(Activity context){
        if (!CommonUtils.isLogin()){
            LoginActivity.launcher(context);
            return;
        }
        Intent intent = new Intent(context,AddBankCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected AddBankCardPresenter getPresenter() {
        return new AddBankCardPresenter(this);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("添加银行卡").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_bank_card);
        mBankCardEt.setOnFocusChangeListener(this);
        mPhoneEt.addTextChangedListener(this);
        mBankCardEt.clearFocus();
        mPhoneEt.clearFocus();
        mCodeEt.clearFocus();
        KeyboardUtils.setKeyboardListener(this,this);
        mTimer = new CountDownTimer(60000,1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                mCodeBtn.setCanClick(false);
                mCodeBtn.setText(String.format("%d秒后获取", millisUntilFinished / 1000));
                isTimerRunning = true;
            }

            @Override
            public void onFinish() {
                mCodeBtn.setCanClick(true);
                mCodeBtn.setText("获取验证码");
                isTimerRunning = false;
            }
        };
        mPresenter.getBankList();
    }

    @Override
    protected void initData() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setInterval(30000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    @OnClick({R.id.tv_bank_address,R.id.btn_code,R.id.btn_commit})
    public void onClick(View v){
        String phone = mPhoneEt.getText().toString();
        String code = mCodeEt.getText().toString();
        String bankCardNumber = mBankCardEt.getText().toString();
        switch (v.getId()){
            case R.id.tv_bank_address:
                AddressListActivity.launcher(this);
                break;
            case R.id.btn_code:
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showToast(this,"请先输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(bankCardNumber)){
                    ToastUtils.showToast(this,"请先输入银行卡号");
                    return;
                }
                if (TextUtils.isEmpty(cityCode)){
                    ToastUtils.showToast(this,"请先选择开户行城市");
                    return;
                }
                mPresenter.getBankCode(phone,bankCardNumber,cityCode);
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(code)){
                    ToastUtils.showToast(this,"请先输入验证码");
                    return;
                }
                mPresenter.addBankCard(code,requestNo);
                break;

        }
    }

    @Override
    public void showBankList(List<BankListPojo> data) {
        mBankListLayout.removeAllViews();
        for (BankListPojo datum : data) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_bank,null);
            ImageView bankIv = item.findViewById(R.id.iv_bank);
            TextView bankNameTv = item.findViewById(R.id.tv_bank_name);
            TextView limitTv = item.findViewById(R.id.tv_limit);
            ImageManager.getInstance().loadNet(datum.getLogo(),bankIv);
            bankNameTv.setText(datum.getName());
            limitTv.setText(datum.getLimit());
            mBankListLayout.addView(item);
        }
    }

    @Override
    public void showGetCodeSuccess(String data) {
        requestNo = data;
        mTimer.start();
    }

    @Override
    public void showBankName(String data) {
        mBankNameTv.setText(data);
    }

    @Override
    public void showCommitSuccess() {
        mPresenter.postLocation(lng,lat,"add_bankcard","android");
    }

    @Override
    public void showPostLocationSuccess() {
        dismissProgressDialog();
        finish();
    }

    @Override
    public void showPostLocationFailure() {
        dismissProgressDialog();
        finish();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus){
            String bankCardNum = mBankCardEt.getText().toString();
            mPresenter.getBankName(bankCardNum);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String phone = mPhoneEt.getText().toString();
        String cardNumber = mBankCardEt.getText().toString();
        mCodeBtn.setCanClick(phone.length() == 11 && !isTimerRunning && !TextUtils.isEmpty(cardNumber));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADDRESS && resultCode == RESULT_OK){
            String province = data.getStringExtra("province");
            String city = data.getStringExtra("city");
            cityCode = data.getStringExtra("cityCode");
            mBankAddressTv.setText(String.format("%s %s", province, city));
        }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if (!visible)
            mBankCardEt.clearFocus();
    }
}
