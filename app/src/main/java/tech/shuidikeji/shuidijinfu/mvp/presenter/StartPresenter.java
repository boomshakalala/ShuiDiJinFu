package tech.shuidikeji.shuidijinfu.mvp.presenter;

import java.util.List;

import cn.beecloud.BeeCloud;
import tech.shuidikeji.shuidijinfu.base.BasePresenter;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.StartContract;
import tech.shuidikeji.shuidijinfu.mvp.model.StartModel;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;

public class StartPresenter extends BasePresenter<StartContract.IStartView,StartContract.IStartModel> {


    public StartPresenter(StartContract.IStartView view) {
        super(view, new StartModel());
    }

    public void getAppConfig(){
        mModel.getAppConfig("android")
                .compose(RxUtils.transform(getView()))
                .subscribe(new RespObserver<AppConfigPojo>() {
                    @Override
                    public void onResult(AppConfigPojo data) {
                        //保存主页面底部tab数据
                        SPUtils.putObject(PreferenceConstant.MAIN_MENU,data.getMenu());
                        SPUtils.putObject(PreferenceConstant.USER_MENU,data.getUser_menu());
                        SPUtils.putInt(PreferenceConstant.CHECK_STATUS,data.getCheck_status());
                        SPUtils.putString(PreferenceConstant.HOME_THEME,data.getTheme());
                        //初始化秒支付
                        BeeCloud.setAppIdAndSecret(data.getBeecloud().getApp_id(), data.getBeecloud().getMaster_secret());
                        getView().showConfigSuccess();
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {

                    }
                });

    }
}
