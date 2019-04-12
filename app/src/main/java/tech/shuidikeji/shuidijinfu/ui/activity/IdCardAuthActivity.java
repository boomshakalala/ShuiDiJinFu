package tech.shuidikeji.shuidijinfu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zqzn.idauth.sdk.DetectEngine;
import com.zqzn.idauth.sdk.ErrorCode;
import com.zqzn.idauth.sdk.IdResultCallback;

import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.BuildConfig;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.IdCardAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.IdCardAuthPresenter;
import tech.shuidikeji.shuidijinfu.pojo.IdCardPojo;
import tech.shuidikeji.shuidijinfu.utils.BitmapUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;

public class IdCardAuthActivity extends BaseMvpActivity<IdCardAuthPresenter> implements IdResultCallback, IdCardAuthContract.IdCardAuthView, AMapLocationListener {

    @BindView(R.id.btn_auth_id_card)
    LinearLayout mAuthIdCardBtn;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_nation)
    TextView mNationTv;
    @BindView(R.id.tv_address)
    TextView mAddressTv;
    @BindView(R.id.tv_number)
    TextView mNumberTv;
    @BindView(R.id.tv_birth)
    TextView mBirthTv;
    @BindView(R.id.tv_sex)
    TextView mSexTv;

    DetectEngine mDetectEngine;
    IdCardPojo idCard;
    private AMapLocationClient mLocationClient;
    private double lat;
    private double lng;

    public static void launcher(Context context){
        Intent intent = new Intent(context,IdCardAuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("身份信息").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_idcard_auth);
    }

    @Override
    protected void initData() {
        mDetectEngine = new DetectEngine();
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setInterval(30000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    @OnClick({R.id.btn_auth_id_card,R.id.btn_commit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_auth_id_card:
                int resultCode = mDetectEngine.id_ocr(this, BuildConfig.ORC_APP_KEY,BuildConfig.ORC_SECRET_KEY,this);
                if (resultCode != ErrorCode.SUCCESS.getCode())
                    ToastUtils.showToast(this,"接口调用失败");
                break;
            case R.id.btn_commit:
                if (idCard == null){
                    ToastUtils.showToast(this,"请先完成身份证扫描");
                    return;
                }
                mPresenter.commitIdCard(idCard.getName(),idCard.getSex(),idCard.getNation(),idCard.getBirth(),idCard.getAddress(),idCard.getNumber());
                break;
        }
    }

    @Override
    public void notifyResult(IdResult idResult) {
        if (idResult.result_code == ErrorCode.SUCCESS.getCode()) {
            String frontImage = BitmapUtils.BitmapToBase64(idResult.front_image);
            String backImage = BitmapUtils.BitmapToBase64(idResult.back_image);
            String faceImage = BitmapUtils.BitmapToBase64(idResult.face_image);
            mPresenter.uploadIdCard(frontImage,backImage,faceImage);

        } else {
            ToastUtils.showToast(this, String.format("OCR扫描失败:%s", idResult.result_code));
        }
    }

    @Override
    protected IdCardAuthPresenter getPresenter() {
        return new IdCardAuthPresenter(this);
    }

    @Override
    public void showUploadSuccess(IdCardPojo data) {
        idCard = data;
        mNameTv.setText(data.getName());
        mNationTv.setText(data.getNation());
        mBirthTv.setText(data.getBirth());
        mSexTv.setText(data.getSex());
        mAddressTv.setText(data.getAddress());
        mNumberTv.setText(data.getNumber());
        mAuthIdCardBtn.setVisibility(View.GONE);

    }

    @Override
    public void showCommitSuccess() {
        mPresenter.postLocation(lng,lat,"req_idcard","android");
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
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
    }
}
