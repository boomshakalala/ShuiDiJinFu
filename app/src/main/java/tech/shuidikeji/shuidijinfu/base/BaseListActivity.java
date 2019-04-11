package tech.shuidikeji.shuidijinfu.base;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.utils.ToastUtils;
import tech.shuidikeji.shuidijinfu.utils.ViewUtils;

/**
 * 列表Activity
 *
 * 布局文件必须包含
 * 1 PtrClassicFrameLayout或其子类 id 为 layout_ptr
 * 2 RecyclerView 或其子类 id 为 recycler_view
 * 子类必须初始化 mAdapter
 */

public abstract class BaseListActivity<P extends BasePresenter> extends BaseRefreshActivity<P>{

    protected String mEmptyMsg;//空视图提示信息
    protected int mEmptyIcon;//空视图图标
    protected int mCurrentPage = 1;//当前页码

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    protected BaseQuickAdapter mAdapter;


    @Override
    protected void initEvent() {
        super.initEvent();
        if (isLoadMoreEnable()) {
            mAdapter.setOnLoadMoreListener(this::onLoadMore, mRecyclerView);
        }

    }

    @Override
    public void showLoading() {
        if (!mPtrLayout.isRefreshing()&&!mAdapter.isLoading()){
            showProgressDialog();
        }
    }


    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mAdapter == null)
            return;
        if (isLoadMoreEnable()&&mAdapter.isLoading())
            mAdapter.loadMoreComplete();
    }

    @Override
    public void showEmpty() {
        if (mAdapter == null)
            return;
        if (mCurrentPage == 1)
            mAdapter.setEmptyView(ViewUtils.genEmptyView(getContext(),mEmptyIcon,mEmptyMsg));
        else if (isLoadMoreEnable()){
            mAdapter.loadMoreEnd(false);
            ToastUtils.showToast(getContext(),"已全部加载完成");
        }
    }


    @Override
    public void showError(String errMsg) {
        super.showError(errMsg);
        if (mAdapter == null)
            return;
        if (mCurrentPage == 1){
            super.showError(errMsg);
        }else if (isLoadMoreEnable()){
           mAdapter.loadMoreFail();
           mCurrentPage --;
        }
    }

    /**
     * 需要加载更多功能时 重写此方法 实现加载更多逻辑
     */
    protected void onLoadMore(){

    }

    /**
     * 子类必须重写此方法
     *
     * @return 是否支持加载更多
     */
    protected abstract boolean isLoadMoreEnable();
}
