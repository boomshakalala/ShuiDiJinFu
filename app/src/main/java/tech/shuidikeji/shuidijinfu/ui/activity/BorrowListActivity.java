package tech.shuidikeji.shuidijinfu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseListActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.BorrowListContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.BorrowListPresenter;
import tech.shuidikeji.shuidijinfu.pojo.BorrowListPojo;
import tech.shuidikeji.shuidijinfu.ui.adapter.BorrowListAdapter;
import tech.shuidikeji.shuidijinfu.utils.CommonUtils;

public class BorrowListActivity extends BaseListActivity<BorrowListPresenter> implements BorrowListContract.IBorrowListView, BaseQuickAdapter.OnItemClickListener {

    public static void launcher(Activity context){
        if (!CommonUtils.isLogin()){
            LoginActivity.launcher(context);
            return;
        }
        Intent intent = new Intent(context,BorrowListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("我的借款").showBack().show();
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return true;
    }

    @Override
    protected void onRefresh() {
        mCurrentPage = 1;
        mPresenter.getBorrowList(mCurrentPage);
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        mCurrentPage++;
        mPresenter.getBorrowList(mCurrentPage);
    }

    @Override
    protected BorrowListPresenter getPresenter() {
        return new BorrowListPresenter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.include_ptr_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
    }

    @Override
    protected void initData() {
        mAdapter = new BorrowListAdapter();
        mAdapter.setOnItemClickListener(this);
        this.mEmptyIcon = R.mipmap.null_img;
    }

    @Override
    public void showBorrowList(List<BorrowListPojo.BorrowPojo> data) {
        if (mCurrentPage == 1){
            mAdapter.setNewData(data);
            mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        }else {
            mAdapter.addData(data);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // TODO: 跳转借款详情
    }
}
