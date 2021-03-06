package tech.shuidikeji.shuidijinfu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.tongdun.android.shell.FMAgent;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseListActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.AuthListContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.AuthPresenter;
import tech.shuidikeji.shuidijinfu.pojo.AuthListPojo;
import tech.shuidikeji.shuidijinfu.pojo.AuthSection;
import tech.shuidikeji.shuidijinfu.pojo.SubmitCheckPojo;
import tech.shuidikeji.shuidijinfu.pojo.SubmitPojo;
import tech.shuidikeji.shuidijinfu.ui.adapter.AuthenticationAdapter;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;
import tech.shuidikeji.shuidijinfu.widget.SuperButton;
import tech.shuidikeji.shuidijinfu.widget.dialog.AlertDialog;

/**
 * 认证项目页
 */
public class AuthListActivity extends BaseListActivity<AuthPresenter> implements AuthListContract.IAuthView, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, AMapLocationListener {

    View mFooterView;
    TextView mAgreementBtn;
    LinearLayout mAgreementLayout;
    SuperButton mNextBtn;
    CheckBox mCheckBox;
    private int status;
    private AMapLocationClient mLocationClient;
    private double lat,lng;

    public static void launcher(Activity context, int status){
        if (!CommonUtils.isLogin()){
            LoginActivity.launcher(context);
            return;
        }
        Intent intent = new Intent(context, AuthListActivity.class);
        intent.putExtra("status",status);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("认证项目").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.include_ptr_recycler);
        mFooterView = LayoutInflater.from(this).inflate(R.layout.layout_auth_footer,null);
        mCheckBox = mFooterView.findViewById(R.id.checkbox);
        mAgreementLayout = mFooterView.findViewById(R.id.ll_agreement);
        mAgreementBtn = mFooterView.findViewById(R.id.btn_agreement);
        mNextBtn = mFooterView.findViewById(R.id.btn_next);
        mAgreementBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        mAdapter.addFooterView(mFooterView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        if (status == 5) {
            mNextBtn.setCanClick(false);
            mNextBtn.setText("审核中");
            mAgreementLayout.setVisibility(View.GONE);
        }else if (status == 40 || status == 20){
            mNextBtn.setText("查看报告");
            mNextBtn.setCanClick(true);
            mAgreementLayout.setVisibility(View.GONE);
        }else {
            mAgreementLayout.setVisibility(View.VISIBLE);
            mNextBtn.setCanClick(true);
            mNextBtn.setText("下一步");
        }

        mFooterView.setVisibility(View.GONE);
        mAdapter.setOnItemClickListener(this);


    }

    @Override
    protected void initData() {
        status = getIntent().getIntExtra("status",10);
        mAdapter = new AuthenticationAdapter();
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setInterval(30000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void onRefresh() {
        mPresenter.getAuthList();
    }

    @Override
    protected AuthPresenter getPresenter() {
        return new AuthPresenter(this);
    }

    @Override
    public void showAuthList(List<AuthSection> authSectionList) {
        mAdapter.setNewData(authSectionList);
        mFooterView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSubmitCheck(SubmitCheckPojo data) {
        if (data.getNext().equals("submit")){
            mPresenter.submitApply(FMAgent.onEvent(this));
        }else {
            // TODO: 申请信息

        }
    }

    @Override
    public void showSubmitSuccess(SubmitPojo data) {
        status = 20;
        mNextBtn.setText("查看报告");
        mNextBtn.setCanClick(true);
        mAgreementLayout.setVisibility(View.GONE);
        switch (data.getNext()){
            case "index":
                break;
            case "detail":
                break;
            case "verify":
                break;
            case "product":
                break;
        }
    }

    @Override
    public void showPendingDialog(SubmitPojo data) {

    }

    @Override
    public void showRefuse(SubmitPojo data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                List<AuthListPojo> data = mAdapter.getData();
                for (AuthListPojo datum : data) {
                    if (datum.getIs_opertional() == 1 && datum.getStatus()!=20){
                        ToastUtils.showToast(getContext(),"请先完成必选认证项目");
                        return;
                    }
                }

                if (status == 5){
                    return;
                }

                if (status == 20 || status == 40){
                    // TODO: 查看报告
                    return;
                }
                mPresenter.postLocation(lng,lat,"submit_apply","android");
                mPresenter.submitCheck();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AuthSection section = (AuthSection) adapter.getItem(position);
        if (section == null)
            return;
        if (section.isHeader)
            return;
        AuthListPojo item = section.t;
        switch (item.getValid_status()){
            case 20:
                ToastUtils.showToast(this,"该项目已认证完成");
                return;
            case 11:
                ToastUtils.showToast(this,"该项目正在认证中");
                return;
            case 40:
                ToastUtils.showToast(this,"很遗憾，您的认证审批未能通过\n请您保持良好的信用，欢迎您在14天后重新尝试。");
                return;
        }
        if (position>1 && item.getIs_opertional() == 1){
            AuthSection lastSection = (AuthSection) adapter.getItem(position -1);
            if (lastSection!=null && lastSection.t.getValid_status()!=20 && lastSection.t.getValid_status() != 11){
                ToastUtils.showToast(this,"请按顺序完成认证");
                return;
            }
        }else if (item.getIs_opertional() == 0){
            AuthSection firstSection = (AuthSection) adapter.getItem(1);
            if (firstSection !=null && firstSection.t.getValid_status() != 20 && firstSection.t.getValid_status() != 11){
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请先完成身份信息认证")
                        .setSingleButton("知道了", v -> { }).build().show();
                return;
            }

        }
        switch (item.getCode()){
            case "setIdcard":
                IdCardAuthActivity.launcher(this);
                break;
            case "livebodyCheck":
                FaceLiveAuthActivity.launcher(this);
                break;
            case "setBank":
                AddBankCardActivity.launcher(this);
                break;
            case "setBasicInfo":
                BasicInfoAuthActivity.launcher(this);
                break;
            case "setEmergencycontact":
                EmergencyContactAuthActivity.launcher(this);
                break;
                default:
                    WebAuthActivity.launcher(this,item.getName(),item.getCode());
                    break;
        }

    }

    @Override
    public void showPostLocationSuccess() {

    }

    @Override
    public void showPostLocationFailure() {

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
