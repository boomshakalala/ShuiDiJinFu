package tech.shuidikeji.shuidijinfu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.base.BaseMvpActivity;
import tech.shuidikeji.shuidijinfu.mvp.contract.AddressListContract;
import tech.shuidikeji.shuidijinfu.mvp.presenter.AddressListPresenter;
import tech.shuidikeji.shuidijinfu.pojo.CityListPojo;
import tech.shuidikeji.shuidijinfu.ui.adapter.CityListAdapter;
import tech.shuidikeji.shuidijinfu.ui.adapter.ProvinceListAdapter;

public class AddressListActivity extends BaseMvpActivity<AddressListPresenter> implements AddressListContract.IAddressListView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view_province)
    RecyclerView mProvinceRecyclerView;
    @BindView(R.id.recycler_view_city)
    RecyclerView mCityRecyclerView;

    ProvinceListAdapter mProvinceAdapter;
    CityListAdapter mCityAdapter;

    private String province;


    public static void launcher(Activity activity){
        Intent intent = new Intent(activity,AddressListActivity.class);
        activity.startActivityForResult(intent,REQUEST_ADDRESS);
    }

    @Override
    protected AddressListPresenter getPresenter() {
        return new AddressListPresenter(this);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        getTitleBar().setTitle("开户地址").showBack().show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_address_list);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProvinceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityRecyclerView.setAdapter(mCityAdapter);
        mProvinceRecyclerView.setAdapter(mProvinceAdapter);
        mProvinceAdapter.setOnItemClickListener(this);
        mCityAdapter.setOnItemClickListener(this);
        mPresenter.getCityList();
    }

    @Override
    protected void initData() {
        mCityAdapter = new CityListAdapter();
        mProvinceAdapter = new ProvinceListAdapter();
    }

    @Override
    public void showAddressList(List<CityListPojo> data) {
        mProvinceAdapter.setNewData(data);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof ProvinceListAdapter){
            CityListPojo item = ((ProvinceListAdapter) adapter).getItem(position);
            mCityAdapter.setNewData(item.getChildren());
            province = item.getTitle();

        }else if (adapter instanceof CityListAdapter){
            CityListPojo.ChildrenPojo item = ((CityListAdapter) adapter).getItem(position);
            Intent intent = new Intent();
            intent.putExtra("city",item.getTitle());
            intent.putExtra("province",province);
            intent.putExtra("cityCode",item.getCode());
            setResult(RESULT_OK,intent);
            finish();
        }
    }

}
