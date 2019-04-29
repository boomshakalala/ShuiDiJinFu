package tech.shuidikeji.shuidijinfu.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.EmergencyContactAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.EmergencyContactAuthPresenter;
import tech.shuidikeji.shuidijinfu.utils.KeyboardUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;
import tech.shuidikeji.shuidijinfu.widget.dialog.AlertDialog;

/**
 * 紧急联系人信息认证
 */
public class EmergencyContactAuthActivity extends BaseMvpActivity<EmergencyContactAuthPresenter> implements EmergencyContactAuthContract.IEmergencyContactAuthView, AMapLocationListener, OnOptionsSelectListener {


    @BindView(R.id.et_immediate_name)
    EditText mImmediateNameEt;
    @BindView(R.id.tv_immediate_relation)
    TextView mImmediateRelationTv;
    @BindView(R.id.tv_immediate_phone)
    TextView mImmediatePhoneTv;
    @BindView(R.id.et_other_name)
    EditText mOtherNameEt;
    @BindView(R.id.tv_other_relation)
    TextView mOtherRelationTv;
    @BindView(R.id.tv_other_phone)
    TextView mOtherPhoneTv;

    OptionsPickerView<String> mImmediateRelationsPv;
    OptionsPickerView<String> mOtherRelationsPv;

    private AMapLocationClient mLocationClient;
    private double lat,lng;
    private String[] immediateRelations = {"配偶", "父母", "兄弟姐妹", "子女"};
    private String[] otherRelations = {"同事", "同学", "朋友"};
    private int requestCode;


    public static void launcher(Context context){
        Intent intent = new Intent(context,EmergencyContactAuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected EmergencyContactAuthPresenter getPresenter() {
        return new EmergencyContactAuthPresenter(this);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("紧急联系人").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_emergency_contact_auth);
        mImmediateRelationsPv = new OptionsPickerBuilder(this,this)
                .setTitleText("与本人关系")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mImmediateRelationsPv.setPicker(Arrays.asList(immediateRelations));

        mOtherRelationsPv = new OptionsPickerBuilder(this,this)
                .setTitleText("与本人关系")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mOtherRelationsPv.setPicker(Arrays.asList(otherRelations));
        mImmediateNameEt.clearFocus();
        mOtherNameEt.clearFocus();
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

    @OnClick({R.id.ll_immediate_phone,R.id.ll_immediate_relation,R.id.ll_other_relation,R.id.ll_other_phone,R.id.btn_commit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_immediate_phone:
                requestCode = REQUEST_IMMEDIATE_PHONE;
                requestPermission();
                break;
            case R.id.ll_immediate_relation:
                KeyboardUtils.hide(this,v);
                mImmediateRelationsPv.show(v);
                break;
            case R.id.ll_other_phone:
                requestCode = REQUEST_OTHER_PHONE;
                requestPermission();
                break;
            case R.id.ll_other_relation:
                KeyboardUtils.hide(this,v);
                mOtherRelationsPv.show(v);
                break;
            case R.id.btn_commit:
                String immediateName = mImmediateNameEt.getText().toString();
                String immediateRelation = mImmediateRelationTv.getText().toString();
                String immediatePhone = mImmediatePhoneTv.getText().toString();
                String otherName = mOtherNameEt.getText().toString();
                String otherRelation = mOtherRelationTv.getText().toString();
                String otherPhone = mOtherPhoneTv.getText().toString();
                if (TextUtils.isEmpty(immediateName)){
                    ToastUtils.showToast(this,"请填写亲属姓名");
                    return;
                }
                if (TextUtils.isEmpty(otherName)){
                    ToastUtils.showToast(this,"请填写其他联系人姓名");
                    return;
                }
                if (TextUtils.isEmpty(immediatePhone)){
                    ToastUtils.showToast(this,"请选择直系亲属电话");
                    return;
                }
                if (TextUtils.isEmpty(immediateRelation)){
                    ToastUtils.showToast(this,"请选择直系亲属关系");
                    return;
                }
                if (TextUtils.isEmpty(otherRelation)){
                    ToastUtils.showToast(this,"请选择其他联系人关系");
                    return;
                }
                if (immediatePhone.equals(otherPhone)){
                    ToastUtils.showToast(this,"两个联系人电话不可相同");
                    return;
                }
                mPresenter.commitEmergencyContact(immediateName,immediatePhone,immediateRelation,otherName,otherPhone,otherRelation);
                break;
        }
    }

    @Override
    public void showCommitSuccess() {
        mPresenter.postLocation(lng,lat,"req_user_contacts","android");
    }

    @Override
    public void showPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast(this,"获取联系人失败，请检查您的权限是否打开");
            return;
        }
        switch (requestCode){
            case REQUEST_IMMEDIATE_PHONE:
                mImmediatePhoneTv.setText(phone);
                break;
            case REQUEST_OTHER_PHONE:
                mOtherPhoneTv.setText(phone);
                break;
        }
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
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        switch (v.getId()){
            case R.id.ll_immediate_relation:
                mImmediateRelationTv.setText(immediateRelations[options1]);
                break;
            case R.id.ll_other_relation:
                mOtherRelationTv.setText(otherRelations[options1]);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void requestPermission(){
        RxPermissions permissions = new RxPermissions(this);
        permissions.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS)
                .subscribe(permission -> {
                    if (permission.granted){
                        //用户同意所有权限
                        // 获取通讯录
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent,requestCode);
                    }else if (permission.shouldShowRequestPermissionRationale){
                        // 至少有一个权限被拒绝，并没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                    }else {
                        // 至少有一个权限被拒绝，并且选中『不再询问』
                        new AlertDialog.Builder(this)
                                .setCancelable(false)
                                .setTitle("提示")
                                .setMessage("为保证APP正常运行，需要您授予部分权限，是否前往设置页面设置？")
                                .setPositiveButton("是", v -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",getContext().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent,REQUEST_CODE_SETTING);
                                })
                                .setNegativeButton("否", v -> finish())
                                .build()
                                .show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING)
            return;
        if ((requestCode == REQUEST_OTHER_PHONE||requestCode == REQUEST_IMMEDIATE_PHONE)&&resultCode == RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            mPresenter.getPhoneNumber(uri);
        }

    }
}
