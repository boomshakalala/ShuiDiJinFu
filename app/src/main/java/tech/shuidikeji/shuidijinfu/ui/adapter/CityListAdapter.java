package tech.shuidikeji.shuidijinfu.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.pojo.CityListPojo;

public class CityListAdapter extends BaseQuickAdapter<CityListPojo.ChildrenPojo, BaseViewHolder> {
    public CityListAdapter() {
        super(R.layout.item_address);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityListPojo.ChildrenPojo item) {
        if (helper.getAdapterPosition()-getHeaderLayoutCount() == 0)
            helper.setGone(R.id.v_divider,false);
        else
            helper.setGone(R.id.v_divider,true);
        helper.setText(R.id.tv_address,item.getTitle());
    }
}
