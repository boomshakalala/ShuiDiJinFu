package tech.shuidikeji.shuidijinfu.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.image.ImageManager;
import tech.shuidikeji.shuidijinfu.pojo.AppConfigPojo;

public class UserAdapter extends BaseQuickAdapter<AppConfigPojo.UserMenu, BaseViewHolder> {
    public UserAdapter() {
        super(R.layout.item_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppConfigPojo.UserMenu item) {
        helper.setText(R.id.tv_name,item.getCn_name());
        ImageView imageView = helper.getView(R.id.iv_img);
        ImageManager.getInstance().loadNet(item.getImg(),imageView);
    }
}
