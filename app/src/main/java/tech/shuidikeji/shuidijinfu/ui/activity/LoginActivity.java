package tech.shuidikeji.shuidijinfu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bqs.risk.df.android.BqsDF;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.image.ImageManager;
import tech.shuidikeji.shuidijinfu.mvp.contract.LoginContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.LoginPresenter;
import tech.shuidikeji.shuidijinfu.utils.MD5Utils;
import tech.shuidikeji.shuidijinfu.utils.SystemUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;
import tech.shuidikeji.shuidijinfu.widget.SuperButton;

/**
 * 注册登录页
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.ILoginView, AMapLocationListener, TextWatcher {

    @BindView(R.id.et_code)
    EditText mCodeEt;
    @BindView(R.id.et_phone)
    EditText mPhoneEt;
    @BindView(R.id.et_img_code)
    EditText mImgCodeEt;
    @BindView(R.id.iv_code)
    ImageView mCodeIv;
    @BindView(R.id.btn_code)
    SuperButton mCodeBtn;
    @BindView(R.id.btn_login)
    SuperButton mLoginBtn;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;

    private AMapLocationClient mLocationClient;
    private double lat;
    private double lng;
    private CountDownTimer mTimer;
    private boolean isTimerRunning;

    private String tokenKey;
    private String deviceSn;
    private String marketId;
    private String brand;
    private String ram;
    private String uuid;


    public static void launcher(Activity activity){
        Intent intent = new Intent(activity,LoginActivity.class);
        activity.startActivityForResult(intent,REQUEST_LOGIN);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("登录/注册").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        mCodeEt.addTextChangedListener(this);
        mPhoneEt.addTextChangedListener(this);
        mCodeBtn.setCanClick(false);
        mLoginBtn.setCanClick(false);
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
//        uuid = MD5Utils.MD5(MD5Utils.getRandomString(40)+System.currentTimeMillis());
//        mPresenter.getImageCode(uuid);
    }

    @Override
    protected void initData() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setInterval(30000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
        tokenKey = BqsDF.getInstance().getTokenKey();
        deviceSn = SystemUtils.getIMEI2(this);
        marketId = "shuidikeji";
        brand = SystemUtils.getDeviceBrand();
        ram = (int) (Float.parseFloat(SystemUtils.getTotalMemory(this)) + 0.5)+"";

    }

    @OnClick({R.id.btn_code,R.id.btn_login,R.id.btn_agreement,R.id.iv_code})
    public void onClick(View v){
        String phone = mPhoneEt.getText().toString();
        String code = mCodeEt.getText().toString();
        String imageCode = mImgCodeEt.getText().toString();
        switch (v.getId()){
            case R.id.btn_code:
                mPresenter.getCaptcha(phone);
                break;
            case R.id.btn_agreement:
                break;
            case R.id.btn_login:
                if (lng == 0 || lat == 0){
                    showError("位置获取失败，请重试");
                    mLocationClient.startLocation();
                }else if (!mCheckBox.isChecked()){
                    ToastUtils.showToast(this, "请先同意注册授权协议");
                } else {
                    mPresenter.login(phone,imageCode,uuid,code,tokenKey,lng,lat,"android","android",deviceSn,marketId,brand,ram);
                }
                break;
//            case R.id.iv_code:
//                uuid = MD5Utils.MD5(MD5Utils.getRandomString(40)+System.currentTimeMillis());
//                mPresenter.getImageCode(uuid);
//                break;
        }
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void showGetCaptchaSuccess() {
        ToastUtils.showToast(this,"验证码获取成功，有效期五分钟");
        mTimer.start();
    }

    @Override
    public void showImageCodeSuccess(String url) {
        ImageManager.getInstance().loadNet(url,mCodeIv);
    }

    @Override
    public void showLoginSuccess() {
        finish();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        mLocationClient.stopLocation();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String code = mCodeEt.getText().toString();
        String phone = mPhoneEt.getText().toString();

        mCodeBtn.setCanClick(phone.length() == 11 && !isTimerRunning);
        mLoginBtn.setCanClick(code.length() == 6 && phone.length() == 11);
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
