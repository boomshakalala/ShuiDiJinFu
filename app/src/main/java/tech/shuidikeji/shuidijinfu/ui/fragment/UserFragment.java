package tech.shuidikeji.shuidijinfu.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseFragment;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;
import tech.shuidikeji.shuidijinfu.ui.activity.LoginActivity;
import tech.shuidikeji.shuidijinfu.ui.adapter.UserAdapter;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;

public class UserFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    UserAdapter mAdapter;

    View mHeaderView;
    View mFooterView;

    LinearLayout mLognBtn;

    List<AppConfigPojo.UserMenu> mData;

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
        mLognBtn = mHeaderView.findViewById(R.id.btn_login);
        mAdapter.addHeaderView(mHeaderView);
        mAdapter.addFooterView(mFooterView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mData);
        if (!CommonUtils.isLogin()){
            mFooterView.setVisibility(View.GONE);
        }else {
            mFooterView.setVisibility(View.VISIBLE);
        }

        mLognBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (!CommonUtils.isLogin())
                    LoginActivity.launcher(getActivity());
                break;
        }
    }
}
