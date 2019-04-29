package tech.shuidikeji.shuidijinfu.ui.activity;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.BasicInfoAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.BasicInfoAuthPresenter;
import tech.shuidikeji.shuidijinfu.pojo.BasicInfoConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.ProvincePojo;
import tech.shuidikeji.shuidijinfu.utils.KeyboardUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;

/**
 * 用户基本信息认证
 */
public class BasicInfoAuthActivity extends BaseMvpActivity<BasicInfoAuthPresenter> implements BasicInfoAuthContract.IBasicInfoAuthView, OnOptionsSelectListener, AMapLocationListener {

    @BindView(R.id.et_qq)
    EditText mQQEt;
    @BindView(R.id.tv_marital_status)
    TextView mMaritalStatusTv;

    @BindView(R.id.tv_job)
    TextView mJobTv;

    @BindView(R.id.tv_job_type)
    TextView mJobTypeTv;

    @BindView(R.id.tv_social)
    TextView mSocialTv;

    @BindView(R.id.tv_company_address)
    TextView mCompanyAddressTv;

    @BindView(R.id.et_company_name)
    EditText mCompanyNameEt;

    @BindView(R.id.et_company_address)
    EditText mCompanyAddressEt;

    OptionsPickerView<BasicInfoConfigPojo.ItemPojo> mMaritalStatusPv,mSocialPv,mJobPv,mJobTypePv;
    OptionsPickerView mAddressPv;


    private int jobTypeCode,jobCode,maritalStatusCode,socialCode;
    private String province,city,area;
    private List<BasicInfoConfigPojo.ItemPojo> jobType,job,maritalStatus,social;
    private List<ProvincePojo> optionItem1;
    private List<List<String>> optionItem2;
    private List<List<List<String>>> optionItem3;

    private AMapLocationClient mLocationClient;
    private double lat,lng;


    public static void launcher(Context context){
        Intent intent  = new Intent(context,BasicInfoAuthActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected BasicInfoAuthPresenter getPresenter() {
        return new BasicInfoAuthPresenter(this);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("个人基础信息").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_basic_info_auth);
        mQQEt.clearFocus();
        mCompanyAddressEt.clearFocus();
        mCompanyNameEt.clearFocus();
        mPresenter.getBasicInfoAuthConfig();
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

    @OnClick({R.id.ll_marital_status,R.id.ll_job,R.id.ll_job_type,R.id.ll_social,R.id.ll_company_address,R.id.btn_commit})
    public void onClick(View v){
        KeyboardUtils.hide(this,v);
        switch (v.getId()){
            case R.id.ll_marital_status:
                if (mMaritalStatusPv != null) {
                    mMaritalStatusPv.show(v);
                }
                break;
            case R.id.ll_job:
                if (mJobPv != null) {
                    mJobPv.show(v);
                }
                break;
            case R.id.ll_job_type:
                if (mJobTypePv != null) {
                    mJobTypePv.show(v);
                }
                break;
            case R.id.ll_social:
                if (mSocialPv != null) {
                    mSocialPv.show(v);
                }
                break;
            case R.id.ll_company_address:
                mAddressPv.show(v);
                break;
            case R.id.btn_commit:
                String qq = mQQEt.getText().toString();
                String companyName = mCompanyNameEt.getText().toString();
                String companyAddress = mCompanyAddressEt.getText().toString();
                String companyCity = mCompanyAddressTv.getText().toString();
                if (TextUtils.isEmpty(companyName)){
                    ToastUtils.showToast(this,"请正确填写您的公司名称");
                    return;
                }
                if (TextUtils.isEmpty(companyCity)){
                    ToastUtils.showToast(this,"请选择您的公司地址");
                    return;
                }
                if (TextUtils.isEmpty(companyAddress)){
                    ToastUtils.showToast(this,"请正确填写您的公司地址");
                    return;
                }
                mPresenter.commitBasicInfo(companyName,companyAddress,province,city,area,qq,maritalStatusCode,socialCode,jobCode,jobTypeCode);
                break;
        }
    }

    @Override
    public void showBasicInfoAuthConfig(BasicInfoConfigPojo data) {
        jobType = data.getJobType();
        job = data.getJob();
        maritalStatus = data.getMaritalStatus();
        social = data.getSocial();
        initMaritalStatusPv();
        initSocialPv();
        initJobPv();
        initJobTypePv();
    }

    @Override
    public void showProvinceConfig(List<ProvincePojo> options1,List<List<String>> options2,List<List<List<String>>> options3) {
        optionItem1 = options1;
        optionItem2 = options2;
        optionItem3 = options3;
        initAddressPv();
    }

    @Override
    public void showCommitSuccess() {
        mPresenter.postLocation(lng,lat,"req_user_basic_msg","android");
    }

    private void initAddressPv() {
        mAddressPv = new OptionsPickerBuilder(this,this)
                .setTitleText("选择城市")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mAddressPv.setPicker(optionItem1,optionItem2,optionItem3);
    }

    private void initJobTypePv() {
        mJobTypePv = new OptionsPickerBuilder(this,this)
                .setTitleText("工作性质")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mJobTypePv.setPicker(jobType);
    }

    private void initJobPv() {
        mJobPv = new OptionsPickerBuilder(this,this)
                .setTitleText("职业")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mJobPv.setPicker(job);
    }

    private void initSocialPv() {
        mSocialPv = new OptionsPickerBuilder(this,this)
                .setTitleText("是否有社保")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mSocialPv.setPicker(social);
    }

    private void initMaritalStatusPv() {
        mMaritalStatusPv = new OptionsPickerBuilder(this,this)
                .setTitleText("婚姻状况")
                .setContentTextSize(20)
                .setSelectOptions(0)
                .setOutSideCancelable(true)
                .setOutSideColor(getResourceColor(R.color.translucent))
                .build();
        mMaritalStatusPv.setPicker(maritalStatus);
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
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        switch (v.getId()){
            case R.id.ll_marital_status:
                maritalStatusCode = maritalStatus.get(options1).getCode();
                mMaritalStatusTv.setText(maritalStatus.get(options1).getText());
                break;
            case R.id.ll_social:
                socialCode = social.get(options1).getCode();
                mSocialTv.setText(social.get(options1).getText());
                break;
            case R.id.ll_job:
                jobCode = job.get(options1).getCode();
                mJobTv.setText(job.get(options1).getText());
                break;
            case R.id.ll_job_type:
                jobTypeCode = jobType.get(options1).getCode();
                mJobTypeTv.setText(jobType.get(options1).getText());
                break;
            case R.id.ll_company_address:
                province = optionItem1.size() > 0 ?
                        optionItem1.get(options1).getPickerViewText() : "";

                city = optionItem2.size() > 0
                        && optionItem2.get(options1).size() > 0 ?
                        optionItem2.get(options1).get(options2) : "";

                area = optionItem2.size() > 0
                        && optionItem3.get(options1).size() > 0
                        && optionItem3.get(options1).get(options2).size() > 0 ?
                        optionItem3.get(options1).get(options2).get(options3) : "";

                String tx = province.equals(city) ?city + area : province + city + area;
                mCompanyAddressTv.setText(tx);
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
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
