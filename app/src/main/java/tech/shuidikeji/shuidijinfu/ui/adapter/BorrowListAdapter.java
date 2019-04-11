package tech.shuidikeji.shuidijinfu.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.pojo.BorrowListPojo;

public class BorrowListAdapter extends BaseQuickAdapter<BorrowListPojo, BaseViewHolder> {
    public BorrowListAdapter() {
        super(R.layout.item_borrow_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BorrowListPojo item) {
        helper.setText(R.id.tv_order_id,item.getSn());
        helper.setText(R.id.tv_status,item.getStatus_txt());
        helper.setText(R.id.tv_order_date,item.getCreate_time());
        if (item.getStatus() == 40 || item.getStatus() == 50){
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.color_text_over_date));
        } else if ( item.getStatus() == 41){
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.color_text_money_back));
        } else  {
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.color_text_gray));
        }
    }
}
