package tech.shuidikeji.shuidijinfu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zqzn.idauth.sdk.DetectEngine;
import com.zqzn.idauth.sdk.ErrorCode;
import com.zqzn.idauth.sdk.FaceResultCallback;

import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.BuildConfig;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.FaceLiveAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.FaceLiveAuthPresenter;
import tech.shuidikeji.shuidijinfu.utils.BitmapUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;

public class FaceLiveAuthActivity extends BaseMvpActivity<FaceLiveAuthPresenter> implements AMapLocationListener, FaceResultCallback, FaceLiveAuthContract.IFaceLiveAuthView {

    DetectEngine mDetectEngine;
    private AMapLocationClient mLocationClient;
    private double lat;
    private double lng;

    public static void launcher(Context context){
        Intent intent = new Intent(context,FaceLiveAuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_face_live_auth);
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

    @OnClick(R.id.btn_auth_face)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_auth_face:
                if (mDetectEngine.face_liveness(this,
                        BuildConfig.ORC_APP_KEY, BuildConfig.ORC_SECRET_KEY, 1,
                        this) != ErrorCode.SUCCESS.getCode())
                    ToastUtils.showToast(this,"接口调用失败");
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        mLocationClient.stopLocation();
    }

    @Override
    public void notifyResult(FaceResult faceResult) {
        String faceImage = BitmapUtils.BitmapToBase64(faceResult.face_image);
        mPresenter.uploadFaceImage(faceImage);
    }

    @Override
    protected FaceLiveAuthPresenter getPresenter() {
        return new FaceLiveAuthPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    public void showUploadSuccess() {
        mPresenter.postLocation(lng,lat,"req_liveness","android");
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
}
