package tech.shuidikeji.shuidijinfu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpFragment;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.constants.UrlConstant;
import tech.shuidikeji.shuidijinfu.mvp.contract.UserContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.UserPresenter;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.VerifyInfoPojo;
import tech.shuidikeji.shuidijinfu.ui.activity.AuthListActivity;
import tech.shuidikeji.shuidijinfu.ui.activity.BankCardActivity;
import tech.shuidikeji.shuidijinfu.ui.activity.BorrowListActivity;
import tech.shuidikeji.shuidijinfu.ui.activity.LoginActivity;
import tech.shuidikeji.shuidijinfu.ui.activity.WebViewActivity;
import tech.shuidikeji.shuidijinfu.ui.adapter.UserAdapter;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.DataOptionUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;

/**
 * 个人中心页
 */
public class UserFragment extends BaseMvpFragment<UserPresenter> implements View.OnClickListener, UserContract.IUserView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    UserAdapter mAdapter;

    View mHeaderView;
    View mFooterView;

    LinearLayout mLoginBtn;
    TextView mPhoneTv;
    TextView mStatusTv;
    ImageView mMessageBtn;
    ImageView mUnreadIv;
    TextView mLogoutBtn;

    List<AppConfigPojo.UserMenu> mData;
    int status;


    public static UserFragment newInstance(){
        return new UserFragment();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.fragment_user,parent,false);
    }

    @Override
    protected void initData() {
        mData = SPUtils.getObject(PreferenceConstant.USER_MENU,null);
        mAdapter = new UserAdapter();
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.layout_user_header,null);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_user_footer,null);
        mLogoutBtn = mFooterView.findViewById(R.id.btn_logout);
        mLoginBtn = mHeaderView.findViewById(R.id.btn_login);
        mPhoneTv = mHeaderView.findViewById(R.id.tv_phone);
        mStatusTv = mHeaderView.findViewById(R.id.tv_verify_status);
        mMessageBtn = mHeaderView.findViewById(R.id.iv_message);
        mUnreadIv = mHeaderView.findViewById(R.id.iv_unread);
        mAdapter.addHeaderView(mHeaderView);
        mAdapter.addFooterView(mFooterView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mData);

        mLoginBtn.setOnClickListener(this);
        mLogoutBtn.setOnClickListener(this);
        mMessageBtn.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (!CommonUtils.isLogin())
                    LoginActivity.launcher(getActivity());
                break;
            case R.id.btn_logout:
                CommonUtils.clearLoginInfo();
                refresh();
                break;
        }
    }

    @Override
    protected UserPresenter getPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public void showUserVerifyInfo(VerifyInfoPojo info) {
        mPhoneTv.setText(DataOptionUtils.getEncryptMobile(SPUtils.getString(PreferenceConstant.USER_PHONE)));
        mStatusTv.setText(info.getStatus_text());
        status = info.getStatus();
        if (status == 0)
            status = 10;
        else if (status == 10){
            status = 5;
        }
    }

    @Override
    public void showUnReadMessage(int unread) {
        mUnreadIv.setVisibility(unread==0?View.GONE:View.VISIBLE);
    }

    private void refresh(){
        if (CommonUtils.isLogin()){
            mPresenter.getUnreadMessage();
            mPresenter.getUserVerifyInfo();
            mFooterView.setVisibility(View.VISIBLE);

        }else {
            mStatusTv.setText("");
            mPhoneTv.setText("登录/注册");
            mFooterView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String flag = mData.get(position).getName();
        String chName = mData.get(position).getCn_name();
        switch (flag){
            case "verify":
                // 检测项目
                AuthListActivity.launcher(getActivity(),status);
                break;
            case "report":
                // TODO: 查看报告
                break;
            case "borrow":
                // 我的借款
                BorrowListActivity.launcher(getActivity());
                break;
            case "bank":
                // TODO: 我的银行卡
                BankCardActivity.launcher(getActivity());
                break;
            case "help":
                // 帮助中心
                WebViewActivity.launcher(getContext(),chName, UrlConstant.HELP_QA);
                break;
            case "aboutus":
                // 关于我们
                WebViewActivity.launcher(getContext(),chName, UrlConstant.HELP_ABOUT);
                break;
            case "concat":
                // 联系我们
                WebViewActivity.launcher(getContext(),chName, UrlConstant.HELP_CONCAT);
                break;
            case "protocol":
                // 用户协议
                WebViewActivity.launcher(getContext(),chName, UrlConstant.HELP_USER_PROTOCOL);
                break;
            case "feedback":
                // TODO: 意见反馈
                break;

        }
    }
}
